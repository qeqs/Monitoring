package logic.events;

import entities.Measure;
import entities.Meter;
import entities.Policy;
import java.util.Date;

public class Event {

    private Meter meter;
    private Measure measure;
    private EventPriority priority;
    private EventAction action;
    private Date createdAt;
    private Policy origin;

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

    public EventPriority getPriority() {
        return priority;
    }

    public void setPriority(EventPriority priority) {
        this.priority = priority;
    }

    public EventAction getAction() {
        return action;
    }

    public void setAction(EventAction action) {
        this.action = action;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    private static class EventPriority {

        public EventPriority() {
        }
    }
}
