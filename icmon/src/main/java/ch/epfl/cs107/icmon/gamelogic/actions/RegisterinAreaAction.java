package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.play.areagame.area.Area;

public class RegisterinAreaAction implements Action {
    private Area area;
    private ICMonItem item;
    private ICMonActor actor;
    private String message;

    public RegisterinAreaAction(Area area, ICMonItem item, String message) {
        this.area = area;
        this.item = item;
        this.message = message;
    }
    public RegisterinAreaAction(Area area, ICMonActor actor, String message) {
        this.area = area;
        this.actor = actor;
        this.message = message;
    }

    public void register() {
        if (item != null) {
            area.registerActor(item);
        } else {
            area.registerActor(actor);
        }
    }

    @Override
    public void perform() {
        System.out.println(message);
    }
}
