package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.cs107.play.math.Orientation;

public class Door extends AreaEntity implements ICMonInteractionVisitor {
    private Area owner;
    private String destinationArea;
    private DiscreteCoordinates destinationCoordinates;
    private List<DiscreteCoordinates> occupiedCells;
    private List<DiscreteCoordinates> additionalCells;

    public Door(Area owner,String destinationArea, DiscreteCoordinates destinationCoordinates, DiscreteCoordinates mainCellPosition, DiscreteCoordinates... additionalCells) {
        super(owner, Orientation.UP, mainCellPosition);
        this.destinationArea = destinationArea;
        this.destinationCoordinates = destinationCoordinates;
        this.occupiedCells = new ArrayList<>();
        this.occupiedCells.add(mainCellPosition);
        if (additionalCells != null) {
            this.occupiedCells.addAll(Arrays.asList(additionalCells));
        }
    }





    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return occupiedCells;
    }


    @Override
    public boolean isViewInteractable() {
        return true;
    }
    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, true);
    }


    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public void draw(Canvas canvas) {

    }
    public String getDestinationArea(){
        return destinationArea;
    }
    public DiscreteCoordinates getDestinationCoordinates(){
        return destinationCoordinates;
    }
    
}
