package ch.epfl.cs107.icmon;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.area.maps.*;
import ch.epfl.cs107.icmon.gamelogic.events.*;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.icmon.messages.GamePlayMessage;
import ch.epfl.cs107.icmon.actor.Door;
import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterinAreaAction;
import ch.epfl.cs107.play.areagame.AreaGame;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;
import java.util.ArrayList;
import java.util.List;
import ch.epfl.cs107.icmon.messages.SuspendWithEvent;



/**
 * ???
 */
public final class ICMon extends AreaGame {
    /** ??? */
    public final static float CAMERA_SCALE_FACTOR = 13.f;
    /** ??? */
    private final String[] areas = {"town", "lab"};
    /** ??? */
    private ICMonPlayer player;
    /** ??? */
    private int areaIndex;
    private ArrayList<ICMonEvent> activeEvents = new ArrayList<>();
    private ArrayList<ICMonEvent> completedEvents = new ArrayList<>();
    private ArrayList<ICMonEvent> newEvents = new ArrayList<>();
    private ICMonGameState gameState;
    private ICMonEventManager eventManager;
    private GamePlayMessage gamePlayMessage;
    private List<GamePlayMessage> mailbox = new ArrayList<>();
    private Town town;
    private Lab lab;
    private Arena arena;
    private House house;
    private Shop shop;
    public ICMonFight pauseMenu;
    private boolean isPaused = false;
    private CollectItemEvent collectItemEvent;
    private EndOfTheGameEvent endOfTheGameEvent;
    private Pokemon opponent;
    

    /**
     * ???
     */
    private void createAreas() {
        this.town = new Town();
        addArea(town);
        this.lab = new Lab();
        addArea(lab);
        this.arena = new Arena();
        addArea(arena);
        this.house = new House();
        addArea(house);
        this.shop = new Shop();
        addArea(shop);


    }

    public void setOpponent(Pokemon opponent){
        this.opponent = opponent;
    }

    public Pokemon getOpponent(){
        return this.opponent;
    }

    public Arena getArena(){
        return arena;
    }

    public Town getTown(){
        return town;
    }

    public Shop getShop(){
        return shop;
    }
    public House getHouse(){
        return house;
    }

    public CollectItemEvent getCollectItemEvent(){
        return collectItemEvent;
    }

    public EndOfTheGameEvent getEndOfTheGameEvent(){
        return endOfTheGameEvent;
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
            new IntroductionEvent(player, eventManager);
            eventManager = new ICMonEventManager();
            createAreas();
            areaIndex = 0;
            gameState = new ICMonGameState();
            gamePlayMessage = new GamePlayMessage();
            initArea("house");
            new Door(getCurrentArea(), "lab", new DiscreteCoordinates(6,2), new DiscreteCoordinates(15, 24));

            pauseMenu = new ICMonFight(player.getCollection().get(0), opponent);
//            player.setIsInDialog(true);
            createFightEvent(arena.getBulbizarre(), arena.getLatios(), player, eventManager);
            this.collectItemEvent = new CollectItemEvent(eventManager, player);
            this.endOfTheGameEvent = new EndOfTheGameEvent(player, eventManager, shop.getICShopAssistant());
            return true;
        }
        
        return false;
    }

    private void createFightEvent(Pokemon pokemon1, Pokemon pokemon2,ICMonActor player, ICMon.ICMonEventManager eventManager){
        new LogAction("Fight event started").perform();
        new RegisterinAreaAction(getCurrentArea(), pokemon1, "Fight event started"); // making 2 bulbasaurs
        new PokemonFightEvent(player, eventManager, pokemon1, pokemon2).onStart();
        new LogAction("Fight event registered").perform();
        new PokemonFightEvent(player, eventManager, pokemon1, pokemon2);
    }


    public class ICMonGameState {
        public ICMonGameState(){
            
        }

        public void acceptInteraction(Interactable interactable, boolean isCellInteraction){
            for(var event : ICMon.this.activeEvents){
                interactable.acceptInteraction(event, isCellInteraction);
            }
        }
        public void send(GamePlayMessage message) {
            mailbox.add(message);
        }
    }
    

    private void events() {
        ICMonEvent initialEvent = player.getIntroductionEvent();
        ICMonEvent[] chainEvents = player.getChainEvents();
        new ICMonChainedEvent(player, eventManager, initialEvent, chainEvents);
    }


    /**
     * ???
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        events();
        for (GamePlayMessage message : mailbox) {
            if (message instanceof SuspendWithEvent) {
                this.setPauseMenu(pauseMenu);
                pauseMenu.update(deltaTime);              
//
            }
            message.process(this);

        }
        mailbox.clear();


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
        this.player = new ICMonPlayer(this, area, Orientation.DOWN, coords, "actors/player", arena.getPlayerStarter());
        player.enterArea(area, coords);
        player.centerCamera();
        
    }

    /**
     * ???
     */
    public void switchArea(String s, DiscreteCoordinates d) {
        player.leaveArea();
        ICMonArea currentArea = (ICMonArea) setCurrentArea(s, false);
        player.enterArea(currentArea, d);

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
    public ICMonEventManager getEventManager(){
        return eventManager;
    }
}