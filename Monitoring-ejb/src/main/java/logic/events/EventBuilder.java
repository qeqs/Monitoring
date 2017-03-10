package logic.events;

import controllers.rmi.entities.Measure;
import controllers.rmi.entities.Meter;
import controllers.rmi.entities.Policy;
import java.util.Date;
import rmi.Action;

public class EventBuilder {

    private Event event;

    public EventBuilder createNewEvent() {
        event = new Event();
        event.setCreatedAt(new Date());
        return EventBuilder.this;
    }

    public Event getEvent() {
        return event;
    }

    public EventBuilder setMeter(Meter meter) {
        if (isCreated()) {
            event.setMeter(meter);
        }
        return EventBuilder.this;
    }

    public EventBuilder setMeasure(Measure measure) {
        if (isCreated()) {
            event.setMeasure(measure);
        }
        return EventBuilder.this;
    }

    public EventBuilder setAction(Action action) {
        if (isCreated()) {
            event.addAction(action);
        }
        return EventBuilder.this;
    }

    public EventBuilder setAction(String[] action) {
        if (isCreated()) {

            for (String string : action) {

                event.addAction(Action.valueOf(string));
            }
        }
        return EventBuilder.this;
    }

    public EventBuilder setPolicy(Policy policy) {
        if (isCreated()) {
            event.setOrigin(policy);
            setSeverity(Severity.valueOf(policy.getIdEvent().getPriority()));
            //setAction(policy.getIdEvent().getAction());            
        }
        return EventBuilder.this;
    }

    public EventBuilder setSeverity(Severity severity) {
        if (isCreated()) {
            event.setSeverity(severity);
        }
        return EventBuilder.this;
    }

    private boolean isCreated() {
        return event != null;
    }
}
