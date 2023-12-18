package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;

public class StartEventAction {
    ICMonEvent currentEvent;
    ICMon.ICMonEventManager eventManager;

    public StartEventAction(ICMon.ICMonEventManager eventManager, ICMonEvent currentEvent) {
        this.eventManager = eventManager;
        this.currentEvent = currentEvent;
        this.eventManager.addActiveEvent(currentEvent);
        currentEvent.start();
    }

}
