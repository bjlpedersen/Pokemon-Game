package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.Door;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.events.CollectItemEvent;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * ???
 */
public final class Town extends ICMonArea {

    /**
     * ???
     * @return ???
     */
    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(15, 20);
    }

    /**
     * ???
     */
    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
        registerActor(new Door(this, "house", new DiscreteCoordinates(2,2), new DiscreteCoordinates(7,27)));
        registerActor(new Door(this, "lab", new DiscreteCoordinates(6,2), new DiscreteCoordinates(15,24)));
        registerActor(new Door(this, "arena", new DiscreteCoordinates(4,2), new DiscreteCoordinates(20,16)));
        registerActor(new Door(this, "shop", new DiscreteCoordinates(3,2), new DiscreteCoordinates(25,20)));

    }


    /**
     * ???
     * @return ???
     */
    @Override
    public String getTitle() {
        return "town";
    }
    @Override
    public void update(float deltaTime){super.update(deltaTime);}
}

