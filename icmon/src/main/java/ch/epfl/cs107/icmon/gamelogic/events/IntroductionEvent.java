package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class IntroductionEvent extends ICMonEvent{
    Dialog dialog;
    public IntroductionEvent(ICMonActor mainCharacter, ICMon.ICMonEventManager eventManager) {
        super(mainCharacter, eventManager);
        this.dialog = new Dialog("welcome_to_icmon");
    }

    public Dialog getDialog() {
        return dialog;
    }
    public void complete() {
        if (dialog.isCompleted()) {
            this.completed = true;
        }
    }
}
