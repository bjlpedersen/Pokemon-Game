package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public class ICShopAssistant extends NPCActor implements ICMonInteractionVisitor {
    public ICShopAssistant(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates, "actors/assistant");
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, true);
    }

    @Override public boolean takeCellSpace(){return true;}
    @Override
    public boolean isViewInteractable() {
        return true;
    }
}
