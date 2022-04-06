package test.BuildingsTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import unsw.Items.BuildingCard.Card;
import unsw.Items.BuildingCard.ZombiepitCard;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;

import org.javatuples.Pair;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.Buildings.ZombiePitBuilding;
import unsw.Character.Character;

public class ZombiepitTest {
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
        PathPosition pathPosition = new PathPosition(0, path);
        character = new Character(pathPosition);
        loopManiaWorld.setCharacter(character);
        character = this.loopManiaWorld.getCharacter();
        loopManiaWorld.setHeroCastle(0, 0);
    }

    @Test
    public void cardSpawnTest() {
        init();
        try {
            loopManiaWorld.loadCard(new ZombiepitCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        } catch (Exception e) {
            System.out.println("Error caught in cardSpawnTest as loading the Zombiepit Card.");
        }
        Card zombiepitCard = new ZombiepitCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        loopManiaWorld.convertCardToBuildingByCoordinates(zombiepitCard.getX(), zombiepitCard.getY(), 1, 1);
        assertEquals(true, loopManiaWorld.checkIfBuildingExistsInMap(new ZombiePitBuilding()));
    }

    @Test
    public void zombiePitSpawnZombie() {
        init();
        try {
            loopManiaWorld.loadCard(new ZombiepitCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        } catch (Exception e) {
            System.out.println("Error caught in cardSpawnTest as loading the Zombiepit Card.");
        }
        Card zombiepitCard = new ZombiepitCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        loopManiaWorld.convertCardToBuildingByCoordinates(zombiepitCard.getX(), zombiepitCard.getY(), 1, 1);
        for (int i = 0; i <= 8; i++) {
            loopManiaWorld.runTickMoves();
            loopManiaWorld.autoUpdateEnemies();
        }

        assertEquals(1, loopManiaWorld.getEnemyNum());
    }

    @Test
    public void zombiePitDisappear() {
        init();
        try {
            loopManiaWorld.loadCard(new ZombiepitCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        } catch (Exception e) {
            System.out.println("Error caught in cardSpawnTest as loading the Zombiepit Card.");
        }
        Card zombiepitCard = new ZombiepitCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        loopManiaWorld.convertCardToBuildingByCoordinates(zombiepitCard.getX(), zombiepitCard.getY(), 1, 1);
        for (int i = 0; i < 16; i++) {
            loopManiaWorld.runTickMoves();
        }
        assertEquals(false, loopManiaWorld.checkIfBuildingExistsInMap(new ZombiePitBuilding()));
    }

    @Test
    public void zombiePitTypeCheck() {
        init();
        ZombiePitBuilding zombiepit = new ZombiePitBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertTrue(zombiepit.equals(new ZombiePitBuilding()));
        assertFalse(zombiepit.equals(null));
        assertTrue(zombiepit.equals(zombiepit));
    }

    @Test
    public void testGetImage() {
        ZombiePitBuilding building = new ZombiePitBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        new JFXPanel();
        Image image = building.getImage();
        assertTrue(image instanceof Image);
    }

}
