package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;

public class Latios extends Pokemon {
    public Latios(Area owner, Orientation orientation, DiscreteCoordinates coordinates){
        super("latios", 10, 10, 1, "latios", owner, orientation, coordinates);
    }


    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }
}
