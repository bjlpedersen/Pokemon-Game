package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.area.maps.Arena;
import ch.epfl.cs107.icmon.area.maps.Lab;
import ch.epfl.cs107.icmon.graphics.ICMonFightArenaGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightInteractionGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightTextGraphics;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class ICMonFight extends PauseMenu {
    private boolean isPaused = false;
    private float counter = 5.f;
    private boolean hasRequestedPause = false;
    private States currentState;
    private Pokemon player;
    private Pokemon opponent;

    


    private enum States {
        INTRODUCTION,
        CONCLUSION,
        COUNTER_HANDLING;
    }

    public ICMonFight(Pokemon player, Pokemon opponent) {
        super();
        currentState = States.INTRODUCTION;
        this.player = player;
        this.opponent = opponent;
    }

    public boolean isPaused() {
        System.out.println(getCounter());
        System.out.println(keyReleased(Keyboard.SPACE));
        return counter < 0;
    }

    public float getCounter() {
        return counter;
    }

    @Override
    public void drawMenu(Canvas canvas) {
        ICMonFightArenaGraphics arena = new ICMonFightArenaGraphics(CAMERA_SCALE_FACTOR, player.properties(), opponent.properties());
        if (currentState == States.INTRODUCTION) {
            arena.setInteractionGraphics(new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "Welcome to the Fight"));
            arena.draw(canvas);
        } else if(currentState == States.CONCLUSION) {
            
            arena.setInteractionGraphics(new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "Good Fight"));

            arena.draw(canvas);
            //arena = new ICMonFightArenaGraphics(CAMERA_SCALE_FACTOR, null, null); // crashes the game 
        }
        else {
            new ICMonFightArenaGraphics(CAMERA_SCALE_FACTOR, player.properties(), opponent.properties());   
            arena.removeInteractionGraphics();
            arena.draw(canvas);
        }
        }



    public boolean keyReleased(int key) {
        // Check if the space key is released

        return !getKeyboard().get(Keyboard.SPACE).isDown();
    }
    private void requestResume() {
        this.isPaused = false;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        switch(currentState) {
            case INTRODUCTION:
                System.out.println("Welcome to the fight!");
                if (getKeyboard().get(Keyboard.SPACE).isDown()) {
                    currentState = States.COUNTER_HANDLING;
                    this.isPaused = true;
                }
                break;
            case COUNTER_HANDLING:
                counter -= deltaTime;
                System.out.println(counter);
                if(getCounter() <= 0){
                    counter = 0;
                currentState = States.CONCLUSION;
                
                }
                    
                    

                //}
            case CONCLUSION:
                if (currentState == States.CONCLUSION) {

                    this.end();
                   // getOwner().switchArea("lab",(or,4));
                

                }
                break;


        }

//        if (currentState == States.INTRODUCTION) {
//            System.out.println("Welcome to the fight!");
//            if (keyReleased(Keyboard.SPACE)) {
//                currentState = States.COUNTER_HANDLING;
//            }
//            currentState = States.COUNTER_HANDLING;
//        }
    
        if(!hasRequestedPause) { 
            getOwner().requestPause();
            hasRequestedPause = true;
        }
    
        if(isPaused()){
            counter -= deltaTime;
            System.out.println("fight paused");
            System.out.println(getCounter());
        }
    
        if(getCounter() <= 0){
            System.out.println("fight ended");
            getOwner().requestResume();
        }
    }
}
