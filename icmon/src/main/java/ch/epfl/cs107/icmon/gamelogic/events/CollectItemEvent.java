package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;

public class CollectItemEvent  extends ICMonEvent {
    public CollectItemEvent(ICMonItem item, ICMonActor mainCharacter) {
        super(mainCharacter);
    }

    public void interactWith(ICShopAssistant assistant , boolean isCellInteraction) {
        if (isCellInteraction) {
            start();
            new LogAction("This is an interaction between the player and ICShopAssistant based on events").perform();
        }
    }

    private void update(ICMonItem item) {
        if (item.isCollected()) {
            complete();
            new LogAction("CollectItem event completed").perform();
        }
    }
}
