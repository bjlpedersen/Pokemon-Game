package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.actor.Interactor;
import ch.epfl.cs107.play.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.area.AreaBehavior;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Animation;
import ch.epfl.cs107.play.engine.actor.OrientedAnimation;
import ch.epfl.cs107.play.engine.actor.Sprite;
import ch.epfl.cs107.play.engine.actor.TextGraphics;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.awt.*;
import java.util.Collections;
import java.util.List;

/**
 * ???
 */
public final class ICMonPlayer extends ICMonActor implements Interactor, Interactable {

    /** ??? */
    private final static int MOVE_DURATION = 8;
    /** ??? */

    /** ??? */

    private final Sprite sprite;
    /** ??? */
    private float hp;
    private ICMonPlayerInteractionHandler handler;
    private OrientedAnimation currentAnimation;
    private final OrientedAnimation landAnimation;
    private final OrientedAnimation waterAnimation;



    /**
     * ???
     * @param owner ???
     * @param orientation ???
     * @param coordinates ???
     * @param spriteName ???
     */
    public ICMonPlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates, String spriteName) {
        super(owner, orientation, coordinates);
        sprite = new Sprite(spriteName, 1.f, 1.f, this);
        orientation = getOrientation();
        landAnimation = new OrientedAnimation("actors/player",MOVE_DURATION, orientation, this);
        waterAnimation = new OrientedAnimation("actors/player_water",MOVE_DURATION, orientation, this);
        currentAnimation = landAnimation;
        handler = new ICMonPlayerInteractionHandler();


    }
    public class ICMonPlayerInteractionHandler implements ICMonInteractionVisitor {

        public void interactWith(ICMonBehavior.ICMonCell cell, boolean isCellInteraction) {
            if (isCellInteraction) {
                ICMonBehavior.AllowedWalkingType walkingType = cell.getAllowedWalkingType();
//                System.out.println("Detected Cell Type: " + walkingType); // Debug output

                if(walkingType==ICMonBehavior.AllowedWalkingType.FEET) {
                    currentAnimation = landAnimation;
                    currentAnimation.orientate(getOrientation());

                } else if(walkingType==ICMonBehavior.AllowedWalkingType.SURF){
                        currentAnimation = waterAnimation;
                        currentAnimation.orientate(getOrientation());
                    }
            }
        }

        public void interactWith(ICBall ball, boolean wantsViewInteraction){
            if (wantsViewInteraction) {
                ball.collect();
                new LogAction("CollectItem event completed").perform();
            }
        }
    }

    @Override
    public void draw(Canvas canvas){
        currentAnimation.draw(canvas);
    }

    /**
     * ???
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */

    @Override
    public void update(float deltaTime) {
        Keyboard keyboard = getOwnerArea().getKeyboard();

        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));
        super.update(deltaTime);

        if(keyboard.get(Keyboard.LEFT).isDown() || keyboard.get(Keyboard.UP).isDown()
        || keyboard.get(Keyboard.RIGHT).isDown() ||keyboard.get(Keyboard.DOWN).isDown()){

            currentAnimation.update(deltaTime);
            currentAnimation.orientate(getOrientation());



        } else {
            currentAnimation.reset();
        }
    }

    @Override
    public void draw() {
    }

    /**
     * ???
     * @return ???
     */
    @Override
    public boolean takeCellSpace() {
        return true;
    }

    /**
     * ???
     * @return ???
     */
    @Override
    public boolean isCellInteractable() {
        return true;
    }

    /**
     * ???
     * @return ???
     */
    @Override
    public boolean isViewInteractable() {
        return false;
    }

    /**
     * ???
     */
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        return keyboard.get(Keyboard.L).isDown();
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, true);
    }

    /**
     * ???
     * @param v (AreaInteractionVisitor) : the visitor
     * @param isCellInteraction ???
     */

    public void acceptInteraction(ICMonInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

    /**
     * Orientate and Move this player in the given orientation if the given button is down
     *
     * @param orientation (Orientation): given orientation, not null
     * @param b           (Button): button corresponding to the given orientation, not null
     */
    private void moveIfPressed(Orientation orientation, Button b) {
        if (b.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move(MOVE_DURATION);

            }
        }

    }

    /**
     * Leave an area by unregister this player
     */
    public void leaveArea() {
        getOwnerArea().unregisterActor(this);
    }

    /**
     * ???
     * @param area     (Area): initial area, not null
     * @param position (DiscreteCoordinates): initial position, not null
     */
    public void enterArea(Area area, DiscreteCoordinates position) {
        area.registerActor(this);
        area.setViewCandidate(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
        resetMotion();
    }

    /**
     * ???
     * @return ???
     */
    public boolean isWeak() {
        return (hp <= 0.f);
    }

    /**
     * Center the camera on the player
     */
    public void centerCamera() {
        getOwnerArea().setViewCandidate(this);
    }




}