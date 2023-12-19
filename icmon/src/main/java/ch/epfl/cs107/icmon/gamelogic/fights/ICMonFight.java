package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;

public class ICMonFight extends PauseMenu {
    private float counter = 5.f;

    public ICMonFight(Pokemon pokemon1, Pokemon pokemon2) {
        super();
    }

    public boolean isRunning() {
        return counter > 0;
    }

    @Override
    public void drawMenu(Canvas canvas) {
    }

    @Override
    public void update(float deltaTime) {
            super.update(deltaTime);
            counter -= deltaTime;
    }
}
