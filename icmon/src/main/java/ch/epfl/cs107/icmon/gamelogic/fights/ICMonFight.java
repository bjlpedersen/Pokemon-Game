package ch.epfl.cs107.icmon.gamelogic.fights;

import java.security.Key;
import java.util.concurrent.TimeUnit;

import ch.epfl.cs107.icmon.actor.pokemon.Bulbizarre;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.actor.pokemon.actions.AttackAction;
import ch.epfl.cs107.icmon.actor.pokemon.actions.RunAction;
import ch.epfl.cs107.icmon.area.maps.Arena;
import ch.epfl.cs107.icmon.area.maps.Lab;
import ch.epfl.cs107.icmon.graphics.ICMonFightActionSelectionGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightArenaGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightInteractionGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightTextGraphics;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class ICMonFight extends PauseMenu {
    private ICMonFightActionSelectionGraphics actionSelectionGraphics;
    private boolean isPaused = true;
    private float counter = 5.f;
    private boolean hasRequestedPause = false;
    private States currentState = States.INTRODUCTION;
    private Pokemon player;
    private Pokemon opponent;
    private ICMonFightAction currentAction;
    private ICMonFightArenaGraphics arena;

    
    
    


    private enum States {
        INTRODUCTION,
        ACTION_SELECTION_PLAYER,
        PLAYER_ACTION,
        OPPONENT_ACTION,
        CONCLUSION;

    }

    public ICMonFight(Pokemon player, Pokemon opponent) {
        super();
        currentState = States.INTRODUCTION;
        this.player = player;
        this.opponent = opponent;

    }

    public boolean isPaused() {
        
        return counter > 0;
    }

    public float getCounter() {
        return counter;
    }

    @Override
public void drawMenu(Canvas canvas) {
        ICMonFightArenaGraphics arena = new ICMonFightArenaGraphics(CAMERA_SCALE_FACTOR,  opponent.properties(), player.properties());

//        if (currentState == States.INTRODUCTION) {
//            arena.setInteractionGraphics(new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "Welcome to the Fight!"));
//        } else if (currentState == States.CONCLUSION) {
//            if (opponent.isDead) {
//                arena.setInteractionGraphics(new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "You won the fight!"));
//            } else if (player.isDead) {
//                arena.setInteractionGraphics(new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "The opponent won the fight!"));
//            }
//            else {
//                arena.setInteractionGraphics(new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "The player decided not to continue the fight!"));
//            }
//        }


    if (currentState == States.INTRODUCTION) {
        arena.setInteractionGraphics(new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "Welcome to the Fight!"));
    } else if (currentState == States.ACTION_SELECTION_PLAYER) {
        arena.setInteractionGraphics(actionSelectionGraphics);
    } else if(currentState == States.CONCLUSION && player.isDead) {
        arena.setInteractionGraphics(new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "The Opponent has Won the Fight"));
    } else if(currentState == States.CONCLUSION && opponent.isDead){
        arena.setInteractionGraphics(new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "The Player has Won the Fight"));
    } else if(currentState == States.PLAYER_ACTION){
        arena.setInteractionGraphics(new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "Fighting opponent"));
    } else if(currentState == States.CONCLUSION) {
        if (currentAction instanceof RunAction) {
            arena.setInteractionGraphics(new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "The player decide not to continue the fight!"));
        }
    }
        arena.draw(canvas);

    }

    
    private boolean actionExecuted = false; // Add this line at the top of your class

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.arena = new ICMonFightArenaGraphics(CAMERA_SCALE_FACTOR, player.properties(), opponent.properties());
        switch (currentState) {
            case INTRODUCTION:
                if (getKeyboard().get(Keyboard.SPACE).isDown()) {
                    currentState = States.ACTION_SELECTION_PLAYER;
                }
                break;
            case ACTION_SELECTION_PLAYER:
                if (actionSelectionGraphics== null) {
                    actionSelectionGraphics = new ICMonFightActionSelectionGraphics(CAMERA_SCALE_FACTOR, getKeyboard(), player.getActions());
                }
                    actionSelectionGraphics.update(deltaTime);
                    ICMonFightAction selectedAction = actionSelectionGraphics.choice();
                    if (selectedAction != null) {
                        currentAction = selectedAction;
                        currentState = States.PLAYER_ACTION;
                    }
                    break;
            case PLAYER_ACTION:
                if (currentAction instanceof RunAction) {
                    currentState = States.CONCLUSION;
                } else {
                    player.attackPokemon(opponent);
                    if (opponent.isDead) {
                        currentState = States.CONCLUSION;
                    } else {
                        currentState = States.OPPONENT_ACTION;
                    }
                }
                break;
            case OPPONENT_ACTION:
                opponent.attackPokemon(player);
                if (player.isDead) {
                    currentState = States.CONCLUSION;
                } else {
                    currentState = States.ACTION_SELECTION_PLAYER;
                }
                break;

            case CONCLUSION:
            if (getKeyboard().get(Keyboard.SPACE).isDown()) {
                end();
                currentAction = null; // Reset the current action
                actionExecuted = false; // Reset the action executed flag
            }
                    break;
                }

        if(!hasRequestedPause) {
            getOwner().requestPause();
            hasRequestedPause = true;
    }


        if(isPaused()){
           // System.out.println("fight paused");

        }



    }
}

