package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.pokemon.ICMonFightableActor;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.events.PokemonFightEvent;
import ch.epfl.cs107.icmon.messages.GamePlayMessage;
import ch.epfl.cs107.icmon.messages.PassDoorMessage;
import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.events.CollectItemEvent;
import ch.epfl.cs107.icmon.gamelogic.events.EndOfTheGameEvent;
import ch.epfl.cs107.icmon.gamelogic.events.StartEventAction;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.icmon.messages.SuspendWithEvent;
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
import ch.epfl.cs107.play.engine.actor.Dialog;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ???
 */
public final class ICMonPlayer extends ICMonActor implements Interactor, Interactable {

    /**
     * ???
     */
    private final static int MOVE_DURATION = 2;
    /** ??? */

    /**
     * ???
     */
    private boolean isInDialog = false;
    private final Sprite sprite;
    /**
     * ???
     */
    private float hp;
    private ICMonPlayerInteractionHandler handler;
    private OrientedAnimation currentAnimation;
    private final OrientedAnimation landAnimation;
    private final OrientedAnimation waterAnimation;

    private ICMon.ICMonGameState gameState;
    private ICMon icMon;
    private Dialog dialog;
    private List<Pokemon> collection = new ArrayList<Pokemon>();


    /**
     * ???
     *
     * @param owner       ???
     * @param orientation ???
     * @param coordinates ???
     * @param spriteName  ???
     */
    public ICMonPlayer(ICMon icMon, Area owner, Orientation orientation, DiscreteCoordinates coordinates, String spriteName, Pokemon starterPokemon) {
        super(owner, orientation, coordinates);
        sprite = new Sprite(spriteName, 1.f, 1.f, this);
        collection.add(starterPokemon);
        orientation = getOrientation();
        landAnimation = new OrientedAnimation("actors/player", MOVE_DURATION, orientation, this);
        waterAnimation = new OrientedAnimation("actors/player_water", MOVE_DURATION, orientation, this);
        currentAnimation = landAnimation;

        this.icMon = icMon;

        this.gameState = icMon.getGameState(); // get the game state from the ICMon instance
        handler = new ICMonPlayerInteractionHandler();


    }

    public class ICMonPlayerInteractionHandler implements ICMonInteractionVisitor {

        public void interactWith(ICMonBehavior.ICMonCell cell, boolean isCellInteraction) {
            if (isCellInteraction) {
                ICMonBehavior.AllowedWalkingType walkingType = cell.getAllowedWalkingType();
//                System.out.println("Detected Cell Type: " + walkingType); // Debug output

                if (walkingType == ICMonBehavior.AllowedWalkingType.FEET) {
                    currentAnimation = landAnimation;
                    currentAnimation.orientate(getOrientation());

                } else if (walkingType == ICMonBehavior.AllowedWalkingType.SURF) {
                    currentAnimation = waterAnimation;
                    currentAnimation.orientate(getOrientation());
                }
            }
        }


        public void interactWith(ICBall ball, boolean wantsViewInteraction) {
            if (wantsViewInteraction) {
                CollectItemEvent event = new CollectItemEvent(ball, icMon.getEventManager(), ICMonPlayer.this);
                ball.collect();
                new Dialog("collect_item_event_interaction_with_icshopassistant");
                new LogAction("CollectItem event completed").perform();
//                event.onComplete(new StartEventAction(icMon.getEventManager(), new EndOfTheGameEvent(ICMonPlayer.this, icMon.getEventManager())));
            }
        }

        public void interactWith(Pokemon pokemon, boolean isCellInteraction) {
            if (isCellInteraction) {
                GamePlayMessage message = new SuspendWithEvent(new PokemonFightEvent(ICMonPlayer.this, icMon.getEventManager(), pokemon, collection.get(0)));
                gameState.send(message);
            }
        }


        public void interactWith(ICShopAssistant assistant, boolean wantsViewInteraction) {
            if (wantsViewInteraction()) {
                isInDialog = true;
                dialog = new Dialog("end_of_game_event_interaction_with_icshopassistant");
                //start();
                //new LogAction("This is an interaction between the player and ICShopAssistant based on events").perform();

            }

        }

        public void interactWith(Door door, boolean isCellInteraction) {
            if (isCellInteraction) {
                GamePlayMessage message = new PassDoorMessage(door);
                gameState.send(message);
            }
        }
    }



        public void draw(Canvas canvas) {
            currentAnimation.draw(canvas);
            if (isInDialog) {
                dialog.draw(canvas);
            }
        }

        /**
         * ???
         *
         * @param deltaTime elapsed time since last update, in seconds, non-negative
         */

        public void update(float deltaTime) {
            Keyboard keyboard = getOwnerArea().getKeyboard();

            if (isInDialog) {
                if (keyboard.get(Keyboard.SPACE).isPressed()) {
                    dialog.update(deltaTime);
                }
                if (dialog.isCompleted()) {
                    isInDialog = false;
                }
            } else {
                moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
                moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
                moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
                moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));
                super.update(deltaTime);

                if (keyboard.get(Keyboard.LEFT).isDown() || keyboard.get(Keyboard.UP).isDown()
                        || keyboard.get(Keyboard.RIGHT).isDown() || keyboard.get(Keyboard.DOWN).isDown()) {

                    currentAnimation.update(deltaTime);
                    currentAnimation.orientate(getOrientation());

                } else {
                    currentAnimation.reset();
                }
            }
        }

        public void draw() {
        }

        /**
         * ???
         *
         * @return ???
         */
        public boolean takeCellSpace() {
            return false;
        }

        /**
         * ???
         *
         * @return ???
         */
        public boolean isCellInteractable() {
            return true;
        }

        /**
         * ???
         *
         * @return ???
         */
        public boolean isViewInteractable() {
            return false;
        }

        /**
         * ???
         */
        public List<DiscreteCoordinates> getCurrentCells() {
            return Collections.singletonList(getCurrentMainCellCoordinates());
        }

        public List<DiscreteCoordinates> getFieldOfViewCells() {
            return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
        }

        public boolean wantsCellInteraction() {
            return true;
        }

        public boolean wantsViewInteraction() {
            Keyboard keyboard = getOwnerArea().getKeyboard();
            return !isInDialog && keyboard.get(Keyboard.L).isDown();
        }


        @Override
        public void interactWith(Interactable other, boolean isCellInteraction) {
            other.acceptInteraction(handler, isCellInteraction);

        }


        /**
         * ???
         *
         * @param v                 (AreaInteractionVisitor) : the visitor
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
         *
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

        /**
         * Center the camera on the player
         */
        public void centerCamera() {
            getOwnerArea().setViewCandidate(this);
        }

    }
