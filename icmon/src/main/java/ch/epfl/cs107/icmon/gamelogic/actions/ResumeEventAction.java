package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.messages.GamePlayMessage;

import java.util.ArrayList;
import java.util.List;

public class ResumeEventAction implements Action{
    private List<ICMonEvent> pausedEvents;
    private List<ICMonEvent> activeEvents = new ArrayList<>();
    private GamePlayMessage message;
    public ResumeEventAction(GamePlayMessage message, List<ICMonEvent> activeEvents){
        this.message = message;
        this.activeEvents = activeEvents;
    }

    @Override
    public void perform() {
            for(ICMonEvent events : activeEvents){
                pausedEvents.add(events);
                events.resume();
            }
        }

    }

