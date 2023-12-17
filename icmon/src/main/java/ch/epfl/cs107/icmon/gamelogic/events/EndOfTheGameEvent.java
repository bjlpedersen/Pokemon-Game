package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;


import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;

public class EndOfTheGameEvent extends ICMonEvent {
    private ICShopAssistant shopAssistant;
    private ICMon.ICMonEventManager eventManager;

    public EndOfTheGameEvent(ICMonActor mainCharacter, ICMon.ICMonEventManager eventManager, ICShopAssistant shopAssistant) {
        super(mainCharacter, eventManager);
        this.shopAssistant = shopAssistant;
    }


    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        if (v instanceof ICMonInteractionVisitor) {
            ((ICMonInteractionVisitor) v).interactWith(shopAssistant, isCellInteraction);
            System.out.println("I heard that you were able to implement this step successfully. Congrats!");
        }
    }

    public String getEventName() {
        return "EndOfTheGame";
    }
}


/*public class EndOfTheGameEvent extends ICMonEvent {
    public EndOfTheGameEvent(ICMonActor mainCharacter, ICShopAssistant shopAssistant) {
        super(mainCharacter);
        System.out.println("End of game bitches");
    }

    
}*/
