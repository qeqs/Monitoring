package logic.events;

import controllers.rmi.entities.Measure;
import controllers.rmi.entities.Meter;
import controllers.rmi.entities.Policy;
import java.util.Date;
import java.util.List;
import controllers.rmi.entities.Action;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class Event {

    private Meter meter;
    private Measure measure;
    private Date createdAt;
    private Policy origin;
    private Severity severity;
    private List<Action> actions;

    public List<Action> getActions() {
        return actions;
    }

    public void addAction(Action action) {
        this.actions.add(action);
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Policy getOrigin() {
        return origin;
    }

    public void setOrigin(Policy origin) {
        this.origin = origin;
    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return severity.toString() + ": " + origin.getIdEvent().getName();
    }
    
    public String description(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        NumberFormat formatter = new DecimalFormat("#0.00");
        return "Value: "+formatter.format(getMeasure().getValue())+"\n"
                +"Treshold: "+getOrigin().getSign()+getOrigin().getTreshhold()+"\n"
                +"Date: "+sdf.format(createdAt);
    }
}
