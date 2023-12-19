package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.Door;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Arena extends ICMonArea {

        @Override
        public String getTitle() {
            return "arena";
        }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(5, 5);
    }

        @Override
        protected void createArea() {
            registerActor(new Background(this));
            registerActor(new Foreground(this));
            registerActor(new Door(this, "town", new DiscreteCoordinates(20,15), new DiscreteCoordinates(4,1),new DiscreteCoordinates(5,1)));
//            registerActor(new Door(this, "arena", new DiscreteCoordinates(4,2), new DiscreteCoordinates(20,16)));

        }

        @Override
        public void update(float deltaTime) {
            super.update(deltaTime);
        }
}
