package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;


public class CollectItemEvent  extends ICMonEvent {
    public CollectItemEvent(ICMonItem item, ICMon.ICMonEventManager eventManager, ICMonActor mainCharacter) {
        super(mainCharacter, eventManager);
    }

    private void update(ICMonItem item) {
        if (item.isCollected()) {
            complete();
            new LogAction("CollectItem event completed").perform();
        }
    }
}
