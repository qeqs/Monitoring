/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoringweb.beans.generatedControllers.classes;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.swing.event.ChangeEvent;
import dao.MeasureFacade;
import dao.MetersFacade;
import entities.Measure;
import entities.Meter;
import entities.Pm;
import entities.Vm;
import org.primefaces.json.JSONArray;
import org.primefaces.json.*;

/**
 *
 * @author annie
 */
@Named(value = "jsonController")
@SessionScoped
public class JSONController implements Serializable {

    
    private JSONObject json;
    private Meter meter;
    private Vm vm;
    private Pm pm;
    private StringBuilder title;
    private List<List<Measure>> measureLists;
    private boolean valueChanged;

   
    @EJB
    private MeasureFacade measureFacade;
     @EJB
    private MetersFacade meterFacade;
    
    public JSONController() {
        json = new JSONObject();
        title = new StringBuilder();
        measureLists = new ArrayList<>();
        valueChanged = false;
    }
    
     public void setMeter(Meter meter) {
        this.meter = meter;
    }

    public void setVm(Vm vm) {
        this.vm = vm;
    }

    public void setPm(Pm pm) {
        this.pm = pm;
    }

    public Meter getMeter() {
        return meter;
    }

    public Vm getVm() {
        return vm;
    }

    public Pm getPm() {
        return pm;
    }
     public void setTitle(StringBuilder title) {
        this.title = title;
    }

    public StringBuilder getTitle() {
        title.setLength(0);
          title.append("Monitoring");
        if(meter!=null){            
            title.append(" ");
            title.append(meter.getName());
        }
        if(vm!=null){
            title.append(" ");
            title.append(vm.getName());
         }      
        return title;
    }
    
    public MeasureFacade getMeasureFacade()
    {
         return measureFacade;
    }

    public void setMeasureFacade(MeasureFacade measureFacade)
    {
        this.measureFacade = measureFacade;
    }
    public MetersFacade getMeterFacade()
    {
         return meterFacade;
    }

    public void setMeasureFacade(MetersFacade meterFacade)
    {
        this.meterFacade = meterFacade;
    }
    
    public JSONObject getJson() throws JSONException
    {
        if (json.length() == 0 || valueChanged) {
            changeJSON();
        }
        return json;
    }
    
    public void changeJSON() throws JSONException
    {
        setMeasureList();
        json.put("title", createTitle());
        json.put("chart", createChart());
        json.put("xAxis", createX());
        json.put("series", createSeries());
        json.put("plotOptions", createPlotOptions());
    }
    
    public void changeValue()
    {
          this.valueChanged = true;
    }
    
    private void setMeasureList(){
        measureLists.clear();
        if(meter==null)
        {
            for(Meter m : meterFacade.findAll())
                measureLists.add( measureFacade.findAllForMeterOrderedByTime(m));
        }
        else
        {
             measureLists.add( measureFacade.findAllForMeterOrderedByTime(meter));
        }
        
    }
    
    private JSONObject createTitle() throws JSONException
    {
        JSONObject jstitle=new JSONObject();
        jstitle.put("text", getTitle());
        jstitle.put("x", new Integer(-20));
        return jstitle;
    }
    
    private JSONObject createChart() throws JSONException
    {
       JSONObject chart=new JSONObject();
        chart.put("zoomType", "x");
        return chart;
    }
    
    
    
    public JSONObject createX() throws JSONException{
        JSONObject x = new JSONObject();
        x.put("type", "datetime");
        return x;
    
    }
    
    private JSONArray createSeries() throws JSONException{
        JSONArray series = new JSONArray();
        for(List<Measure> list:measureLists)
        {
            
        JSONObject line = new JSONObject();
        JSONArray points=new JSONArray();
      
       for(Measure m : list)
       {
           JSONObject point= new JSONObject();
           point.put("x", m.getTstamp().getTime());
           point.put("y", m.getValue());
           point.put("name", m.getTstamp().toString()+m.getIdMeasure());
           points.put(point);
       
       }

        line.put("data", points);
        line.put("type","line");
        line.put("name", "");
        series.put(line);
        }
        return series;
    }
    
    private JSONObject createPlotOptions() throws JSONException{
        JSONObject plotOptions = new JSONObject();
        JSONObject line = new JSONObject();
        JSONObject marker = new JSONObject();
        marker.put("radius",4);
        marker.put("enabled", true);
        line.put("marker", marker);
        plotOptions.put("line", line);
        return plotOptions;
    }
    
    
    
    
}
