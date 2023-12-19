package ch.epfl.cs107.icmon.actor.pokemon;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.gamelogic.events.PokemonFightEvent;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.engine.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.RegionOfInterest;

/**
 * ???
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 */
public abstract class Pokemon extends ICMonActor implements ICMonFightableActor{

    final String name;
    int hp;
    final int maxHp;
    int damage;
    Sprite sprite;
    boolean isDead = false;

    public Pokemon(String name, int hp, int maxHp, int damage, String spriteName, Area owner, Orientation orientation, DiscreteCoordinates coordinates) {
        super(owner, orientation, coordinates);
        this.name = name;
        this.hp = maxHp;
        this.maxHp = maxHp;
        this.damage = damage;
        this.sprite = new RPGSprite("pokemon/" + spriteName, 1, 1, this);
    }
    /**
     * @author Hamza REMMAL (hamza.remmal@epfl.ch)
     */
    public final class PokemonProperties {

        public String name(){
            return null;
        }

        public float hp(){
            return 0f;
        }

        public float maxHp(){
            return 0f;
        }

        public int damage(){
            return 0;
        }


    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, true);
        System.out.println("interacting with pokemon");
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }
    @Override
    public boolean isCellInteractable() {
        return true;
    }
    @Override
    public boolean takeCellSpace() {
        return false;
    }

    public void updateHealth(){
        hp -= damage;

        if (hp < 0){
            hp = 0;
        }
        if(hp == 0){
            isDead = true;
        }
    }


}