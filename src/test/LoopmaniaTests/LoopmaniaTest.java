package test.LoopmaniaTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;

import org.javatuples.Pair;

import unsw.Buildings.VampireCastleBuilding;
import unsw.Character.Character;
import unsw.Enemies.BasicEnemy;
import unsw.Enemies.Doggie;
import unsw.Enemies.ElanMuske;
import unsw.Enemies.Slug;
import unsw.Items.Armour;
import unsw.Items.HealthPotion;
import unsw.Items.Helmet;
import unsw.Items.Item;
import unsw.Items.Onering;
import unsw.Items.Shield;
import unsw.Items.Sword;
import unsw.Items.BuildingCard.BarracksCard;
import unsw.Items.BuildingCard.VampireCastleCard;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;

public class LoopmaniaTest {

    private List<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();
    private LoopManiaWorld loopManiaWorld;
    private Character character;

    /**
     * Initialize the sample world state.
     */
    public void init() {
        path.add(new Pair<Integer, Integer>(0, 0));
        path.add(new Pair<Integer, Integer>(1, 0));
        path.add(new Pair<Integer, Integer>(2, 0));
        path.add(new Pair<Integer, Integer>(2, 1));
        path.add(new Pair<Integer, Integer>(2, 2));
        path.add(new Pair<Integer, Integer>(1, 2));
        path.add(new Pair<Integer, Integer>(0, 2));
        path.add(new Pair<Integer, Integer>(0, 1));
        loopManiaWorld = new LoopManiaWorld(3, 3, path);
        loopManiaWorld.setHeroCastle(0, 0);
        PathPosition characterPosition = new PathPosition(0, path);
        character = new Character(characterPosition);
        loopManiaWorld.setCharacter(character);
    }

    @Test
    public void testMode() {
        init();
        assertEquals(loopManiaWorld.getGameMode(), "Normal");
        loopManiaWorld.setGameMode("Survival");
        assertEquals(loopManiaWorld.getGameMode(), "Survival");
    }

    @Test
    public void testHeight() {
        init();
        assertEquals(loopManiaWorld.getHeight(), 3);
    }

    @Test
    public void testPossiblyItemsPosition() {
        init();
        int sizeBefore = loopManiaWorld.getRandomItemSize();
        loopManiaWorld.possiblySpawnRandomItems();
        int sizeAfter = loopManiaWorld.getRandomItemSize();
        assertTrue(sizeBefore <= sizeAfter);
    }

    @Test
    public void testGetItemByName() {
        init();
        Item item = loopManiaWorld.getItemByName("Sword");
        assertTrue(item.equals(new Sword()));
    }

    @Test
    public void sellItemsNoSuchItem() {
        init();
        // this one should not break
        loopManiaWorld.sellItems(new Sword());
    }

    @Test
    public void addSoldiersExcessNumber() {
        init();
        for (int i = 0; i <= 6; i++)
            loopManiaWorld.addSoldiers();
        assertEquals(6, loopManiaWorld.getSoldiersNum());
    }

    @Test
    public void useHealthPotionTest() throws Exception {
        init();
        character.decreaseHealth(10);
        assertEquals(90, character.getHealth());
        loopManiaWorld.useHealthPotion();
        loopManiaWorld.addUnequipped(new Sword(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0)));
        loopManiaWorld.addUnequipped(new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        loopManiaWorld.addUnequipped(new Sword(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0)));
        loopManiaWorld.useHealthPotion();
        assertEquals(100, character.getHealth());
    }

    @Test
    public void ifAtCastleTest() {
        init();
        assertFalse(loopManiaWorld.ifAtCastle());
        for (int i = 0; i < 8; i++) {
            loopManiaWorld.runTickMoves();
        }
        assertTrue(loopManiaWorld.ifAtCastle());
    }

    @Test
    public void useOneRingTest() throws Exception {
        init();
        loopManiaWorld.useOneRing();
        loopManiaWorld.addUnequipped(new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        loopManiaWorld.addUnequipped(new Onering(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        loopManiaWorld.addUnequipped(new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        character.decreaseHealth(100);
        loopManiaWorld.useOneRing();
        assertEquals(100, character.getHealth());
    }

    @Test
    public void possiblySpawnEnemiesTest() {
        init();
        loopManiaWorld.possiblySpawnEnemies();
        assertTrue(loopManiaWorld.getEnemyNum() > 0);
    }

    @Test
    public void removeEquippedItemByCoordinates() {
        init();
        character.getEquippedBoard().setWeapon(new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        loopManiaWorld.removeEquippedItemByCoordinates(0, 0);
        try {
            loopManiaWorld.removeEquippedItemByCoordinates(1, 0);
        } catch (Exception e) {
            System.out.println("correct");
        }
        try {
            loopManiaWorld.removeEquippedItemByCoordinates(0, 1);
        } catch (Exception e) {
            System.out.println("correct");
        }
        assertEquals(0, loopManiaWorld.getNumItems());
        character.getEquippedBoard().setArmour(new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        loopManiaWorld.removeEquippedItemByCoordinates(0, 0);
        try {
            loopManiaWorld.removeEquippedItemByCoordinates(1, 0);
        } catch (Exception e) {
            System.out.println("correct");
        }
        try {
            loopManiaWorld.removeEquippedItemByCoordinates(0, 1);
        } catch (Exception e) {
            System.out.println("correct");
        }
        assertEquals(0, loopManiaWorld.getNumItems());
        character.getEquippedBoard().setHelmet(new Helmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        loopManiaWorld.removeEquippedItemByCoordinates(0, 0);
        try {
            loopManiaWorld.removeEquippedItemByCoordinates(1, 0);
        } catch (Exception e) {
            System.out.println("correct");
        }
        try {
            loopManiaWorld.removeEquippedItemByCoordinates(0, 1);
        } catch (Exception e) {
            System.out.println("correct");
        }
        assertEquals(0, loopManiaWorld.getNumItems());
        character.getEquippedBoard().setShield(new Shield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        loopManiaWorld.removeEquippedItemByCoordinates(0, 0);
        try {
            loopManiaWorld.removeEquippedItemByCoordinates(1, 0);
        } catch (Exception e) {
            System.out.println("correct");
        }
        try {
            loopManiaWorld.removeEquippedItemByCoordinates(0, 1);
        } catch (Exception e) {
            System.out.println("correct");
        }
        assertEquals(0, loopManiaWorld.getNumItems());
    }

    @Test
    public void convertUnequippedItemToEquippedTest() throws Exception {
        init();
        loopManiaWorld.addUnequipped(new HealthPotion(new SimpleIntegerProperty(3), new SimpleIntegerProperty(0)));
        loopManiaWorld.addUnequipped(new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        loopManiaWorld.addUnequipped(new HealthPotion(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0)));
        loopManiaWorld.convertUnequippedItemToEquipped(0, 0, 0, 0);
        loopManiaWorld.convertUnequippedItemToEquipped(1, 0, 0, 0);
        assertTrue(character.checkIfArmourEquipped());
    }

    @Test
    public void overSpawnEnemy() {
        init();
        PathPosition pos = new PathPosition(2, path);
        Slug slug1 = new Slug(pos);
        Slug slug2 = new Slug(pos);
        Slug slug3 = new Slug(pos);
        loopManiaWorld.addEnemy(slug1);
        loopManiaWorld.addEnemy(slug2);
        loopManiaWorld.addEnemy(slug3);
        assertEquals(3, loopManiaWorld.getEnemyNum());
        loopManiaWorld.possiblySpawnEnemies();
        loopManiaWorld.possiblySpawnEnemies();
        assertEquals(3, loopManiaWorld.getEnemyNum());
    }

    @Test
    public void vampireSpawnTest() throws Exception {
        init();
        loopManiaWorld.setRound(5);
        loopManiaWorld.loadCard(new BarracksCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        loopManiaWorld.loadCard(new VampireCastleCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        loopManiaWorld.convertCardToBuildingByCoordinates(0, 0, 1, 0);
        loopManiaWorld.loadCard(new VampireCastleCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        loopManiaWorld.convertCardToBuildingByCoordinates(0, 0, 1, 1);
        for (int i = 0; i < 8; i++) {
            loopManiaWorld.runTickMoves();
            loopManiaWorld.autoUpdateBuildings();
            loopManiaWorld.autoUpdateEnemies();
        }
        assertEquals(1, loopManiaWorld.getEnemyNum());
    }

    @Test
    public void doggieSpawnTest() {
        init();
        loopManiaWorld.setRound(20);
        for (int i = 0; i < 8; i++) {
            loopManiaWorld.runTickMoves();
            loopManiaWorld.autoUpdateBuildings();
            loopManiaWorld.autoUpdateEnemies();
        }
        assertEquals(1, loopManiaWorld.getEnemyNum());
        List<BasicEnemy> enemies = loopManiaWorld.getEnemiesList();
        assertTrue(enemies.get(0) instanceof Doggie);

    }

    @Test
    public void elanSpawnTest() {
        init();
        character.increaseExperience(1000000);
        loopManiaWorld.setRound(100);
        for (int i = 0; i < 8; i++) {
            loopManiaWorld.runTickMoves();
            loopManiaWorld.autoUpdateBuildings();
            loopManiaWorld.autoUpdateEnemies();
        }
        assertEquals(2, loopManiaWorld.getEnemyNum());
        List<BasicEnemy> enemies = loopManiaWorld.getEnemiesList();
        assertTrue(enemies.get(1) instanceof ElanMuske);

    }

    @Test
    public void randomWeapon() {
        init();
        for (int i = 0; i < 10; i++)
            loopManiaWorld.randomWeapon();
    }

    @Test
    public void checkIfAdj() throws Exception {
        init();
        path.clear();
        path.add(new Pair<Integer, Integer>(0, 0));
        path.add(new Pair<Integer, Integer>(1, 0));
        path.add(new Pair<Integer, Integer>(2, 0));
        path.add(new Pair<Integer, Integer>(3, 0));
        path.add(new Pair<Integer, Integer>(4, 0));
        path.add(new Pair<Integer, Integer>(4, 1));
        path.add(new Pair<Integer, Integer>(4, 2));
        path.add(new Pair<Integer, Integer>(4, 3));
        path.add(new Pair<Integer, Integer>(4, 4));
        path.add(new Pair<Integer, Integer>(3, 4));
        path.add(new Pair<Integer, Integer>(2, 4));
        path.add(new Pair<Integer, Integer>(1, 4));
        path.add(new Pair<Integer, Integer>(0, 4));
        path.add(new Pair<Integer, Integer>(0, 3));
        path.add(new Pair<Integer, Integer>(0, 2));
        path.add(new Pair<Integer, Integer>(0, 1));
        VampireCastleBuilding v = new VampireCastleBuilding(new SimpleIntegerProperty(2), new SimpleIntegerProperty(2));
        assertFalse(loopManiaWorld.checkEntityAdjacentPathWhichIndex(v));
    }

    @Test
    public void lootDropNull() {
        init();
        loopManiaWorld.lootDrop(null);
    }

    @Test
    public void removeUnequippedInventoryItemTest() throws Exception {
        init();
        Sword s1 = new Sword(new SimpleIntegerProperty(2), new SimpleIntegerProperty(2));
        Sword s2 = new Sword(new SimpleIntegerProperty(2), new SimpleIntegerProperty(2));
        Sword s3 = new Sword(new SimpleIntegerProperty(2), new SimpleIntegerProperty(2));
        loopManiaWorld.addUnequipped(s1);
        loopManiaWorld.addUnequipped(s2);
        loopManiaWorld.removeUnequippedInventoryItem(s3);
        loopManiaWorld.removeUnequippedInventoryItem(s2);
        loopManiaWorld.removeUnequippedInventoryItem(s1);
    }
}
