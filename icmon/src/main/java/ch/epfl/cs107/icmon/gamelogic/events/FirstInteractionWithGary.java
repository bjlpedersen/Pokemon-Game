package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonActor;

public class FirstInteractionWithGary extends ICMonEvent{
    public FirstInteractionWithGary(ICMonActor mainCharacter, ICMon.ICMonEventManager eventManager) {
        super(mainCharacter, eventManager);
    }
}
