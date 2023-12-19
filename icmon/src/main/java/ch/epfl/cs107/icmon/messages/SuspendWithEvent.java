package ch.epfl.cs107.icmon.messages;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.gamelogic.events.PokemonFightEvent;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.play.engine.PauseMenu;

public class SuspendWithEvent extends GamePlayMessage{
    private PokemonFightEvent event;

    public SuspendWithEvent(PokemonFightEvent event){
        this.event = event;
    }

    public void process(ICMon icMon){
        System.out.println("suspension of ongoing events due to a combat event");
        new ICMonFight();
    }
}
