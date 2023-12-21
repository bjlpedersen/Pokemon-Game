package ch.epfl.cs107.icmon.actor.pokemon.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;

public class RunAction implements ICMonFightAction {
    public RunAction() {
        
    }
    public boolean execute(Pokemon target){
        return false;
    }
    public String name(){
        return "Run";
    }
    
}
