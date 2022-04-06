package test.BuildingsTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import unsw.Items.BuildingCard.Card;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;

import org.javatuples.Pair;
import unsw.Items.BuildingCard.BarracksCard;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.Buildings.BarracksBuilding;
import unsw.Character.Character;

public class BarracksTest {
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
            loopManiaWorld.loadCard(new BarracksCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        } catch (Exception e) {
            System.out.println("Error caught in cardSpawnTest as loading the Barracks Card.");
        }
        Card barracksCard = new BarracksCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        loopManiaWorld.convertCardToBuildingByCoordinates(barracksCard.getX(), barracksCard.getY(), 2, 0);
        assertEquals(true, loopManiaWorld.checkIfBuildingExistsInMap(new BarracksBuilding()));
    }

    @Test
    public void cardSpawnFailTest() {
        init();
        try {
            loopManiaWorld.loadCard(new BarracksCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        } catch (Exception e) {
            System.out.println("Error caught in cardSpawnTest as loading the Barracks Card.");
        }
        Card barracksCard = new BarracksCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        loopManiaWorld.convertCardToBuildingByCoordinates(barracksCard.getX(), barracksCard.getY(), 1, 1);
        assertEquals(false, loopManiaWorld.checkIfBuildingExistsInMap(new BarracksBuilding()));
    }

    @Test
    public void soldierSpawnTest() {
        init();
        try {
            loopManiaWorld.loadCard(new BarracksCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        } catch (Exception e) {
            System.out.println("Error caught in cardSpawnTest as loading the Barracks Card.");
        }
        Card barracksCard = new BarracksCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        loopManiaWorld.convertCardToBuildingByCoordinates(barracksCard.getX(), barracksCard.getY(), 1, 0);
        loopManiaWorld.runTickMoves();
        assertEquals(1, loopManiaWorld.getSoldiers().size());
    }

    @Test
    public void barracksDisappearTest() {
        init();
        try {
            loopManiaWorld.loadCard(new BarracksCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        } catch (Exception e) {
            System.out.println("Error caught in cardSpawnTest as loading the Barracks Card.");
        }
        Card barracksCard = new BarracksCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        loopManiaWorld.convertCardToBuildingByCoordinates(barracksCard.getX(), barracksCard.getY(), 1, 0);
        for (int i = 0; i <= 16; i++) {
            loopManiaWorld.runTickMoves();
        }
        assertEquals(1, loopManiaWorld.getBuildingsNum());
    }

    @Test
    public void barracksTypeCheck() {
        init();
        BarracksBuilding barracksBuilding = new BarracksBuilding(new SimpleIntegerProperty(1),
                new SimpleIntegerProperty(1));
        assertTrue(barracksBuilding.equals(new BarracksBuilding()));
        assertFalse(barracksBuilding.equals(null));
        assertTrue(barracksBuilding.equals(barracksBuilding));
    }

    @Test
    public void testGetImage() {
        BarracksBuilding building = new BarracksBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        new JFXPanel();
        Image image = building.getImage();
        assertTrue(image instanceof Image);
    }

}
