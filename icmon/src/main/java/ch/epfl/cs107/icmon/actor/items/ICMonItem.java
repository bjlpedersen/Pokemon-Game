package ch.epfl.cs107.icmon.actor.items;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.engine.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;


 abstract public class ICMonItem extends CollectableAreaEntity implements Interactable {
     Sprite sprite;
     private final Area currentArea;


    public ICMonItem(Area area, DiscreteCoordinates position, String spriteName){
        super(area, Orientation.DOWN, position);
        this.sprite = new RPGSprite(spriteName, 1f, 1f, this);
        currentArea = area;


    }
    public Area getArea(){
        return currentArea;
    }

    public void Draw(Canvas canvas){
        sprite.draw(canvas);
    }
    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override public boolean takeCellSpace(){return true;}

     public void acceptInteraction(ICMonInteractionVisitor v, boolean isCellInteraction) {
         ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
     }


 }
