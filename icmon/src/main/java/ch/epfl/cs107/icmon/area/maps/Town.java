package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.window.Canvas;

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
        return new DiscreteCoordinates(2, 10);
    }

    /**
     * ???
     */
    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
        registerActor(new ICBall(this, new DiscreteCoordinates(6,6)));
    }

    /**
     * ???
     * @return ???
     */
    @Override
    public String getTitle() {
        return "town";
    }

}