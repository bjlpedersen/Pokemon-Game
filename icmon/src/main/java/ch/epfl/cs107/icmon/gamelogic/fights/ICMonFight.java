package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;

public class ICMonFight extends PauseMenu {
    private float counter = 5.f;
    private boolean hasRequestedPause = false;

    public ICMonFight() {
        super();
    }

    public boolean isPaused() {
        return counter > 0;
    }

    public float getCounter() {
        return counter;
    }

    @Override
    public void drawMenu(Canvas canvas) {
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    
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
