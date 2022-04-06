package unsw.loopmania;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.Buildings.BarracksBuilding;
import unsw.Buildings.Building;
import unsw.Buildings.CampfireBuilding;
import unsw.Buildings.HeroCastleBuilding;
import unsw.Buildings.VampireCastleBuilding;
import unsw.Buildings.VillageBuilding;
import unsw.Buildings.ZombiePitBuilding;
import unsw.Character.Soldier;
import unsw.Character.Character;
import unsw.Enemies.BasicEnemy;
import unsw.Enemies.Slug;
import unsw.Items.Armour;
import unsw.Items.DoggieCoin;
import unsw.Items.Armour;
import unsw.Items.Equipment;
import unsw.Items.Gold;
import unsw.Items.HealthPotion;
import unsw.Items.Helmet;
import unsw.Items.BuildingCard.BarracksCard;
import unsw.Items.BuildingCard.CampfireCard;
import unsw.Items.BuildingCard.Card;
import unsw.Items.BuildingCard.TowerCard;
import unsw.Items.BuildingCard.TrapCard;
import unsw.Items.BuildingCard.VampireCastleCard;
import unsw.Items.BuildingCard.VillageCard;
import unsw.Items.BuildingCard.ZombiepitCard;
import unsw.Items.Item;
import unsw.Items.Onering;
import unsw.Items.Staff;
import unsw.Items.Stake;
import unsw.Items.Sword;
import unsw.Buildings.TowerBuilding;
import unsw.Buildings.TrapBuilding;
import unsw.Enemies.Vampire;
import unsw.Enemies.Doggie;
import unsw.Enemies.ElanMuske;

/**
 * A backend world.
 *
 * A world can contain many entities, each occupy a square. More than one entity
 * can occupy the same square.
 */
public class LoopManiaWorld implements Cloneable {
    // TODO = add additional backend functionality

    // Assume that the number of equipment is at most 24.
    // Therefore, set the unequipped invenroty with to be 6
    // and height to be 4.
    public static final int unequippedInventoryWidth = 6;
    public static final int unequippedInventoryHeight = 4;
    private int round = 0;

    /**
     * Variable used to determinate which game mode the player choose.
     */
    private String gameMode;

    /**
     * width of the world in GridPane cells
     */
    private int width;

    /**
     * height of the world in GridPane cells
     */
    private int height;

    /**
     * generic entitites - i.e. those which don't have dedicated fields
     */
    private List<Entity> nonSpecifiedEntities;

    private Character character;

    // TODO = add more lists for other entities, for equipped inventory items,
    // etc...

    // TODO = expand the range of enemies
    private List<BasicEnemy> enemies;

    // TODO = expand the range of cards
    private List<Card> cardEntities;

    // TODO = expand the range of items
    private List<Entity> unequippedInventoryItems;

    // TODO = expand the range of buildings
    private List<Building> buildingEntities;
    private List<Soldier> soldiers;
    // The only HeroCastle in the map
    private HeroCastleBuilding heroCastleBuilding;
    /**
     * list of x,y coordinate pairs in the order by which moving entities traverse
     * them
     */
    private List<Pair<Integer, Integer>> orderedPath;
    private List<Entity> randomItem;

    //

    /**
     * create the world (constructor)
     * 
     * @param width       width of world in number of cells
     * @param height      height of world in number of cells
     * @param orderedPath ordered list of x, y coordinate pairs representing
     *                    position of path cells in world
     */
    public LoopManiaWorld(int width, int height, List<Pair<Integer, Integer>> orderedPath) {
        this.width = width;
        this.height = height;
        nonSpecifiedEntities = new ArrayList<>();
        character = null;
        enemies = new ArrayList<>();
        cardEntities = new ArrayList<>();
        unequippedInventoryItems = new ArrayList<>();
        this.orderedPath = orderedPath;
        buildingEntities = new ArrayList<>();
        soldiers = new ArrayList<>();
        this.gameMode = "Normal";
        this.randomItem = new ArrayList<>();
        // for debug
        // PathPosition pos = new PathPosition(60, orderedPath);
        // enemies.add(new Vampire(pos));

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * set the character. This is necessary because it is loaded as a special entity
     * out of the file
     * 
     * @param character the character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    /**
     * add a generic entity (without it's own dedicated method for adding to the
     * world)
     * 
     * @param entity
     */
    public void addEntity(Entity entity) {
        // for adding non-specific entities (ones without another dedicated list)
        // TODO = if more specialised types being added from main menu, add more methods
        // like this with specific input types...
        nonSpecifiedEntities.add(entity);
    }

    public void setHeroCastle(int buildingNodeX, int buildingNodeY) {
        // spawn building
        HeroCastleBuilding newBuilding = new HeroCastleBuilding(new SimpleIntegerProperty(buildingNodeX),
                new SimpleIntegerProperty(buildingNodeY));
        try {
            if (!newBuilding.checkIfAvailablePosition(this))
                throw new Exception("setHeroCastle Error");
            buildingEntities.add(0, newBuilding);
            newBuilding.setRound(19);
            heroCastleBuilding = newBuilding;
        } catch (Exception e) {
            System.out.println("Please set the hero castle on the path.");
        }
    }

    public HeroCastleBuilding getHeroCastle() {
        return heroCastleBuilding;
    }

    public void addEnemy(BasicEnemy enemy) {
        if (enemy != null)
            enemies.add(enemy);
    }

    /**
     * spawns enemies if the conditions warrant it, adds to world
     * 
     * @return list of the enemies to be displayed on screen
     */
    public List<BasicEnemy> possiblySpawnEnemies() {
        // TODO = expand this very basic version
        Pair<Integer, Integer> pos = possiblyGetBasicEnemySpawnPosition();
        List<BasicEnemy> spawningEnemies = new ArrayList<>();
        if (pos != null && checkEnemyNumByType(new Slug()) < 3) {
            int indexInPath = orderedPath.indexOf(pos);
            BasicEnemy enemy = new Slug(new PathPosition(indexInPath, orderedPath));
            enemies.add(enemy);
            spawningEnemies.add(enemy);
        }
        return spawningEnemies;
    }

    /**
     * get a randomly generated position which could be used to spawn an enemy
     * 
     * @return null if random choice is that wont be spawning an enemy or it isn't
     *         possible, or random coordinate pair if should go ahead
     */
    private Pair<Integer, Integer> possiblyGetBasicEnemySpawnPosition() {
        // TODO = modify this

        // has a chance spawning a basic enemy on a tile the character isn't on or
        // immediately before or after (currently space required = 2)...
        Random rand = new Random();
        // int choice = rand.nextInt(2); // TODO = change based on spec... currently low
        // value for dev purposes...
        // TODO = change based on spec
        // if ((choice == 0) && (enemies.size() < 2)) {
        List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
        int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
        // inclusive start and exclusive end of range of positions not allowed
        int startNotAllowed = (indexPosition - 2 + orderedPath.size()) % orderedPath.size();
        int endNotAllowed = (indexPosition + 3) % orderedPath.size();
        // note terminating condition has to be != rather than < since wrap around...
        for (int i = endNotAllowed; i != startNotAllowed; i = (i + 1) % orderedPath.size()) {
            orderedPathSpawnCandidates.add(orderedPath.get(i));
        }

        // choose random choice
        Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates
                .get(rand.nextInt(orderedPathSpawnCandidates.size()));

        return spawnPosition;
        // }

    }

    public List<Item> possiblySpawnRandomItems() {
        // TODO = expand this very basic version

        Pair<Integer, Integer> pos = possiblyGetRandomItemsSpawnPosition();
        List<Item> items = new ArrayList<>();
        if (pos != null) {
            if (pos.getValue0() % 2 == 0) {
                Gold gold = new Gold(new SimpleIntegerProperty(pos.getValue0()),
                        new SimpleIntegerProperty(pos.getValue1()));
                randomItem.add(gold);
            } else {
                HealthPotion healthPotion = new HealthPotion(new SimpleIntegerProperty(pos.getValue0()),
                        new SimpleIntegerProperty(pos.getValue1()));
                randomItem.add(healthPotion);
            }

        }
        return items;
    }

    private Pair<Integer, Integer> possiblyGetRandomItemsSpawnPosition() {
        // TODO = modify this

        // has a chance spawning a basic enemy on a tile the character isn't on or
        // immediately before or after (currently space required = 2)...
        Random rand = new Random();
        int choice = rand.nextInt(2); // TODO = change based on spec... currently low value for dev purposes...
        // TODO = change based on spec
        if ((choice == 0) && (randomItem.size() < 2)) {
            List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
            int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
            // inclusive start and exclusive end of range of positions not allowed
            int startNotAllowed = (indexPosition - 2 + orderedPath.size()) % orderedPath.size();
            int endNotAllowed = (indexPosition + 3) % orderedPath.size();
            // note terminating condition has to be != rather than < since wrap around...
            for (int i = endNotAllowed; i != startNotAllowed; i = (i + 1) % orderedPath.size()) {
                orderedPathSpawnCandidates.add(orderedPath.get(i));
            }

            // choose random choice
            Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates
                    .get(rand.nextInt(orderedPathSpawnCandidates.size()));

            return spawnPosition;
        }
        return null;
    }

    /**
     * kill an enemy
     * 
     * @param enemy enemy to be killed
     */
    private void killEnemy(BasicEnemy enemy) {
        enemy.destroy();
        enemies.remove(enemy);
    }

    public void removeBuildingByCoordinate(int x, int y) {

        Iterator<Building> iterator = buildingEntities.iterator();
        while (iterator.hasNext()) {
            Building building = iterator.next();
            if (building.getX() == x && building.getY() == y) {
                building.destroy();
                iterator.remove();
            }
        }
    }

    /**
     * run the expected battles in the world, based on current world state
     * 
     * @return list of enemies which have been killed
     */
    public List<BasicEnemy> runBattles() {
        List<BasicEnemy> battleList = new ArrayList<BasicEnemy>();
        List<BasicEnemy> supportList = new ArrayList<BasicEnemy>();
        List<BasicEnemy> defeatedEnemies = new ArrayList<BasicEnemy>();
        List<Soldier> soldiers = new ArrayList<Soldier>();
        updateCharacterNearByBuilding();
        // Create battle list and support list
        for (BasicEnemy e : enemies) {
            if (e.ifBattle(character)) {
                battleList.add(e);
            } else if (e.ifSupport(character)) {
                supportList.add(e);
            }
        }
        // battle and create defeated list
        for (BasicEnemy e : battleList) {
            if (battle(e, supportList, soldiers)) {
                defeatedEnemies.add(e);
                if (e.checkIfTurnToSoldier())
                    addSoldiers();
            } else {
                // use the one ring
                // useOneRing();
                // otherwise the character killed
                gameOver();
            }
        }

        // kill enemies
        for (BasicEnemy e : defeatedEnemies) {
            // IMPORTANT = we kill enemies here, because killEnemy removes the enemy from
            // the enemies list
            // if we killEnemy in prior loop, we get
            // java.util.ConcurrentModificationException
            // due to mutating list we're iterating over
            killEnemy(e);
        }

        return defeatedEnemies;
    }

    /**
     * Run the battle between specific enemy and character The character will also
     * take the damage from the supporting enemies
     * 
     * @param enemy
     * @return true if character win the battle; false if character lose the battle
     */
    private boolean battle(BasicEnemy enemy, List<BasicEnemy> supportList, List<Soldier> soldiers) {
        try {
            // Check if this is a trap.
            if (thereIsTrap(enemy.getX(), enemy.getY())) {
                removeBuildingByCoordinate(enemy.getX(), enemy.getY());
                character.increaseGold(enemy.getGold());
                character.increaseExperience(enemy.getExperience());
                addUnequipped(randomItem());
                loadCard(randomCard());
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception caught in thereIsTrap in LoopManiaWorld.battle");
        }

        while (character.getHealth() > 0) {
            try {
                character.attack(enemy);
                if (enemy.getHealth() <= 0) {
                    if (enemy instanceof ElanMuske) {
                        DoggieCoin.setElanifExist(false);
                    }
                    character.increaseGold(enemy.getGold());
                    character.increaseExperience(enemy.getExperience());
                    addUnequipped(randomItem());
                    loadCard(randomCard());
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Exception caught when checking trap in LoopManiaWorld.battle");
            }
            try {
                // soldiers' round
                Iterator<Soldier> iter = this.soldiers.iterator();
                while (iter.hasNext()) {
                    Soldier s = iter.next();
                    s.attack(enemy);
                    if (s.ifDead()) {
                        iter.remove();
                        // System.out.println("remove!!!!");
                    }

                }
                // System.out.println("we have " + this.soldiers.size() + " soldiers now.");
            } catch (Exception e) {
                System.out.println("Exception caught when soldiers are attacking in LoopManiaWorld.battle");
            }
            try {
                // enemies' round
                ElanMuske elan = null;
                for (BasicEnemy be : supportList) {
                    if (be instanceof ElanMuske) {
                        elan = (ElanMuske) be;
                        break;
                    }
                }

                enemy.attack(character);
                for (BasicEnemy e : supportList) {

                    if (elan != null) {
                        elan.heal(e);
                    }
                    e.attack(character);
                }
            } catch (Exception e) {
                System.out.println("Exception caught when enemies are attacking in LoopManiaWorld.battle");
            }
        }
        return false;
    }

    /** Loot drop item */
    public List<Entity> lootDrop(BasicEnemy enemy) {
        if (enemy == null)
            return null;
        List<Entity> loots = new ArrayList<>();
        try {
            // Drop a weapon
            loots.add(addUnequipped(randomWeapon()));
            // Drop the first item
            loots.add(addUnequipped(randomItem()));
            // Drop the second item if need
            int chance = Math.random() <= 0.5 ? 0 : 1;
            if (chance == 1) {
                Entity target = addUnequipped(randomItem());
                loots.add(target);
            }
            // Drop the third item if need
            chance = Math.random() <= 0.5 ? 0 : 1;
            if (chance == 1) {
                Entity target = addUnequipped(randomItem());
                loots.add(target);
            }
            Card card = loadCard(randomCard());
            loots.add(card);
            if (enemy instanceof Doggie) {
                Pair<Integer, Integer> position = getFirstAvailableSlotForItem();
                DoggieCoin dogCoin = new DoggieCoin(new SimpleIntegerProperty(position.getValue0()),
                        new SimpleIntegerProperty(position.getValue1()));
                addUnequipped(dogCoin);
                loots.add(dogCoin);
            }
        } catch (Exception e) {
            System.out.println("Loot drop error.");
        }

        return loots;
    }

    /**
     * spawn a card in the world and return the card entity
     * 
     * @return a card to be spawned in the controller as a JavaFX node
     */
    public Card loadCard(Card card) throws Exception {

        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()) {
            // TODO = give some cash/experience/item rewards for the discarding of the
            // oldest card
            removeCard(0);

            character.increaseGold(50);
            character.increaseExperience(20);
            // TODO: this addUnequipped not loading the image in frontend
            addUnequipped(randomItem());
        }
        cardEntities.add(card);
        return card;
    }

    public void useOneRing() {
        Iterator<Entity> iter = unequippedInventoryItems.iterator();
        while (iter.hasNext()) {
            Entity item = iter.next();
            if (item instanceof Onering) {
                Onering onering = (Onering) item;
                onering.revive(character);
                iter.remove();
                break;
            }
        }
    }

    /**
     * remove card at a particular index of cards (position in gridpane of unplayed
     * cards)
     * 
     * @param index the index of the card, from 0 to length-1
     */
    private void removeCard(int index) {
        Card c = cardEntities.get(index);
        int x = c.getX();
        c.destroy();
        cardEntities.remove(index);
        shiftCardsDownFromXCoordinate(x);
    }

    /**
     * spawn a sword in the world and return the sword entity
     * 
     * @return a sword to be spawned in the controller as a JavaFX node
     */
    public Entity addUnequipped(StaticEntity entity) throws Exception {

        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null) {
            // Eject the oldest unequipped item and replace it.
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();

            // Assume character can gain 20 gold and 5 experience.
            character.increaseGold(20);
            character.increaseExperience(5);
        }
        entity.setX(new SimpleIntegerProperty(firstAvailableSlot.getValue0()));
        entity.setY(new SimpleIntegerProperty(firstAvailableSlot.getValue1()));

        unequippedInventoryItems.add(entity);
        return entity;
    }

    /**
     * remove an item by x,y coordinates
     * 
     * @param x x coordinate from 0 to width-1
     * @param y y coordinate from 0 to height-1
     */
    public void removeUnequippedInventoryItemByCoordinates(int x, int y) {
        Entity item = getUnequippedInventoryItemEntityByCoordinates(x, y);
        removeUnequippedInventoryItem(item);
    }

    public void removeEquippedItemByCoordinates(int x, int y) {
        EquippedBoard equippedBoard = character.getEquippedBoard();
        Item weapon = equippedBoard.getWeapon();
        if (weapon != null && weapon.getX() == x && weapon.getY() == y) {
            weapon.destroy();
            equippedBoard.setWeapon(null);
        }
        Item armour = equippedBoard.getArmour();
        if (armour != null && armour.getX() == x && armour.getY() == y) {
            armour.destroy();
            equippedBoard.setArmour(null);
        }
        Item shield = equippedBoard.getShield();
        if (shield != null && shield.getX() == x && shield.getY() == y) {
            shield.destroy();
            equippedBoard.setShield(null);
        }
        Item helmet = equippedBoard.getHelmet();
        if (helmet != null && helmet.getX() == x && helmet.getY() == y) {
            helmet.destroy();
            equippedBoard.setHelmet(null);
        }
    }

    /**
     * run moves which occur with every tick without needing to spawn anything
     * immediately
     */
    public void runTickMoves() {
        character.moveDownPath();
        moveBasicEnemies();
        autoJoinSoldiers();
        if (characterAtHeroCastle()) {
            round++;
            // update if any building will disappear
            autoUpdateBuildings();
            // update if any enemy will disappear
            // generate some Gold and HealthPotion
            possiblySpawnRandomItems();
        }
        possiblySpawnRandomItems();
        pickupItems();
        updateCharacterNearByBuilding();
    }

    /**
     * remove an item from the unequipped inventory
     * 
     * @param item item to be removed
     */
    public void removeUnequippedInventoryItem(Entity item) {
        Iterator iter = unequippedInventoryItems.iterator();
        while (iter.hasNext()) {
            Entity e = (Entity) iter.next();
            if (e.getX() == item.getX() && e.getY() == item.getY()) {
                iter.remove();
                break;
            }
        }
        item.destroy();
    }

    /**
     * return an unequipped inventory item by x and y coordinates assumes that no 2
     * unequipped inventory items share x and y coordinates
     * 
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return unequipped inventory item at the input position
     */
    public Entity getUnequippedInventoryItemEntityByCoordinates(int x, int y) {
        for (Entity e : unequippedInventoryItems) {
            if ((e.getX() == x) && (e.getY() == y)) {
                return e;
            }
        }
        return null;
    }

    /**
     * remove item at a particular index in the unequipped inventory items list
     * (this is ordered based on age in the starter code)
     * 
     * @param index index from 0 to length-1
     */
    private void removeItemByPositionInUnequippedInventoryItems(int index) {
        Entity item = unequippedInventoryItems.get(index);
        item.destroy();
        unequippedInventoryItems.remove(index);
    }

    /**
     * get the first pair of x,y coordinates which don't have any items in it in the
     * unequipped inventory
     * 
     * @return x,y coordinate pair
     */
    private Pair<Integer, Integer> getFirstAvailableSlotForItem() {
        // first available slot for an item...
        // IMPORTANT - have to check by y then x, since trying to find first available
        // slot defined by looking row by row
        for (int y = 0; y < unequippedInventoryHeight; y++) {
            for (int x = 0; x < unequippedInventoryWidth; x++) {
                if (getUnequippedInventoryItemEntityByCoordinates(x, y) == null) {
                    // System.out.println(x + " " + y);
                    return new Pair<Integer, Integer>(x, y);

                }
            }
        }
        return null;
    }

    /**
     * shift card coordinates down starting from x coordinate
     * 
     * @param x x coordinate which can range from 0 to width-1
     */
    private void shiftCardsDownFromXCoordinate(int x) {
        for (Card c : cardEntities) {
            if (c.getX() >= x) {
                c.x().set(c.getX() - 1);
            }
        }
    }

    public boolean thereIsTrap(int x, int y) {
        for (Building building : buildingEntities) {
            if (x == building.getX() && y == building.getY()) {
                if (building instanceof TrapBuilding) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean thereisCampfire(int x, int y) {
        for (Building building : buildingEntities) {
            if (Math.pow(x - building.getX(), 2) + Math.pow(y - building.getY(), 2) <= 1) {
                if (building instanceof CampfireBuilding) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * move all enemies
     */
    private void moveBasicEnemies() {
        // TODO = expand to more types of enemy
        for (BasicEnemy e : enemies) {
            // System.out.println("current mode: " + getGameMode());
            if (e instanceof Vampire && thereisCampfire(e.getX(), e.getY())) {
                e.moveDownPath();
                e.moveDownPath();
            } else {
                e.move();
            }
        }
    }

    /**
     * remove a card by its x, y coordinates
     * 
     * @param cardNodeX     x index from 0 to width-1 of card to be removed
     * @param cardNodeY     y index from 0 to height-1 of card to be removed
     * @param buildingNodeX x index from 0 to width-1 of building to be added
     * @param buildingNodeY y index from 0 to height-1 of building to be added
     */
    public Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX,
            int buildingNodeY) {
        // start by getting card
        Card card = null;
        for (Card c : cardEntities) {
            if ((c.getX() == cardNodeX) && (c.getY() == cardNodeY)) {
                card = c;
                break;
            }
        }

        // now spawn building
        Building newBuilding = card.getBuilding(new SimpleIntegerProperty(buildingNodeX),
                new SimpleIntegerProperty(buildingNodeY));
        try {
            if (newBuilding.checkIfAvailablePosition(this)) {
                buildingEntities.add(newBuilding);
                newBuilding.setRound(round);
            } else {
                if (newBuilding.checkIfAvailablePosition(this))
                    buildingEntities.add(newBuilding);
                else {
                    throw new Exception("Card Spawn Building Error");
                }
            }
        } catch (Exception e) {
            System.out.println("Buildings cannot be spawn here, please choose another position");
            return null;
        }

        // destroy the card
        card.destroy();
        cardEntities.remove(card);
        shiftCardsDownFromXCoordinate(cardNodeX);

        return newBuilding;
    }

    public Equipment convertUnequippedItemToEquipped(int unequippedNodeX, int unequippedNodeY, int equippedNodeX,
            int equippedNodeY) {
        Entity item = null;
        for (Entity entity : unequippedInventoryItems) {
            if (entity.getX() == unequippedNodeX && entity.getY() == unequippedNodeY) {
                item = entity;
                break;
            }
        }
        if (item instanceof Equipment) {
            Equipment equipment = (Equipment) item;
            Equipment newEquipment = equipment.getEquipment(new SimpleIntegerProperty(equippedNodeX),
                    new SimpleIntegerProperty(equippedNodeY));
            newEquipment.equip(character.getEquippedBoard(), newEquipment);
            return newEquipment;
        }
        return null;

    }

    /**
     * players can use money to buy items to gain force values.
     * 
     * buyItems is called only when character at castle
     * 
     * @throws Exception
     */
    public boolean buyItems(Item item) {
        // debug
        // System.out.println(character.getGold());
        if (round == 0) {
            System.out.println("buy item failed in round 0");
            return false;
        }
        if (!heroCastleBuilding.checkIfItemsAvailable(item)) {
            // System.out.println("");
            return false;
        }
        try {
            character.decreaseGold(item.getBuyingPrice());
        } catch (Exception e) {
            return false;
        }
        try {
            addUnequipped(item);
        } catch (Exception e) {
            System.out.println("Exception caught in LoopManiaWorld.buyItems");
            return false;
        }
        // for debug
        // for (Entity entity : unequippedInventoryItems)
        // System.out.println(entity.getClass().getSimpleName() + " " + entity.getX() +
        // " " + entity.getY());
        return true;
    }

    /**
     * player can obtain money by selling items
     * 
     * sellItems is called only when character at castle
     */
    public void sellItems(Item item) {
        // System.out.println(character.getGold());
        if (round == 0)
            return;
        character.increaseGold(item.getSellingPrice());
        try {
            removeUnequippedInventoryItem(item);
        } catch (Exception e) {
            System.out.println("No such an item to sell!");
        }
        // dubug
        // for (Entity entity : unequippedInventoryItems)
        // System.out.println(entity.getClass().getSimpleName() + " " + entity.getX() +
        // " " + entity.getY());
    }

    public StaticEntity randomItem() {
        // The list that contains the items can be gained from
        // droping cards.
        Pair<Integer, Integer> slotPosition = getFirstAvailableSlotForItem();
        StaticEntity target;
        int chance = (int) (Math.random() * 100);
        if (chance < 5) {
            target = new Onering(new SimpleIntegerProperty(slotPosition.getValue0()),
                    new SimpleIntegerProperty(slotPosition.getValue1()));
        } else if (chance >= 5 && chance < 20) {
            target = new Sword(new SimpleIntegerProperty(slotPosition.getValue0()),
                    new SimpleIntegerProperty(slotPosition.getValue1()));
        } else if (chance >= 20 && chance < 30) {
            target = new Helmet(new SimpleIntegerProperty(slotPosition.getValue0()),
                    new SimpleIntegerProperty(slotPosition.getValue1()));
        } else if (chance >= 30 && chance < 40) {
            target = new Armour(new SimpleIntegerProperty(slotPosition.getValue0()),
                    new SimpleIntegerProperty(slotPosition.getValue1()));
        } else if (chance >= 40 && chance < 55) {
            target = new Helmet(new SimpleIntegerProperty(slotPosition.getValue0()),
                    new SimpleIntegerProperty(slotPosition.getValue1()));
        } else if (chance >= 55 && chance < 65) {
            target = new Staff(new SimpleIntegerProperty(slotPosition.getValue0()),
                    new SimpleIntegerProperty(slotPosition.getValue1()));
        } else if (chance >= 65 && chance < 80) {
            target = new HealthPotion(new SimpleIntegerProperty(slotPosition.getValue0()),
                    new SimpleIntegerProperty(slotPosition.getValue1()));
        } else {
            target = new Stake(new SimpleIntegerProperty(slotPosition.getValue0()),
                    new SimpleIntegerProperty(slotPosition.getValue1()));
        }

        // target = new Onering(new SimpleIntegerProperty(slotPosition.getValue0()),
        // new SimpleIntegerProperty(slotPosition.getValue1()));

        return target;
    }

    public StaticEntity randomWeapon() {
        Pair<Integer, Integer> slotPosition = getFirstAvailableSlotForItem();
        StaticEntity target;
        int chance = (int) (Math.random() * 100);
        if (chance < 35) {
            target = new Sword(new SimpleIntegerProperty(slotPosition.getValue0()),
                    new SimpleIntegerProperty(slotPosition.getValue1()));
        } else if (chance >= 35 && chance < 65) {
            target = new Stake(new SimpleIntegerProperty(slotPosition.getValue0()),
                    new SimpleIntegerProperty(slotPosition.getValue1()));
        } else {
            target = new Staff(new SimpleIntegerProperty(slotPosition.getValue0()),
                    new SimpleIntegerProperty(slotPosition.getValue1()));
        }

        return target;
    }

    public Card randomCard() {
        Card target;

        // Generate a random int, and use it to
        // determinate what type of building card
        // need to return back;

        // Generate a random number between
        // 0 and 99 to represent the possibility.
        int chance = (int) (Math.random() * 100);
        if (chance < 15) {
            target = new VampireCastleCard(new SimpleIntegerProperty(cardEntities.size()),
                    new SimpleIntegerProperty(0));
        } else if (chance >= 15 && chance < 30) {
            target = new ZombiepitCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
        } else if (chance >= 30 && chance < 50) {
            target = new TowerCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
        } else if (chance >= 50 && chance < 60) {
            target = new VillageCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
        } else if (chance >= 60 && chance < 70) {
            target = new BarracksCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
        } else if (chance >= 70 && chance < 80) {
            target = new TrapCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
        } else {
            target = new CampfireCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
        }

        // return target;
        return target;
    }

    // <<<<<<<<<<<< Getter and Setter >>>>>>>>>>>>>>>>>>>
    public Character getCharacter() {
        return character;
    }

    public static int getUnequippedinventorywidth() {
        return unequippedInventoryWidth;
    }

    public static int getUnequippedinventoryheight() {
        return unequippedInventoryHeight;
    }

    public boolean checkIfItemInUnequippedInventory(Item item) {
        for (Entity itemInInventory : unequippedInventoryItems) {
            if (item.equals(itemInInventory))
                return true;
        }
        return false;
    }

    public Entity getNewestUnequippedInventoryItems() {
        return unequippedInventoryItems.get(23);
    }

    public int getNumItems() {
        return unequippedInventoryItems.size();
    }

    public int getRandomItemSize() {
        return randomItem.size();
    }

    public int getCardEntitiesSize() {
        return cardEntities.size();
    }

    public List<Card> getCardEntities() {
        return cardEntities;
    }

    public Card getNewestCard() {
        return cardEntities.get(getWidth() - 1);
    }

    public List<Entity> getUnequippedInventoryItems() {
        return unequippedInventoryItems;
    }

    public int getEnemyNum() {
        return enemies.size();
    }

    public void setGameMode(String mode) {
        this.gameMode = mode;
        heroCastleBuilding.setGameMode(mode);
    }

    public String getGameMode() {
        return gameMode;
    }

    public boolean checkIfBuildingExistsInMap(Building building) {
        for (Building buildingInMap : buildingEntities) {
            if (building.equals(buildingInMap))
                return true;
        }
        return false;
    }

    public int getBuildingsNum() {
        return buildingEntities.size();
    }

    public void useHealthPotion() {

        Iterator<Entity> iterator = unequippedInventoryItems.iterator();
        while (iterator.hasNext()) {
            Entity current = iterator.next();
            if (current instanceof HealthPotion) {
                // System.out.println("has health potion");
                character.useHealthPotion();
                iterator.remove();
                break;
            }
        }

        // for (Entity item : unequippedInventoryItems) {
        // if (item.equals(new HealthPotion())) {
        // character.useHealthPotion();
        // unequippedInventoryItems.remove(item);
        // break;
        // }
        // }

    }

    public void gameOver() {
        // for debug
        // character.useHealthPotion();
        System.out.println("GameOver!!!!!");
    }

    public void pickupItems() {
        try {
            if (checkIfGoldOnTheGround()) {
                System.out.println("=================");
                System.out.println("pick gold");
                System.out.println("=================");
                character.increaseGold(50);
            }
            if (checkIfHealthPotionOnTheGround()) {
                System.out.println("=================");
                System.out.println("pick health potion");
                System.out.println("=================");
                Pair<Integer, Integer> pos = getFirstAvailableSlotForItem();
                addUnequipped(new HealthPotion(new SimpleIntegerProperty(pos.getValue0()),
                        new SimpleIntegerProperty(pos.getValue1())));
            }
        } catch (Exception e) {
            System.out.println("Exception caught in prickupItems");
        }
    }

    public boolean checkIfHealthPotionOnTheGround() {
        Iterator iter = randomItem.iterator();
        while (iter.hasNext()) {
            Entity item = (Entity) iter.next();
            if (character.getX() == item.getX() && character.getY() == item.getY() && item instanceof HealthPotion) {
                iter.remove();
                item.destroy();
                return true;
            }
        }
        return false;
    }

    public boolean checkIfGoldOnTheGround() {
        Iterator iter = randomItem.iterator();
        while (iter.hasNext()) {
            Entity item = (Entity) iter.next();
            if (character.getX() == item.getX() && character.getY() == item.getY() && item instanceof Gold) {
                iter.remove();
                item.destroy();
                return true;
            }
        }
        return false;
    }

    public void addSoldiers() {
        // we can only have 6 soldiers
        if (getSoldiersNum() >= 6)
            return;
        Soldier soldier = new Soldier(new SimpleIntegerProperty(getFirstAvailablePosForSoldier()),
                new SimpleIntegerProperty(0));
        soldiers.add(soldier);
    }

    public int getFirstAvailablePosForSoldier() {
        for (int x = 0; x < soldiers.size(); x++) {
            if (getSoldierEntityByCoordinates(x) == null) {
                return x;
            }
        }
        return 0;
    }

    private Entity getSoldierEntityByCoordinates(int x) {
        for (Soldier soldier : soldiers) {
            if (soldier.getX() == x)
                return soldier;
        }
        return null;
    }

    public List<Soldier> getSoldiers() {
        return soldiers;
    }

    /**
     * This will check if the eneity is on path, if yes, return the index of it
     * positioning, if not , return -1, the entity could be moving entity or static
     * entity
     * 
     * @param building
     * @return the index of the entity
     */
    public int getEntityOnPathWhichIndex(Entity entity) {
        int index = -1;
        Pair<Integer, Integer> pos = new Pair<>(entity.getX(), entity.getY());
        try {
            index = orderedPath.indexOf(pos);
        } catch (Exception e) {
            System.out.println("No such a tile on path");
        }
        return index;
    }

    /**
     * This will check if the eneity is adjacent to path, if yes, return the index
     * of it positioning, if not , return -1, the entity could be moving entity or
     * static entity
     *
     * @param building
     * @return if entity near to path
     */
    public boolean checkEntityAdjacentPathWhichIndex(Entity entity) {
        int index = -1;
        // path on top
        Pair<Integer, Integer> posUp = new Pair<>(entity.getX(), entity.getY() + 1);
        index = index == -1 ? orderedPath.indexOf(posUp) : index;
        // path on bottom
        Pair<Integer, Integer> posDown = new Pair<>(entity.getX(), entity.getY() - 1);
        index = index == -1 ? orderedPath.indexOf(posDown) : index;
        // path on left
        Pair<Integer, Integer> posLeft = new Pair<>(entity.getX() - 1, entity.getY());
        index = index == -1 ? orderedPath.indexOf(posLeft) : index;
        // path on right
        Pair<Integer, Integer> posRight = new Pair<>(entity.getX() + 1, entity.getY());
        index = index == -1 ? orderedPath.indexOf(posRight) : index;
        return index != -1 ? true : false;
    }

    public void autoJoinSoldiers() {
        Iterator<Building> iter = buildingEntities.iterator();
        while (iter.hasNext()) {
            Building building = iter.next();
            if (building instanceof BarracksBuilding && building.getX() == character.getX()
                    && building.getY() == character.getY()) {
                addSoldiers();
                BarracksBuilding barracks = (BarracksBuilding) building;
                barracks.increTimePassedThrough();
                if (barracks.checkIfDisappear()) {
                    barracks.destroy();
                    iter.remove();
                }
                // System.out.println("soldier num: " + getSoldiersNum());
                break;
            }
        }
    }

    public boolean characterAtHeroCastle() {
        if (character.getX() == getHeroCastle().getX() && character.getY() == getHeroCastle().getY())
            return true;
        return false;
    }

    public int getRound() {
        return round;
    }

    public void autoUpdateBuildings() {
        Iterator<Building> iter = buildingEntities.iterator();
        while (iter.hasNext()) {
            Building building = iter.next();
            if (building.checkIfDisappear(round)) {
                building.destroy();
                iter.remove();
            }
        }
    }

    public List<BasicEnemy> autoUpdateEnemies() {
        List<BasicEnemy> newEnemies = new ArrayList<>();
        for (Building building : buildingEntities) {
            if (building instanceof VampireCastleBuilding) {
                Pair<Integer, Integer> pos = possiblyGetBasicEnemySpawnPosition();
                PathPosition pathPos = new PathPosition(orderedPath.indexOf(pos), orderedPath);
                VampireCastleBuilding vampireCastleBuilding = (VampireCastleBuilding) building;
                BasicEnemy enemy = vampireCastleBuilding.spawn(pathPos, this);
                if (enemy != null) {
                    addEnemy(enemy);
                    newEnemies.add(enemy);
                }
            } else if (building instanceof ZombiePitBuilding) {
                Pair<Integer, Integer> pos = possiblyGetBasicEnemySpawnPosition();
                PathPosition pathPos = new PathPosition(orderedPath.indexOf(pos), orderedPath);
                ZombiePitBuilding zombiePitBuilding = (ZombiePitBuilding) building;
                BasicEnemy enemy = zombiePitBuilding.spawn(pathPos, this);
                if (enemy != null) {
                    addEnemy(enemy);
                    newEnemies.add(enemy);
                }
            }
        }
        if (round > 19 && (checkEnemyNumByType(new Doggie()) < 1)) {
            Pair<Integer, Integer> pos = possiblyGetBasicEnemySpawnPosition();
            PathPosition pathPos = new PathPosition(orderedPath.indexOf(pos), orderedPath);
            Doggie dog = new Doggie(pathPos);
            addEnemy(dog);
            newEnemies.add(dog);
            // System.out.println(newEnemies.contains(new Doggie()));
        }

        if (round >= 40 && checkEnemyNumByType(new ElanMuske()) < 1 && character.getExperience() >= 10000) {
            Pair<Integer, Integer> pos = possiblyGetBasicEnemySpawnPosition();
            PathPosition pathPos = new PathPosition(orderedPath.indexOf(pos), orderedPath);
            ElanMuske musk = new ElanMuske(pathPos);
            addEnemy(musk);
            newEnemies.add(musk);
        }

        return newEnemies;
    }

    public int checkEnemyNumByType(BasicEnemy enemyType) {
        int count = 0;
        for (BasicEnemy e : enemies) {
            if (enemyType.sameType(e))
                count++;
        }
        return count;
    }

    public void updateCharacterNearByBuilding() {
        boolean campfireChangeFlag = false;
        boolean towerChangeFlag = false;
        for (Building building : buildingEntities) {
            if (building instanceof CampfireBuilding) {
                CampfireBuilding campfire = (CampfireBuilding) building;
                if (campfire.checkIfEntityInBuildingRadius(character)) {
                    character.setCharacterUnderCampfire(true);
                    campfireChangeFlag = true;
                }
            }
            if (building instanceof TowerBuilding) {
                TowerBuilding tower = (TowerBuilding) building;
                if (tower.checkIfEntityInBuildingRadius(character)) {
                    character.setCharacterUnderTower(true);
                    towerChangeFlag = true;
                }
            }
            if (building instanceof VillageBuilding) {
                VillageBuilding village = (VillageBuilding) building;
                if (village.getX() == character.getX() && village.getY() == character.getY())
                    village.Heal(character);
            }
        }
        if (!campfireChangeFlag)
            character.setCharacterUnderCampfire(false);
        if (!towerChangeFlag)
            character.setCharacterUnderTower(false);
    }

    public List<Entity> getRandomItem() {
        return randomItem;
    }

    public List<BasicEnemy> getEnemiesList() {
        return enemies;
    }

    public boolean ifAtCastle() {
        if (character.getX() == heroCastleBuilding.getX() && character.getY() == heroCastleBuilding.getY()
                && round != 0)
            return true;
        return false;
    }

    public Item getItemByName(String itemName) {
        Item target = null;
        try {
            String classname = "unsw.Items." + itemName;
            Class<?> cl = Class.forName(classname);
            Constructor<?> con = cl.getConstructor(SimpleIntegerProperty.class, SimpleIntegerProperty.class);
            Pair<Integer, Integer> pos = getFirstAvailableSlotForItem();
            target = (Item) con.newInstance(new SimpleIntegerProperty(pos.getValue0()),
                    new SimpleIntegerProperty(pos.getValue1()));
        } catch (Exception e) {
            System.out.println("create item by name failed.");
        }
        return target;
    }

    public int getSoldiersNum() {
        return soldiers.size();
    }

    public void setRound(int round) {
        this.round = round;
    }

}
