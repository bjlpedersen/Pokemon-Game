package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.pokemon.ICMonFightableActor;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.actions.LeaveAreaAction;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;

public class PokemonFightEvent extends ICMonEvent {
    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private ICMon.ICMonEventManager eventManager;
    private ICMonActor mainCharacter;

    public PokemonFightEvent(ICMonActor mainCharacter, ICMon.ICMonEventManager eventManager, Pokemon pokemon1, Pokemon pokemon2) {
        super(mainCharacter, eventManager);
        this.pokemon1 = pokemon1;
        this.pokemon2 = pokemon2;
        this.fight();
    }


    public Pokemon getPokemon1() {
        return pokemon1;
    }

    public Pokemon getPokemon2() {
        return pokemon2;
    }

    public void onComplete() {
        System.out.println("PokemonFightEvent completed");
        new LeaveAreaAction(pokemon2);
    }

    private ICMonFight fight() {
        return new ICMonFight(pokemon1, pokemon2);
    }

    public ICMonActor getMainCharacter() {
        return mainCharacter;
    }

    public ICMon.ICMonEventManager getEventManager() {
        return eventManager;
    }
}
