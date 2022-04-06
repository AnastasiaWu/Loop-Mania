package test.BuildingsTests;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;

import org.javatuples.Pair;

import unsw.Items.Armour;
import unsw.Items.HealthPotion;
import unsw.Items.Helmet;
import unsw.Items.Shield;
import unsw.Items.Sword;
import unsw.loopmania.Entity;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.Buildings.HeroCastleBuilding;
import unsw.Character.Character;

public class HeroCastleBuildingsTests {
    private List<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();
    private LoopManiaWorld loopManiaWorld;
    private Character character;

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
        loopManiaWorld.getBuildingsNum();
        PathPosition pathPosition = new PathPosition(1, path);
        Character character = new Character(pathPosition);
        loopManiaWorld.setCharacter(character);
        this.character = this.loopManiaWorld.getCharacter();
    }

    // Test set castle
    // Castle are created as the map generated, so the setHeroCastle would be called
    // by loopManiaWorld
    @Test
    public void setHeroCastleTests() {
        init();
        assertEquals(0, loopManiaWorld.getBuildingsNum());
        assertFalse(loopManiaWorld.checkIfBuildingExistsInMap(new HeroCastleBuilding()));
        loopManiaWorld.setHeroCastle(0, 0);
        HeroCastleBuilding heroCastleBuilding = loopManiaWorld.getHeroCastle();
        assertEquals(1, loopManiaWorld.getBuildingsNum());
        assertTrue(loopManiaWorld.checkIfBuildingExistsInMap(heroCastleBuilding));
    }

    // Test set castle error
    @Test
    public void setHeroCastleErrorTests() {
        init();
        assertEquals(0, loopManiaWorld.getBuildingsNum());
        assertFalse(loopManiaWorld.checkIfBuildingExistsInMap(new HeroCastleBuilding()));
        loopManiaWorld.setHeroCastle(1, 1);
        HeroCastleBuilding heroCastleBuilding = loopManiaWorld.getHeroCastle();
        assertEquals(null, heroCastleBuilding);
        assertEquals(0, loopManiaWorld.getBuildingsNum());
        assertFalse(loopManiaWorld.checkIfBuildingExistsInMap(new HeroCastleBuilding()));
    }

    // Test buy items
    @Test
    public void buyItemsTests() {
        init();
        loopManiaWorld.setHeroCastle(0, 0);
        // for (int i = 0; i < 9; i++) {
        //     loopManiaWorld.runTickMoves();
        // }
        character.increaseGold(100);
        loopManiaWorld.setRound(1);
        int wealth = character.getGold();
        Sword sword = new Sword();
        loopManiaWorld.buyItems(sword);
        assertTrue(loopManiaWorld.checkIfItemInUnequippedInventory(sword));
        assertEquals(wealth - 20, character.getGold());
        assertEquals(1, loopManiaWorld.getNumItems());

    }

    // Test sell items
    @Test
    public void sellItemsTests() {
        init();
        loopManiaWorld.setHeroCastle(0, 0);
        // for (int i = 0; i < 9; i++) {
        //     loopManiaWorld.runTickMoves();
        // }
        character.increaseGold(100);
        loopManiaWorld.setRound(1);
        Sword sword = new Sword();
        loopManiaWorld.buyItems(sword);
        int wealth = character.getGold();
        loopManiaWorld.sellItems(sword);
        assertFalse(loopManiaWorld.checkIfItemInUnequippedInventory(sword));
        assertEquals(wealth + 15, character.getGold());
        assertEquals(0, loopManiaWorld.getNumItems());

    }

    @Test
    public void heroCastleTypeCheck() {
        init();
        HeroCastleBuilding vampireCastleBuilding = new HeroCastleBuilding(new SimpleIntegerProperty(1),
                new SimpleIntegerProperty(1));
        assertTrue(vampireCastleBuilding.equals(new HeroCastleBuilding()));
        assertFalse(vampireCastleBuilding.equals(null));
        assertTrue(vampireCastleBuilding.equals(vampireCastleBuilding));
    }

    @Test
    public void survivalModeBuyItems() {
        init();
        loopManiaWorld.setHeroCastle(0, 0);
        loopManiaWorld.setGameMode("Survival");
        // for (int i = 0; i < 8; i++)
        //     loopManiaWorld.runTickMoves();
        character.increaseGold(100);
        loopManiaWorld.setRound(1);
        loopManiaWorld.buyItems(new HealthPotion());
        assertEquals(90, character.getGold());
        assertEquals(1, loopManiaWorld.getNumItems());
        loopManiaWorld.buyItems(new HealthPotion());
        assertEquals(90, character.getGold());
        assertEquals(1, loopManiaWorld.getNumItems());
    }

    @Test
    public void berserkerModeBuyItemsTest() {
        init();
        loopManiaWorld.setHeroCastle(0, 0);
        loopManiaWorld.setGameMode("Bersek");
        // for (int i = 0; i < 8; i++)
        //     loopManiaWorld.runTickMoves();
        character.increaseGold(100);
        loopManiaWorld.setRound(1);
        loopManiaWorld.buyItems(new Armour());
        assertEquals(80, character.getGold());
        assertEquals(1, loopManiaWorld.getNumItems());
        loopManiaWorld.buyItems(new Armour());
        assertEquals(80, character.getGold());
        assertEquals(1, loopManiaWorld.getNumItems());
        loopManiaWorld.buyItems(new Shield());
        assertEquals(80, character.getGold());
        assertEquals(1, loopManiaWorld.getNumItems());

        loopManiaWorld.buyItems(new Helmet());
        assertEquals(80, character.getGold());
        assertEquals(1, loopManiaWorld.getNumItems());
    }

    @Test
    public void testGetImage() {
        HeroCastleBuilding building = new HeroCastleBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        new JFXPanel();
        Image image = building.getImage();
        assertTrue(image instanceof Image);
    }

}
