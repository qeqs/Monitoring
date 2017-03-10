package logic.events;

import controllers.rmi.entities.Measure;
import controllers.rmi.entities.Meter;
import controllers.rmi.entities.Policy;
import java.util.Date;
import java.util.List;
import rmi.Action;

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
}
