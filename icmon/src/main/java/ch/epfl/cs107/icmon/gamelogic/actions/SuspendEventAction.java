package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.messages.GamePlayMessage;

import java.util.ArrayList;
import java.util.List;

public class SuspendEventAction implements Action{
    private List<ICMonEvent> pausedEvents = new ArrayList<>();
    private List<ICMonEvent> activeEvents;
    private GamePlayMessage message;


    public SuspendEventAction(GamePlayMessage message, List<ICMonEvent> activeEvents){
        this.message = message;
        this.activeEvents = activeEvents;
    }


    @Override
    public void perform() {
            for(ICMonEvent events : activeEvents){
                pausedEvents.add(events);
                events.suspend();
            }
        }

    public List<ICMonEvent> getPausedEvents(){
            return pausedEvents;
    }
}

