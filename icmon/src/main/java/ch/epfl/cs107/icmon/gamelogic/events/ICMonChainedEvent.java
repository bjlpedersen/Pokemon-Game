package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonActor;

public class ICMonChainedEvent extends ICMonEvent {
    ICMonEvent initialEvent;
    ICMonEvent[] chainedEvents;

    public ICMonChainedEvent(ICMonActor mainCharacter, ICMon.ICMonEventManager eventManager, ICMonEvent intialEvent, ICMonEvent... chainedEvents) {
        super(mainCharacter, eventManager);
        this.initialEvent = intialEvent;
        this.chainedEvents = chainedEvents;
        new StartEventAction(eventManager, initialEvent);
        intialEvent.onComplete(new StartEventAction(eventManager, chainedEvents[0]));

        executeChainedEvents();
        if (chainedEvents[3].getCompleted()) {
            this.completed = true;
        }
    }

    private void executeChainedEvents() {
        if (chainedEvents[0].getCompleted()) {
            new StartEventAction(eventManager, chainedEvents[1]);
        }
        if (chainedEvents[1].getCompleted()) {
            new StartEventAction(eventManager, chainedEvents[2]);
        }
        if (chainedEvents[2].getCompleted()) {
            System.out.println("garry executed");
            new StartEventAction(eventManager, chainedEvents[3]);
        }
    }


    private int getIndex(ICMonEvent event) {
        for (int i = 0; i < chainedEvents.length; i++) {
            if (chainedEvents[i] == event) {
                return i;
            }
        }
        return -1;
    }
}
