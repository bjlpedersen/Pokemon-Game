package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;

public class LeaveAreaAction implements Action{
    Pokemon pokemon;

    public LeaveAreaAction(Pokemon pokemon) {
        this.pokemon = pokemon;
    }
    @Override
    public void perform() {
        pokemon.leaveArea();
    }
}
