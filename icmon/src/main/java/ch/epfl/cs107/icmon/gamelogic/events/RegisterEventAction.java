package ch.epfl.cs107.icmon.gamelogic.events;
import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.actions.Action;


public class RegisterEventAction implements Action {

    ICMonEvent currentEvent;
    ICMon.ICMonEventManager eventManager;


    public RegisterEventAction(ICMon.ICMonEventManager eventManager, ICMonEvent currentEvent){
        this.eventManager = eventManager;
        this.currentEvent = currentEvent;
        this.perform();
    }

    public void perform(){
        eventManager.addActiveEvent(currentEvent);
    }
                                                           
}
