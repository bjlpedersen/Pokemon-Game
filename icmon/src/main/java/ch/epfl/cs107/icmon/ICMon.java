package ch.epfl.cs107.icmon;
import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.maps.Town;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterinAreaAction;
import ch.epfl.cs107.icmon.gamelogic.events.CollectItemEvent;
import ch.epfl.cs107.icmon.gamelogic.events.EndOfTheGameEvent;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.areagame.AreaGame;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;



/**
 * ???
 */
public final class ICMon extends AreaGame {

    /** ??? */
    public final static float CAMERA_SCALE_FACTOR = 13.f;
    /** ??? */
    private final String[] areas = {"town"};
    /** ??? */
    private ICMonPlayer player;
    /** ??? */
    private int areaIndex;

    private ArrayList<ICMonEvent> activeEvents = new ArrayList<>();

    private ArrayList<ICMonEvent> completedEvents = new ArrayList<>();

    private ArrayList<ICMonEvent> newEvents = new ArrayList<>();


    private ICMonGameState gameState;

    private ICMonEventManager eventManager;

    public ICMonEventManager getEventManager(){
        return eventManager;
    }

    /**
     * ???
     */
    private void createAreas() {
        addArea(new Town());
        System.out.println("Areas created");
    }

    /**
     * ???
     * @param window (Window): display context. Not null
     * @param fileSystem (FileSystem): given file system. Not null
     * @return ???
     */
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            eventManager = new ICMonEventManager();
            createAreas();
            areaIndex = 0;
            initArea(areas[areaIndex]);
            ICBall ball = new ICBall(getCurrentArea(), new DiscreteCoordinates(6,6));
            ICShopAssistant icShopAssistant = new ICShopAssistant(getCurrentArea(), Orientation.DOWN, new DiscreteCoordinates(8, 8));
            CollectItemEvent collectItemEvent = createCollectItemEvent(ball);
            createEndOfGameEvent(icShopAssistant, collectItemEvent);

            return true;
        }
        
        return false;
    }

    private void createEndOfGameEvent(ICShopAssistant shopAssistant, ICMonEvent otherEvent) {
        // Creates a EndOfTheGame event and registers it in the area
        new LogAction("EndOfTheGame event started").perform();
        RegisterinAreaAction registerEndOfGame = new RegisterinAreaAction(getCurrentArea(), shopAssistant, "EndOfTheGame event started");
        new EndOfTheGameEvent(player, eventManager, shopAssistant).onStart(otherEvent);
        new LogAction("EndOfTheGame event registered").perform();
    }


    private CollectItemEvent createCollectItemEvent(ICMonItem item){
        // Creates a CollectItem event and registers it in the area
        new LogAction("CollectItem event started").perform();
        new RegisterinAreaAction(getCurrentArea(), item, "CollectItem event started");
        CollectItemEvent collectItemEvent = new CollectItemEvent(item, eventManager, player);
        collectItemEvent.onStart();
        System.out.println("Ball collection registered");
        return collectItemEvent;
    }

    public class ICMonGameState {
        private ICMonGameState(){
            
        }

        void acceptInteraction(Interactable interactable, boolean isCellInteraction){
            for(var event : ICMon.this.activeEvents){
                interactable.acceptInteraction(event, isCellInteraction);
            }
        }


    }


    

    /**
     * ???
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        //switchArea();

        for(ICMonEvent event : completedEvents){
            if(event.getCompleted()){
                activeEvents.remove(event);
                completedEvents.add(event);
        }
        for(ICMonEvent newEvent : newEvents){
            if(!activeEvents.contains(newEvent)){
                activeEvents.add(newEvent);
            }
        }
        completedEvents.clear();
        newEvents.clear();

    }
        super.update(deltaTime);
        Keyboard keyboard = getWindow().getKeyboard();
        if(keyboard.get(Keyboard.R).isDown()){
            begin(getWindow(), getFileSystem());
        
        }
    }
    public ICMonGameState getGameState(){
        return gameState;
    }

    /**
     * ???
     */
    @Override
    public void end() {

    }

    /**
     * ???
     * @return ???
     */
    @Override
    public String getTitle() {
        return "ICMon";
    }

    /**
     * ???
     * @param areaKey ???
     */
    private void initArea(String areaKey) {
        ICMonArea area = (ICMonArea) setCurrentArea(areaKey, true);
        DiscreteCoordinates coords = area.getPlayerSpawnPosition();
        player = new ICMonPlayer(this, area, Orientation.DOWN, coords, "actors/player");
        player.enterArea(area, coords);
        player.centerCamera();
        
    }

    /**
     * ???
     */
    private void switchArea() {
        player.leaveArea();
        areaIndex = (areaIndex == 0) ? 1 : 0;
        ICMonArea currentArea = (ICMonArea) setCurrentArea(areas[areaIndex], false);
        player.enterArea(currentArea, currentArea.getPlayerSpawnPosition());

    }

    public class ICMonEventManager{

       public ArrayList <ICMonEvent> getCompletedEvents(){
              return completedEvents;
       }
       public void addCompletedEvents(ICMonEvent event){
           if (!completedEvents.contains(event))completedEvents.add(event);
       }

       public void removeCompletedEvents(ICMonEvent event){
        completedEvents.remove(event);
       }

       public ArrayList <ICMonEvent> getActiveEvents(){
        return activeEvents;
       }


       public void addActiveEvent(ICMonEvent event){
        if(!activeEvents.contains(event))activeEvents.add(event);
       }
       public void removeActiveEvent(ICMonEvent event){
        activeEvents.remove(event);
       }

       


    }


    



}