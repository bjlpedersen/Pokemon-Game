package ch.epfl.cs107.icmon.messages;

import ch.epfl.cs107.icmon.actor.Door;
import ch.epfl.cs107.icmon.messages.GamePlayMessage;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.icmon.ICMon;


public class PassDoorMessage extends GamePlayMessage {
    private String area;
    private DiscreteCoordinates coordinates;

    public PassDoorMessage(Door door){
        area = door.getDestinationArea();
        coordinates = door.getDestinationCoordinates()
        ;
    }

    @Override
    public void process(ICMon icMon){
        icMon.switchArea(area, coordinates);
    }
}