package logic.events;

import controllers.rmi.entities.Measure;
import controllers.rmi.entities.Meter;
import controllers.rmi.entities.Policy;
import java.util.Date;

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

    public EventBuilder setPriority(EventPriority priority) {
        if (isCreated()) {
            event.setPriority(priority);
        }
        return EventBuilder.this;
    }

    public EventBuilder setAction(EventAction action) {
        if (isCreated()) {
            event.setAction(action);
        }
        return EventBuilder.this;
    }

    public EventBuilder setAction(int action) {
        if (isCreated()) {
            EventAction act;
            switch (action) {
                case 0:
                    act = EventAction.DoNothing;
                    break;
                case 1:
                    act = EventAction.Notification;
                    break;
                case 2:
                    act = EventAction.Alarm;
                    break;
                case 3:
                    act = EventAction.Auto;
                    break;
                default:
                    act = null;
            }
            event.setAction(act);
        }
        return EventBuilder.this;
    }
     public EventBuilder setPolicy(Policy policy) {
        if (isCreated()) {
            event.setOrigin(policy);
            setPriority(EventPriority.valueOf(policy.getIdEvent().getPriority()));
            //setAction(policy.getIdEvent().getAction());            
        }
        return EventBuilder.this;
    }

    private boolean isCreated() {
        return event != null;
    }
}
