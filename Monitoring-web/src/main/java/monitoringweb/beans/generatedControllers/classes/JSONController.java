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
import java.util.List;
import javax.ejb.EJB;
import dao.MeasureFacade;
import dao.MetersFacade;
import controllers.rmi.entities.Measure;
import controllers.rmi.entities.Meter;
import controllers.rmi.entities.Profile;
import java.util.Date;
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
    private Profile profile;
    private Object resource;
    private StringBuilder title;
    private List<List<Measure>> measureLists;
    private boolean valueChanged;
    private GraphicType type;
    private AnimationType animation;
    private boolean timerStarted;
    private int timeForMeasure; //minutes
  
    @EJB
    private MeasureFacade measureFacade;
     @EJB
    private MetersFacade meterFacade;
    
    public JSONController() {
        json = new JSONObject();
        title = new StringBuilder();
        measureLists = new ArrayList<>();
        valueChanged = false;
        type=GraphicType.line;
        animation=AnimationType.none;
        timerStarted=false;
    }
    
     public void setTimeForMeasure(int timeForMeasure)
    {
        this.timeForMeasure = timeForMeasure;
    }
    public int getTimeForMeasure()
    {
        return timeForMeasure;
    }
    
     public void setMeter(Meter meter) {
        this.meter = meter;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
 
    public Meter getMeter() {
        return meter;
    }

    public Profile getProfile() {
        return profile;
    }


     public void setTitle(StringBuilder title) {
        this.title = title;
    }

    public StringBuilder getTitle() {
        title.setLength(0);
          title.append("Monitoring");
        if(meter!=null){            
            title.append(" ");
            if (meter.getUnit().equals(""))
               title.append(meter.getName());
            else
               title.append(meter.getName()).append(" (").append(meter.getUnit()).append(")");
        }
        if(profile!=null){
            title.append(" ");
            title.append(profile.getName());
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
    
    public void setType(GraphicType type) {
        this.type = type;
    }

    public GraphicType getType() {
        return type;
    }
       public void setRefresh(AnimationType refresh) {
        this.animation = refresh;
    }

    public AnimationType getRefresh() {
        return animation;
    }
    
    public boolean getTimerStarted(){
        return timerStarted;
    }
    public void setTimerStarted(boolean timerStarted){
         this.timerStarted=timerStarted;
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
        json.put("credits", createCredits());
    }
    
    public void changeValue()
    {
          this.valueChanged = true;
    }
    
    
     public List<List<Measure>> getMeasureLists() {
        return measureLists;
    }
    
     public List<GraphicType> availableTypes()
    {
        List<GraphicType> list=new ArrayList();
        list.add(GraphicType.line);
        list.add(GraphicType.column);
        list.add(GraphicType.scatter);
        list.add(GraphicType.spline);
        return list;
    }
    
    public List<AnimationType> availableAnimation(){
         List<AnimationType> list=new ArrayList();
         list.add(AnimationType.none);
         list.add(AnimationType.simple);
         return list;        
    }
    
    private void setMeasureList() {
        measureLists.clear();
        List<Measure> current = new ArrayList();
        
        Date date = new Date(new Date().getTime()-timeForMeasure*60*1000);
        
        if (meter == null && profile == null) {
            for (Meter m : meterFacade.findAll()) {
                    current = measureFacade.findAllForMeterOrderedByTimeForTime(m,date);
                if (!current.isEmpty()) {
                    measureLists.add(current);
                }
            }
        } 
        else if (meter == null && profile != null ) {
            for (Meter met : meterFacade.findAll()) {
                 current = measureFacade.findAllForMeterAndProfileForTime(met,profile,date);
                if (!current.isEmpty()) {
                    measureLists.add(current);
                }
            }
        } 
          else if (meter != null && profile == null ) {
                 current = measureFacade.findAllForMeterOrderedByTimeForTime(meter,date);
                if (!current.isEmpty()) {
                    measureLists.add(current);
                }        
        } 
        
        
         else if (meter != null && profile != null ) {
                    current = measureFacade.findAllForMeterAndProfileForTime(meter,profile,date);
                if (!current.isEmpty()) {
                    measureLists.add(current);
                }            
        } 

    }

    
    public void changeAnimationToNone(){
        this.animation=AnimationType.none;
        changeValue();
    }
    
      public void changeAnimationToSimple(){
        this.animation=AnimationType.simple;
        changeValue();
    }
    
   public void initAfterRefresh()
   {
       this.timerStarted=false;
       this.animation=AnimationType.simple;
   }
      
    public JSONObject changeSeries() throws JSONException
    { 
        setMeasureList();
        json.append("series", createSeries());
        return json;
    }
    private JSONObject createTitle() throws JSONException
    {
        JSONObject jstitle=new JSONObject();
        jstitle.put("text", getTitle());
        jstitle.put("x", new Integer(-20));
        return jstitle;
    }
    
      private JSONObject createCredits() throws JSONException
    {
        JSONObject jstitle=new JSONObject();
        jstitle.put("text", "Monitoring");
        jstitle.put("href", "http://185.5.251.73:28080/Monitoring-web");
        return jstitle;
    }
    
    private JSONObject createChart() throws JSONException {
        JSONObject chart = new JSONObject();
        
        if(animation.equals(AnimationType.none))
             chart.put("zoomType", "x");
        
        switch(type){
            case line:
                chart.put("type", "line");
                break;
            case column:    
                chart.put("type", "column");
                break;
            case scatter:
                 chart.put("type", "scatter");
            break;
             case spline:
                 chart.put("type", "spline");
             break;
            default:
                chart.put("type", "column");
                break;
        }
         chart.put("animation", false);
        return chart;
    }
   
    public JSONObject createX() throws JSONException{
        JSONObject x = new JSONObject();
        x.put("type", "datetime");
        x.put("minRange", 0.05);
        return x;
    
    }
    
    
    
    private JSONArray createSeries() throws JSONException{
        JSONArray series = new JSONArray();
        for(List<Measure> list : measureLists)
        {   
             JSONObject line = new JSONObject();
             JSONArray points=new JSONArray();
      
                for(Measure m : list)
                   {
                    JSONObject point= new JSONObject();
                    point.put("x", m.getTstamp().getTime());
                    point.put("y", m.getValue());
                    point.put("name", m.getTstamp().toString()+m.getResource());
                    points.put(point);       
                    }

         line.put("data", points);
          switch(type){
            case line:
               line.put("type", "line");
               break;
            case column:    
               line.put("type", "column");
               break;
             case scatter:
                 line.put("type", "scatter");
               break;
             case spline:
                 line.put("type", "spline");
               break;
            default:
                line.put("type", "column");
                break;
        }
        line.put("name", list.get(0).getIdMeter().getName());
        series.put(line);
        }
        return series;
    }
    
    private JSONObject createPlotOptions() throws JSONException{
        JSONObject plotOptions = new JSONObject();
        JSONObject line = new JSONObject();
        JSONObject marker = new JSONObject();
        JSONObject series = new JSONObject();
        marker.put("radius",3);
        marker.put("enabled", true);
        line.put("marker", marker);
        series.put("animation", false);
        series.put("turboThreshold", 0);
        plotOptions.put("line", line);
        
        plotOptions.put("series", series);
       
        
        return plotOptions;
    }
      
public enum GraphicType{
    line,
    column,
    scatter,
    spline
}

public enum AnimationType{
    none,
    simple
}

}

class JSONFunction implements JSONString {

    private String string;

    public JSONFunction(String string) {
        this.string = string;
    }

    @Override
    public String toJSONString() {
        return string;
    }

}



