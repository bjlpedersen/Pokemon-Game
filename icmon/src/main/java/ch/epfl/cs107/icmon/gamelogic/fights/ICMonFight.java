package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;

public class ICMonFight extends PauseMenu {
    private float counter = 5.f;

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
            counter -= deltaTime;
            if(counter <= 0){
                System.out.println("fight ended");
                end();
            }

    }
}
