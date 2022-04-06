package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;

import org.codefx.libfx.listener.handle.ListenerHandle;
import org.codefx.libfx.listener.handle.ListenerHandles;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import unsw.Buildings.VampireCastleBuilding;
import unsw.Character.Character;
import unsw.Character.Soldier;
import unsw.Enemies.BasicEnemy;
import unsw.Enemies.ElanMuske;
import unsw.Items.BuildingCard.Card;
import java.util.Iterator;

import unsw.Music.Music;
import unsw.Buildings.Building;
import java.util.EnumMap;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;

import java.io.File;
import java.io.IOException;

import unsw.Items.Armour;
import unsw.Items.Equipment;
import unsw.Items.Helmet;
import unsw.Items.Item;
import unsw.Items.Shield;
import unsw.Items.Sword;
import unsw.Items.Weapon;

/**
 * the draggable types. If you add more draggable types, add an enum value here.
 * This is so we can see what type is being dragged.
 */
enum DRAGGABLE_TYPE {
    CARD, ITEM
}

/**
 * A JavaFX controller for the world.
 * 
 * All event handlers and the timeline in JavaFX run on the JavaFX application
 * thread:
 * https://examples.javacodegeeks.com/desktop-java/javafx/javafx-concurrency-example/
 * Note in
 * https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Application.html
 * under heading "Threading", it specifies animation timelines are run in the
 * application thread. This means that the starter code does not need locks
 * (mutexes) for resources shared between the timeline KeyFrame, and all of the
 * event handlers (including between different event handlers). This will make
 * the game easier for you to implement. However, if you add time-consuming
 * processes to this, the game may lag or become choppy.
 * 
 * If you need to implement time-consuming processes, we recommend: using Task
 * https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Task.html by
 * itself or within a Service
 * https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Service.html
 * 
 * Tasks ensure that any changes to public properties, change notifications for
 * errors or cancellation, event handlers, and states occur on the JavaFX
 * Application thread, so is a better alternative to using a basic Java Thread:
 * https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm The Service class
 * is used for executing/reusing tasks. You can run tasks without Service,
 * however, if you don't need to reuse it.
 *
 * If you implement time-consuming processes in a Task or thread, you may need
 * to implement locks on resources shared with the application thread (i.e.
 * Timeline KeyFrame and drag Event handlers). You can check whether code is
 * running on the JavaFX application thread by running the helper method
 * printThreadingNotes in this class.
 * 
 * NOTE: http://tutorials.jenkov.com/javafx/concurrency.html and
 * https://www.developer.com/design/multithreading-in-javafx/#:~:text=JavaFX%20has%20a%20unique%20set,in%20the%20JavaFX%20Application%20Thread.
 * 
 * If you need to delay some code but it is not long-running, consider using
 * Platform.runLater
 * https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Platform.html#runLater(java.lang.Runnable)
 * This is run on the JavaFX application thread when it has enough time.
 */
public class LoopManiaWorldController {

    /**
     * squares gridpane includes path images, enemies, character, empty grass,
     * buildings
     */
    @FXML
    private GridPane squares;

    /**
     * cards gridpane includes cards and the ground underneath the cards
     */
    @FXML
    private GridPane cards;

    /**
     * anchorPaneRoot is the "background". It is useful since anchorPaneRoot
     * stretches over the entire game world, so we can detect dragging of
     * cards/items over this and accordingly update DragIcon coordinates
     */
    @FXML
    private AnchorPane anchorPaneRoot;

    /**
     * equippedItems gridpane is for equipped items (e.g. swords, shield, axe)
     */
    @FXML
    private GridPane equippedItems;
    @FXML
    private ImageView swordSlot;
    @FXML
    private ImageView armourSlot;
    @FXML
    private ImageView shieldSlot;
    @FXML
    private ImageView helmetSlot;
    @FXML
    private GridPane unequippedInventory;

    @FXML
    private Rectangle HealthBar;

    @FXML
    private Rectangle GoldBar;

    @FXML
    private Rectangle XPBar;

    @FXML
    private Text healthNum;

    @FXML
    private Text goldNum;

    @FXML
    private Text XPNum;

    // all image views including tiles, character, enemies, cards... even though
    // cards in separate gridpane...
    private List<ImageView> entityImages;

    /**
     * when we drag a card/item, the picture for whatever we're dragging is set here
     * and we actually drag this node
     */
    private DragIcon draggedEntity;

    private boolean isPaused;
    private LoopManiaWorld world;

    /**
     * runs the periodic game logic - second-by-second moving of character through
     * maze, as well as enemies, and running of battles
     */
    private Timeline timeline;

    private Stage helpPage;
    private Stage gameOverPage;
    private Stage gameWinPage;

    /**
     * the image currently being dragged, if there is one, otherwise null. Holding
     * the ImageView being dragged allows us to spawn it again in the drop location
     * if appropriate.
     */
    private ImageView currentlyDraggedImage;

    /**
     * null if nothing being dragged, or the type of item being dragged
     */
    private DRAGGABLE_TYPE currentlyDraggedType;

    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dropped over its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dragged over the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragOver;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dropped in the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dragged into the boundaries of its appropriate
     * gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragEntered;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dragged outside of the boundaries of its
     * appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragExited;

    /**
     * object handling switching to the main menu
     */
    private MenuSwitcher mainMenuSwitcher;
    private Item itemTryToSell;
    /**
     * sellButton
     */
    @FXML
    private Button sellButton;
    @FXML
    private ChoiceBox<String> shopList;
    @FXML
    private Button buyButton;

    private Item itemToBuy;
    @FXML
    private Label shopNotes;
    private double previousHealth;
    private double previousGold;
    private double previousXP;

    @FXML
    private GridPane soldiersBoard;
    private int soldiersNum = 0;

    /**
     * @param world           world object loaded from file
     * @param initialEntities the initial JavaFX nodes (ImageViews) which should be
     *                        loaded into the GUI
     */
    public LoopManiaWorldController(LoopManiaWorld world, List<ImageView> initialEntities) {
        this.world = world;
        entityImages = new ArrayList<>(initialEntities);

        currentlyDraggedImage = null;
        currentlyDraggedType = null;

        // initialize them all...
        gridPaneSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragOver = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragEntered = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragExited = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);

    }

    /**
     * initialize the game
     * 
     * @throws IOException
     */
    @FXML
    public void initialize() throws IOException {

        Image pathTilesImage = new Image((new File("src/images/32x32GrassAndDirtPath.png")).toURI().toString());
        Image inventorySlotImage = new Image((new File("src/images/empty_slot.png")).toURI().toString());
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);
        try {
            new Music();
        } catch (Exception e) {
            System.out.println("no music");
        }
        // Add the ground first so it is below all other entities (inculding all the
        // twists and turns)
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                ImageView groundView = new ImageView(pathTilesImage);
                groundView.setViewport(imagePart);
                squares.add(groundView, x, y);
            }
        }

        // load entities loaded from the file in the loader into the squares gridpane
        for (ImageView entity : entityImages) {
            squares.getChildren().add(entity);
        }

        // add the ground underneath the cards
        for (int x = 0; x < world.getWidth(); x++) {
            ImageView groundView = new ImageView(pathTilesImage);
            groundView.setViewport(imagePart);
            cards.add(groundView, x, 0);
        }

        // add the empty slot images for the unequipped inventory
        for (int x = 0; x < LoopManiaWorld.unequippedInventoryWidth; x++) {
            for (int y = 0; y < LoopManiaWorld.unequippedInventoryHeight; y++) {
                ImageView emptySlotView = new ImageView(inventorySlotImage);
                unequippedInventory.add(emptySlotView, x, y);
            }
        }

        // add sword when initializing
        swordSlot.setImage(new Sword().getImage());
        // add hero castle
        onLoad(world.getHeroCastle());
        soldiersBoard.setGridLinesVisible(true);
        // set shop function button invisible
        setShopTagInvisible();
        shopListInit();
        // create the draggable icon
        draggedEntity = new DragIcon();
        draggedEntity.setVisible(false);
        draggedEntity.setOpacity(0.7);
        anchorPaneRoot.getChildren().add(draggedEntity);

        setupHelpPage();
        setupGameOverPage();
        setupGameWinPage();

    }

    /**
     * create and run the timer
     */
    public void startTimer() {
        setShopTagInvisible();
        System.out.println("starting timer");
        isPaused = false;
        // trigger adding code to process main game logic to queue. JavaFX will target
        // framerate of 0.3 seconds
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), event -> {

            Character tempCharacter = world.getCharacter();
            previousHealth = tempCharacter.getHealth() * 1.0;
            previousGold = tempCharacter.getGold() * 1.0;
            previousXP = tempCharacter.getExperience() * 1.0;

            List<BasicEnemy> enemiesListStash = enemiesListCopy(world.getEnemiesList());
            List<Entity> beforeEntityList = world.getRandomItem();
            world.runTickMoves();
            List<Entity> randomItem = world.getRandomItem();

            for (Entity en : randomItem) {
                onLoadRandomItem(en);
            }
            updateUnequippedInventory();
            updateRandomItem(beforeEntityList, randomItem);

            List<BasicEnemy> defeatedEnemies = world.runBattles();
            for (BasicEnemy e : defeatedEnemies) {
                try {
                    reactToEnemyDefeat(e);
                    if (e instanceof ElanMuske) {
                        gameWinPage.show();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            soldiersNum = world.getSoldiersNum();
            updateSolidersFrontend();
            updateBar(tempCharacter);

            List<BasicEnemy> newEnemies = getNewEnemies(enemiesListStash);

            for (BasicEnemy newEnemy : newEnemies) {
                onLoad(newEnemy);
            }
            updateEquippedBoard(world.getCharacter());

            if (checkGameWin()) {
                pause();
                gameWinPage.show();
            }

            if (checkGameEnd()) {
                pause();
                gameOverPage.show();
            }

            if (world.ifAtCastle())
                shopPage();

            printThreadingNotes("HANDLED TIMER");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * pause the execution of the game loop the human player can still drag and drop
     * items during the game pause
     */
    public void pause() {
        isPaused = true;
        System.out.println("pausing");
        timeline.stop();
    }

    public void terminate() {
        pause();
    }

    /**
     * pair the entity an view so that the view copies the movements of the entity.
     * add view to list of entity images
     * 
     * @param entity backend entity to be paired with view
     * @param view   frontend imageview to be paired with backend entity
     */
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entityImages.add(view);
    }

    /**
     * load a vampire card from the world, and pair it with an image in the GUI
     */
    private void loadCard(Card card) throws Exception {
        // TODO = load more types of card
        onLoad(world.loadCard(card));
    }

    /**
     * run GUI events after an enemy is defeated, such as spawning
     * items/experience/gold
     * 
     * @param enemy defeated enemy for which we should react to the death of
     */
    private void reactToEnemyDefeat(BasicEnemy enemy) throws Exception {
        // react to character defeating an enemy
        // in starter code, spawning extra card/weapon...
        List<Entity> loots = world.lootDrop(enemy);

        for (Entity entity : loots) {
            if (entity instanceof Item) {
                Item item = (Item) entity;
                onLoad(item);
            }
            if (entity instanceof Card) {
                Card card = (Card) entity;
                loadCard(card);
            }
        }

    }

    /**
     * load a vampire castle card into the GUI. Particularly, we must connect to the
     * drag detection event handler, and load the image into the cards GridPane.
     * 
     * @param vampireCastleCard
     */
    private void onLoad(Card card) {
        ImageView view = new ImageView(card.getImage());
        // FROM
        // https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
        // note target setOnDragOver and setOnDragEntered defined in initialize method
        addDragEventHandlers(view, DRAGGABLE_TYPE.CARD, cards, squares);

        addEntity(card, view);
        cards.getChildren().add(view);
    }

    /**
     * load a sword into the GUI. Particularly, we must connect to the drag
     * detection event handler, and load the image into the unequippedInventory
     * GridPane.
     * 
     * @param sword
     */
    private void onLoad(Entity entity) {
        ImageView view = new ImageView(entity.getImage());
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedItems);
        addEntity(entity, view);
        unequippedInventory.getChildren().add(view);
    }

    /**
     * load a random created entity into the GUI. (Gold/Healthpotion)
     * 
     * @param entity the gold or health potion need to load.
     */
    private void onLoadRandomItem(Entity entity) {
        ImageView view = new ImageView(entity.getImage());
        addEntity(entity, view);
        squares.getChildren().add(view);
    }

    /**
     * load an enemy into the GUI
     * 
     * @param enemy
     */
    private void onLoad(BasicEnemy enemy) {
        ImageView view = new ImageView(enemy.getImage());
        addEntity(enemy, view);
        squares.getChildren().add(view);
    }

    /**
     * load a building into the GUI
     * 
     * @param building
     */
    private void onLoad(Building building) {
        ImageView view = new ImageView(building.getImage());
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    /**
     * Load a soldier into the GUI
     */
    private void onLoadSoldier() {
        ImageView view = new ImageView(new Soldier().getImage());
        soldiersBoard.add(view, soldiersBoard.getColumnCount() + 1, 0);
    }

    /**
     * Load a equipment into the GUI
     * 
     * @param equipment
     * @pre the equipment with location x and y
     * @post the image load to frontend
     * @invarriant the equippment passed in
     */
    private void onLoad(Equipment equipment) {
        if (equipment == null)
            return;
        Image view = equipment.getImage();
        if (equipment instanceof Weapon) {
            swordSlot.setImage(view);
            return;
        }
        if (equipment instanceof Armour) {
            armourSlot.setImage(view);
            return;
        }
        if (equipment instanceof Shield) {
            shieldSlot.setImage(view);
            return;
        }
        if (equipment instanceof Helmet) {
            helmetSlot.setImage(view);
            return;
        }
    }

    /**
     * add drag event handlers for dropping into gridpanes, dragging over the
     * background, dropping over the background. These are not attached to invidual
     * items such as swords/cards.
     * 
     * @param draggableType  the type being dragged - card or item
     * @param sourceGridPane the gridpane being dragged from
     * @param targetGridPane the gridpane the human player should be dragging to
     *                       (but we of course cannot guarantee they will do so)
     */
    private void buildNonEntityDragHandlers(DRAGGABLE_TYPE draggableType, GridPane sourceGridPane,
            GridPane targetGridPane) {
        // TODO = be more selective about where something can be dropped
        // for example, in the specification, villages can only be dropped on path,
        // whilst vampire castles cannot go on the path

        gridPaneSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /*
                 * you might want to design the application so dropping at an invalid location
                 * drops at the most recent valid location hovered over, or simply allow the
                 * card/item to return to its slot (the latter is easier, as you won't have to
                 * store the last valid drop location!)
                 */
                if (currentlyDraggedType == draggableType) {
                    // problem = event is drop completed is false when should be true...
                    // https://bugs.openjdk.java.net/browse/JDK-8117019
                    // putting drop completed at start not making complete on VLAB...

                    // Data dropped
                    // If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if (node != targetGridPane && db.hasImage()) {

                        Integer cIndex = GridPane.getColumnIndex(node);
                        Integer rIndex = GridPane.getRowIndex(node);
                        int x = cIndex == null ? 0 : cIndex;
                        int y = rIndex == null ? 0 : rIndex;
                        // Places at 0,0 - will need to take coordinates once that is implemented
                        ImageView image = new ImageView(db.getImage());

                        int nodeX = GridPane.getColumnIndex(currentlyDraggedImage);
                        int nodeY = GridPane.getRowIndex(currentlyDraggedImage);
                        switch (draggableType) {
                            case CARD:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // TODO = spawn a building here of different types
                                Building newBuilding = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                onLoad(newBuilding);
                                break;
                            case ITEM:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // TODO = spawn an item in the new location. The above code for spawning a
                                // building will help, it is very similar
                                Equipment equipment = world.convertUnequippedItemToEquipped(nodeX, nodeY, x, y);
                                onLoad(equipment);
                                removeItemByCoordinates(nodeX, nodeY);
                                // targetGridPane.add(image, x, y, 1, 1);
                                break;
                            default:
                                break;
                        }

                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                        printThreadingNotes("DRAG DROPPED ON GRIDPANE HANDLED");
                    }
                }
                event.setDropCompleted(true);
                // consuming prevents the propagation of the event to the anchorPaneRoot (as a
                // sub-node of anchorPaneRoot, GridPane is prioritized)
                // https://openjfx.io/javadoc/11/javafx.base/javafx/event/Event.html#consume()
                // to understand this in full detail, ask your tutor or read
                // https://docs.oracle.com/javase/8/javafx/events-tutorial/processing.htm
                event.consume();
            }
        });

        // this doesn't fire when we drag over GridPane because in the event handler for
        // dragging over GridPanes, we consume the event
        anchorPaneRootSetOnDragOver.put(draggableType, new EventHandler<DragEvent>() {
            // https://github.com/joelgraff/java_fx_node_link_demo/blob/master/Draggable_Node/DraggableNodeDemo/src/application/RootLayout.java#L110
            @Override
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType) {
                    if (event.getGestureSource() != anchorPaneRoot && event.getDragboard().hasImage()) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                }
                if (currentlyDraggedType != null) {
                    draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                }
                event.consume();
            }
        });

        // this doesn't fire when we drop over GridPane because in the event handler for
        // dropping over GridPanes, we consume the event
        anchorPaneRootSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType) {
                    // Data dropped
                    // If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if (node != anchorPaneRoot && db.hasImage()) {
                        // Places at 0,0 - will need to take coordinates once that is implemented
                        currentlyDraggedImage.setVisible(true);
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        removeDraggableDragEventHandlers(draggableType, targetGridPane);

                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                    }
                }
                // let the source know whether the image was successfully transferred and used
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }

    /**
     * remove the card from the world, and spawn and return a building instead where
     * the card was dropped
     * 
     * @param cardNodeX     the x coordinate of the card which was dragged, from 0
     *                      to width-1
     * @param cardNodeY     the y coordinate of the card which was dragged (in
     *                      starter code this is 0 as only 1 row of cards)
     * @param buildingNodeX the x coordinate of the drop location for the card,
     *                      where the building will spawn, from 0 to width-1
     * @param buildingNodeY the y coordinate of the drop location for the card,
     *                      where the building will spawn, from 0 to height-1
     * @return building entity returned from the world
     */
    private Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX,
            int buildingNodeY) {
        return world.convertCardToBuildingByCoordinates(cardNodeX, cardNodeY, buildingNodeX, buildingNodeY);
    }

    /**
     * remove an item from the unequipped inventory by its x and y coordinates in
     * the unequipped inventory gridpane
     * 
     * @param nodeX x coordinate from 0 to unequippedInventoryWidth-1
     * @param nodeY y coordinate from 0 to unequippedInventoryHeight-1
     */
    private void removeItemByCoordinates(int nodeX, int nodeY) {
        world.removeUnequippedInventoryItemByCoordinates(nodeX, nodeY);
    }

    /**
     * add drag event handlers to an ImageView
     * 
     * @param view           the view to attach drag event handlers to
     * @param draggableType  the type of item being dragged - card or item
     * @param sourceGridPane the relevant gridpane from which the entity would be
     *                       dragged
     * @param targetGridPane the relevant gridpane to which the entity would be
     *                       dragged to
     */
    private void addDragEventHandlers(ImageView view, DRAGGABLE_TYPE draggableType, GridPane sourceGridPane,
            GridPane targetGridPane) {
        view.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                currentlyDraggedImage = view; // set image currently being dragged, so squares setOnDragEntered can
                                              // detect it...
                currentlyDraggedType = draggableType;
                // Drag was detected, start drap-and-drop gesture
                // Allow any transfer node
                Dragboard db = view.startDragAndDrop(TransferMode.MOVE);

                // Put ImageView on dragboard
                ClipboardContent cbContent = new ClipboardContent();
                cbContent.putImage(view.getImage());
                db.setContent(cbContent);
                view.setVisible(false);

                buildNonEntityDragHandlers(draggableType, sourceGridPane, targetGridPane);

                draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                switch (draggableType) {
                    case CARD:
                        draggedEntity.setImage(new VampireCastleBuilding().getImage());
                        break;
                    case ITEM:
                        draggedEntity.setImage(new Sword().getImage());
                        break;
                    default:
                        break;
                }

                draggedEntity.setVisible(true);
                draggedEntity.setMouseTransparent(true);
                draggedEntity.toFront();

                // IMPORTANT!!!
                // to be able to remove event handlers, need to use addEventHandler
                // https://stackoverflow.com/a/67283792
                targetGridPane.addEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_DROPPED,
                        anchorPaneRootSetOnDragDropped.get(draggableType));

                for (Node n : targetGridPane.getChildren()) {
                    // events for entering and exiting are attached to squares children because that
                    // impacts opacity change
                    // these do not affect visibility of original image...
                    // https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
                    gridPaneNodeSetOnDragEntered.put(draggableType, new EventHandler<DragEvent>() {
                        // TODO = be more selective about whether highlighting changes - if it cannot be
                        // dropped in the location, the location shouldn't be highlighted!
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType) {
                                // The drag-and-drop gesture entered the target
                                // show the user that it is an actual gesture target
                                if (event.getGestureSource() != n && event.getDragboard().hasImage()) {
                                    n.setOpacity(0.7);
                                }
                            }
                            event.consume();
                        }
                    });
                    gridPaneNodeSetOnDragExited.put(draggableType, new EventHandler<DragEvent>() {
                        // TODO = since being more selective about whether highlighting changes, you
                        // could program the game so if the new highlight location is invalid the
                        // highlighting doesn't change, or leave this as-is
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType) {
                                n.setOpacity(1);
                            }

                            event.consume();
                        }
                    });
                    n.addEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
                    n.addEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
                }
                event.consume();
            }

        });
    }

    /**
     * remove drag event handlers so that we don't process redundant events this is
     * particularly important for slower machines such as over VLAB.
     * 
     * @param draggableType  either cards, or items in unequipped inventory
     * @param targetGridPane the gridpane to remove the drag event handlers from
     */
    private void removeDraggableDragEventHandlers(DRAGGABLE_TYPE draggableType, GridPane targetGridPane) {
        // remove event handlers from nodes in children squares, from anchorPaneRoot,
        // and squares
        targetGridPane.removeEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));

        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

        for (Node n : targetGridPane.getChildren()) {
            n.removeEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
            n.removeEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
        }
    }

    /**
     * handle the pressing of keyboard keys. Specifically, we should pause when
     * pressing SPACE
     * 
     * @param event some keyboard key press
     * @throws InterruptedException
     * @throws IOException
     */
    @FXML
    public void handleKeyPress(KeyEvent event) throws InterruptedException {
        switch (event.getCode()) {
            case SPACE:
                if (isPaused) {
                    startTimer();
                } else {
                    pause();
                }
                break;
            case H:
                pause();
                break;
            case X:
                pause();
                world.useHealthPotion();
                updateUnequippedInventory();
                updateBar(world.getCharacter());
                Thread.sleep(1000);
                startTimer();
                break;
            case C:
                world.useOneRing();
                updateUnequippedInventory();
                world.getCharacter().getEquippedBoard().setWeapon(new Sword());
                swordSlot.setImage(new Sword().getImage());
                updateEquippedBoard(world.getCharacter());
                updateBar(world.getCharacter());
                startTimer();
                break;
            default:
                break;
        }
    }

    /**
     * <<<<<<< HEAD Set the swithcher of the specific page
     * 
     * @param mainMenuSwitcher the swithcher need to set. ======= set menu switcher
     * 
     * @param mainMenuSwitcher >>>>>>> f59286f... add javadoc
     */
    public void setMainMenuSwitcher(MenuSwitcher mainMenuSwitcher) {
        this.mainMenuSwitcher = mainMenuSwitcher;
    }

    /**
     * this method is triggered when click button to go to main menu in FXML
     * 
     * @throws IOException
     */
    @FXML
    private void switchToMainMenu() throws IOException {
        pause();
        mainMenuSwitcher.switchMenu();
    }

    /**
     * Set a node in a GridPane to have its position track the position of an entity
     * in the world.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the model
     * will automatically be reflected in the view.
     * 
     * note that this is put in the controller rather than the loader because we
     * need to track positions of spawned entities such as enemy or items which
     * might need to be removed should be tracked here
     * 
     * NOTE teardown functions setup here also remove nodes from their GridPane. So
     * it is vital this is handled in this Controller class
     * 
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());

        ChangeListener<Number> xListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        };
        ChangeListener<Number> yListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        };

        // if need to remove items from the equipped inventory, add code to remove from
        // equipped inventory gridpane in the .onDetach part
        ListenerHandle handleX = ListenerHandles.createFor(entity.x(), node)
                .onAttach((o, l) -> o.addListener(xListener)).onDetach((o, l) -> {
                    o.removeListener(xListener);
                    entityImages.remove(node);
                    squares.getChildren().remove(node);
                    cards.getChildren().remove(node);
                    equippedItems.getChildren().remove(node);
                    unequippedInventory.getChildren().remove(node);
                }).buildAttached();
        ListenerHandle handleY = ListenerHandles.createFor(entity.y(), node)
                .onAttach((o, l) -> o.addListener(yListener)).onDetach((o, l) -> {
                    o.removeListener(yListener);
                    entityImages.remove(node);
                    squares.getChildren().remove(node);
                    cards.getChildren().remove(node);
                    equippedItems.getChildren().remove(node);
                    unequippedInventory.getChildren().remove(node);
                }).buildAttached();
        handleX.attach();
        handleY.attach();

        // this means that if we change boolean property in an entity tracked from here,
        // position will stop being tracked
        // this wont work on character/path entities loaded from loader classes
        entity.shouldExist().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obervable, Boolean oldValue, Boolean newValue) {
                handleX.detach();
                handleY.detach();
            }
        });
    }

    /**
     * we added this method to help with debugging so you could check your code is
     * running on the application thread. By running everything on the application
     * thread, you will not need to worry about implementing locks, which is outside
     * the scope of the course. Always writing code running on the application
     * thread will make the project easier, as long as you are not running
     * time-consuming tasks. We recommend only running code on the application
     * thread, by using Timelines when you want to run multiple processes at once.
     * EventHandlers will run on the application thread.
     */
    private void printThreadingNotes(String currentMethodLabel) {
        System.out.println("\n###########################################");
        System.out.println("current method = " + currentMethodLabel);
        System.out.println("In application thread? = " + Platform.isFxApplicationThread());
        System.out.println("Current system time = " + java.time.LocalDateTime.now().toString().replace('T', ' '));
        System.out.println("current mode:" + world.getGameMode());
    }

    /**
     * Setting up the help page.
     * 
     * @throws IOException
     */
    public void setupHelpPage() throws IOException {
        Parent helpRoot = FXMLLoader.load(getClass().getResource("HelpPage.fxml"));
        Scene scene = new Scene(helpRoot);
        helpPage = new Stage();
        helpPage.setTitle("Help Page");
        helpPage.setScene(scene);
    }

    /**
     * Setting up the game over page.
     * 
     * @throws IOException
     */
    public void setupGameOverPage() throws IOException {
        Parent gameoverRoot = FXMLLoader.load(getClass().getResource("gameover.fxml"));
        Scene scene = new Scene(gameoverRoot);
        gameOverPage = new Stage();
        gameOverPage.setTitle("Game over");
        gameOverPage.setScene(scene);
    }

    /**
     * Setting up the game win page.
     * 
     * @throws IOException
     */
    public void setupGameWinPage() throws IOException {
        Parent gamewinRoot = FXMLLoader.load(getClass().getResource("Gamewin.fxml"));
        Scene scene = new Scene(gamewinRoot);
        gameWinPage = new Stage();
        gameWinPage.setTitle("Game Win");
        gameWinPage.setScene(scene);
    }

    /**
     * Update the equippedboard of the specific character.
     * 
     * @param character the specific character need to update equippedboard
     */
    public void updateEquippedBoard(Character character) {
        EquippedBoard equippedBoard = character.getEquippedBoard();
        if (equippedBoard.getWeapon() == null)
            swordSlot.setImage(new Sword().getSlotImage());
        if (equippedBoard.getArmour() == null)
            armourSlot.setImage(new Armour().getSlotImage());
        if (equippedBoard.getHelmet() == null)
            helmetSlot.setImage(new Helmet().getSlotImage());
        if (equippedBoard.getShield() == null)
            shieldSlot.setImage(new Shield().getSlotImage());
    }

    /**
     * Copy the enemies list.
     * 
     * @param list the enemies list need to copy.
     * @return the new copy of the enemies list.
     */
    private List<BasicEnemy> enemiesListCopy(List<BasicEnemy> list) {
        List<BasicEnemy> newList = new ArrayList<>();
        for (BasicEnemy entity : list)
            newList.add(entity);
        return newList;
    }

    /**
     * Get the new enemies need to create.
     * 
     * @param enemyListStash the list of new enemies need to create.
     * @return list of new enemies.
     */
    private List<BasicEnemy> getNewEnemies(List<BasicEnemy> enemyListStash) {
        List<BasicEnemy> newEnemies = new ArrayList<>();
        newEnemies.addAll(world.autoUpdateEnemies());
        List<BasicEnemy> newEnemiesRandomlySpawn = world.possiblySpawnEnemies();
        newEnemies.addAll(newEnemiesRandomlySpawn);
        return newEnemies;
    }

    /**
     * Update the health, gold and XP bar of the chatacter.
     * 
     * @param tempCharacter the specific character need to update bar.
     */
    public void updateBar(Character tempCharacter) {

        Double currentHealth = tempCharacter.getHealth() * 1.0;
        Double currentGold = tempCharacter.getGold() * 1.0;
        Double currentXP = tempCharacter.getExperience() * 1.0;
        System.out.println("current XP: " + currentXP);

        healthNum.setText(Double.toString(currentHealth));
        goldNum.setText(Integer.toString(world.getCharacter().getGold()));
        XPNum.setText(Integer.toString(world.getCharacter().getExperience()));

        if (previousHealth != currentHealth) {
            Double newWidth = 42 * (currentHealth / tempCharacter.getHEALTHLIMIT());
            HealthBar.setWidth(newWidth);
        }

        if (previousGold != currentGold) {
            Double newWidth = 42 * (currentGold / tempCharacter.getGOLDLIMIT());
            GoldBar.setWidth(newWidth);
        }

        if (previousXP != currentXP) {
            Double newWidth = 42 * (currentXP / tempCharacter.getEXPLIMIT());
            XPBar.setWidth(newWidth);
        }

    }

    /**
     * Update the unequipped inventory.
     */
    public void updateUnequippedInventory() {
        clearUnequippedInventory();
        for (Entity entity : world.getUnequippedInventoryItems()) {
            onLoad(entity);
        }
    }

    /**
     * Clean up the unequipped inventory.
     */
    public void clearUnequippedInventory() {
        for (int x = 0; x < LoopManiaWorld.unequippedInventoryWidth; x++) {
            for (int y = 0; y < LoopManiaWorld.unequippedInventoryHeight; y++) {
                ImageView emptySlotView = new ImageView(
                        new Image((new File("src/images/empty_slot.png")).toURI().toString()));
                unequippedInventory.add(emptySlotView, x, y);
            }
        }
    }

    /**
     * Update the random created item(Gold/Health potion) list.
     * 
     * @param beforeEntityList the list of random items before the move.
     * @param randomItemthe    list of random items after the move.
     */
    public void updateRandomItem(List<Entity> beforeEntityList, List<Entity> randomItem) {
        for (Entity en : beforeEntityList) {
            if (randomItem.contains(en) == false) {
                Iterator<ImageView> iterator = entityImages.iterator();
                while (iterator.hasNext()) {
                    ImageView current = iterator.next();
                    if ((int) current.getLayoutX() == en.getX() && (int) current.getLayoutY() == en.getY()) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    /**
     * Showing the new pop out window.
     */
    private void shopPage() {
        pause();
        isPaused = true;
        buyButton.requestFocus();
        shopList.requestFocus();
        shopNotes.requestFocus();
        buyButton.setVisible(true);
        shopList.setVisible(true);
        shopNotes.setVisible(true);
    }

    /**
     * Initialize the shop list.
     */
    private void shopListInit() {
        for (String item : ShopList.getShopList()) {
            if (!shopList.getItems().contains(item))
                shopList.getItems().addAll(item);
        }
        shopList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue observableValue, Number value, Number new_value) {
                String item = ShopList.getShopList().get(new_value.intValue());
                itemToBuy = world.getItemByName(item);
            }
        });
    }

    /**
     * handle the click operation of the mouse.
     * 
     * @param event the current click operation need to handle.
     */
    @FXML
    public void handleMouseClick(MouseEvent event) {
        // source:
        // https://stackoverflow.com/questions/50012463/how-can-i-click-a-gridpane-cell-and-have-it-perform-an-action
        Node clickedNode = event.getPickResult().getIntersectedNode();
        // click on descendant node
        Node parent = clickedNode.getParent();
        while (parent != unequippedInventory) {
            clickedNode = parent;
            parent = clickedNode.getParent();
        }
        int colIndex = GridPane.getColumnIndex(clickedNode);
        int rowIndex = GridPane.getRowIndex(clickedNode);
        Entity item = world.getUnequippedInventoryItemEntityByCoordinates(colIndex, rowIndex);
        // debug
        // System.out.println(item.getX() + " " + item.getY() + " " +
        // item.getClass().getSimpleName());
        if (item != null && world.ifAtCastle()) {
            sellButton.setVisible(true);
            if (item instanceof Item)
                itemTryToSell = (Item) item;
        } else {
            sellButton.setVisible(false);
        }
    }

    /**
     * handle the cleck sell button operation of the mouse.
     * 
     * @param event the current click operation need to handle.
     */
    @FXML
    public void handleSellButton(Event event) throws Exception {
        if (itemTryToSell != null) {
            world.sellItems(itemTryToSell);
            itemTryToSell = null;
            buyButton.requestFocus();
            shopList.requestFocus();
            shopNotes.requestFocus();
            updateBar(world.getCharacter());
        }
        sellButton.setVisible(false);
    }

    /**
     * handle the cleck buy button operation of the mouse.
     * 
     * @param event the current click operation need to handle.
     */
    @FXML
    public void handleBuyButton(Event event) {
        if (itemToBuy != null) {
            if (world.buyItems(itemToBuy)) {
                onLoad(itemToBuy);
                buyButton.requestFocus();
                shopList.requestFocus();
                shopNotes.requestFocus();
                updateBar(world.getCharacter());
            }
        }
    }

    /**
     * Setting the property of the shop tag.
     * 
     * @pre shop page button visible
     * @post shop page button invisible
     * @invarriant shop page button >>>>>>> f59286f... add javadoc
     */
    public void setShopTagInvisible() {
        sellButton.setVisible(false);
        shopList.setVisible(false);
        buyButton.setVisible(false);
        shopNotes.setVisible(false);
    }

    /**
     * Update the soliders state for frontend.
     */
    private void updateSolidersFrontend() {
        soldiersBoard.getChildren().clear();
        System.out.println(soldiersNum);
        addSoldiersImage(soldiersNum);
    }

    /**
     * Load image for the soldiers.
     * 
     * @param numToLoad the number of soldiers need to add image.
     */
    private void addSoldiersImage(int numToLoad) {
        for (int i = 0; i < numToLoad; i++)
            onLoadSoldier();
    }

    /**
     * Check the game meet the end condition or not.
     * 
     * @return true if the game end, otherwise false.
     */
    public boolean checkGameEnd() {
        if (world.getCharacter().getHealth() <= 0) {
            return true;
        }
        return false;
    }

    /**
     * Check the game meet the win condition or not.
     * 
     * @return true if the game win, otherwise false.
     */
    public boolean checkGameWin() {
        if (world.getCharacter().getExperience() >= world.getCharacter().getEXPLIMIT()) {
            return true;
        }

        if (world.getCharacter().getGold() >= 9999) {
            return true;
        }

        if (world.getRound() == 50) {
            return true;
        }

        return false;
    }

}
