package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.play.areagame.area.Area;

public class RegisterinAreaAction implements Action {
    private Area area;
    private ICMonItem item;
    private ICMonActor actor;
    private String message;
    private Pokemon pokemon1;
    private Pokemon pokemon2;

    public RegisterinAreaAction(Area area, ICMonItem item, String message) {
        this.area = area;
        this.item = item;
        this.message = message;
        this.register();
    }
    public RegisterinAreaAction(Area area, ICMonActor actor, String message) {
        this.area = area;
        this.actor = actor;
        this.message = message;
        this.register();
    }

    public RegisterinAreaAction(Area area, Pokemon pokemon1, Pokemon pokemon2) {
        this.area = area;
        this.pokemon1 = pokemon1;
        this.pokemon2 = pokemon2;
        this.register();
    }

    public void register() {
        if (item != null) {
            area.registerActor(item);
        } else if (actor != null && !(actor instanceof Pokemon)){
            area.registerActor(actor);
        } else if (pokemon1 != null) {
            area.registerActor(pokemon1);
            if (pokemon2 != null) {
                area.registerActor(pokemon2);
            }
        }
    }

    @Override
    public void perform() {
        System.out.println(message);
    }
}
