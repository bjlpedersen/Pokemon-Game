package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.gamelogic.actions.Action;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;

public class ICMonEvent implements ICMonInteractionVisitor {
    boolean started = false;
    boolean completed = false;
    boolean suspended = false;
    ICMonActor mainCharacter;
    ICMon.ICMonEventManager eventManager;

    public ICMonEvent(ICMonActor mainCharacter, ICMon.ICMonEventManager eventManager) {
        this.mainCharacter = mainCharacter;
        this.eventManager = eventManager;

    }

    public boolean getStarted() {
        return started;
    }

    public boolean getCompleted() {
        return completed;
    }

    public final void start() {
        if (!started) {
            started = true;
            // Execute he actions needed to start the event
        }
    }

    public void complete() {
        if (!completed && started) {
            completed = true;
            // Execute the actions needed to complete the event
        }
    }

    public final void suspend() {
        if (!suspended && !completed && started) {
            suspended = true;
            // Execute the actions needed to suspend the event
        }
    }

    public final void resume() {
        if (suspended && !completed && started) {
            suspended = false;
            // Execute the actions needed to resume the event
        }
    }

    public final void onStart(ICMonEvent otherEvent) {
        if (otherEvent.completed) {
            new RegisterEventAction(eventManager, this);
        }
    }

    public final void onStart(){
        if (!completed) {
            new RegisterEventAction(eventManager, this);
        }
    }

    public final void onComplete(StartEventAction action) {
        if (this.completed) {
            new UnregisterEventAction(eventManager, this).perform();
        }
    }

    final void onSuspension(Action action) {
        new UnregisterEventAction(eventManager, this).perform();
    }

    final void onResume(Action action) {
        new UnregisterEventAction(eventManager, this).perform();
    }

    public String getName(){
        return "ICMonEvent";
    }


}
