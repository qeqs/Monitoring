package rest;

import logic.events.Event;


public interface Listener {

    /**
     * метод выполняется до решения controller что делать с event
     * @param event
     */
    void onStoreEvent(Event event);
}
