package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;

public class UnregisterEventAction {
    

    ICMonEvent currentEvent;
    ICMon.ICMonEventManager eventManager;

    public UnregisterEventAction(ICMon.ICMonEventManager eventManager, ICMonEvent currentEvent){
        this.eventManager = eventManager;
        this.currentEvent = currentEvent;
        this.perform();
    }

    public void perform(){
        eventManager.removeActiveEvent(currentEvent);
    }
}
