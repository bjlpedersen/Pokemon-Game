package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class InteractionWithProfesorOakEvent extends ICMonEvent{
    Dialog dialog = new Dialog("first_interaction_with_prof_oak");
    public InteractionWithProfesorOakEvent(ICMonActor mainCharacter, ICMon.ICMonEventManager eventManager) {
        super(mainCharacter, eventManager);
    }

    public Dialog perform() {
        return dialog;
    }

    public boolean getCompleted() {
        return dialog.isCompleted();
    }
}
