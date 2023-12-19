package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.graphics.ICMonFightArenaGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightInteractionGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightTextGraphics;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class ICMonFight extends PauseMenu {
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
        return counter > 0;
    }

    public float getCounter() {
        return counter;
    }

    @Override
    public void drawMenu(Canvas canvas) {
        ICMonFightArenaGraphics arena = new ICMonFightArenaGraphics(CAMERA_SCALE_FACTOR, player.properties(), opponent.properties());
        arena.setInteractionGraphics(new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "hello world"));
        arena.draw(canvas);
    }


    public boolean keyReleased(int key) {
        // Check if the space key is released
        if (key == Keyboard.SPACE) {
            return true;
        }
        return false;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        switch(currentState) {
            case INTRODUCTION:
                System.out.println("Welcome to the fight!");
                if (keyReleased(Keyboard.SPACE)) {
                    currentState = States.COUNTER_HANDLING;
                }
                break;
            case COUNTER_HANDLING:
                counter -= deltaTime;
                System.out.println(counter);
                if(getCounter() <= 0){
                    getOwner().requestResume();
                    currentState = States.CONCLUSION;
                }
            case CONCLUSION:
                System.out.println("Good Fight!");
                if (keyReleased(Keyboard.SPACE)) {
                    this.end();
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
