package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;

public class Nidoqueen extends Pokemon {
    public Nidoqueen(Area owner, Orientation orientation, DiscreteCoordinates coordinates){
        super("nidoqueen", 10, 10, 1, "nidoqueen", owner, orientation, coordinates);
    }


    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }
}
