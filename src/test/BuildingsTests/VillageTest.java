package test.BuildingsTests;

import org.javatuples.Pair;
import org.junit.Test;

import java.util.List;

import unsw.Items.BuildingCard.Card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.Buildings.VillageBuilding;
import unsw.Character.Character;
import unsw.Items.BuildingCard.VillageCard;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;

public class VillageTest {
    private List<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();
    private LoopManiaWorld loopManiaWorld;
    private Character character;

    private void init() {
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
        loopManiaWorld.setHeroCastle(0, 0);

    }

    @Test
    public void VillageHealTest() {
        init();
        character.setHealth(90);
        VillageBuilding v = new VillageBuilding();
        v.Heal(character);
        assertEquals(100, character.getHealth());
    }

    @Test
    public void VillageOnPathTest() {
        init();
        VillageBuilding v = new VillageBuilding();
        // loopManiaWorld.convertCardToBuildingByCoordinates(cardNodeX, cardNodeY,
        // buildingNodeX, buildingNodeY)
        v.setVillagePath(2, 1);

        assertEquals(true, v.checkifonpath(path));

    }

    @Test
    public void CardSpawnTest() {
        init();
        try {
            loopManiaWorld.loadCard(new VillageCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        } catch (Exception e) {
            System.out.println("Error caught in cardSpawnTest as loading the Village Card.");
        }

        Card villagecard = new VillageCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        loopManiaWorld.convertCardToBuildingByCoordinates(villagecard.getX(), villagecard.getY(), 1, 0);
        assertEquals(true, loopManiaWorld.checkIfBuildingExistsInMap(new VillageBuilding()));
    }

    @Test
    public void villageDisappearTest() {
        init();
        try {
            loopManiaWorld.loadCard(new VillageCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        } catch (Exception e) {
            System.out.println("Error caught in cardSpawnTest as loading the Village Card.");
        }

        Card villagecard = new VillageCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        loopManiaWorld.convertCardToBuildingByCoordinates(villagecard.getX(), villagecard.getY(), 1, 0);
        assertEquals(true, loopManiaWorld.checkIfBuildingExistsInMap(new VillageBuilding()));
        for (int i = 0; i < 16; i++)
            loopManiaWorld.runTickMoves();
        assertEquals(false, loopManiaWorld.checkIfBuildingExistsInMap(new VillageBuilding()));
    }

    @Test
    public void villageTypeCheck() {
        init();
        VillageBuilding villageBuilding = new VillageBuilding(new SimpleIntegerProperty(1),
                new SimpleIntegerProperty(1));
        assertTrue(villageBuilding.equals(new VillageBuilding()));
        assertFalse(villageBuilding.equals(null));
        assertTrue(villageBuilding.equals(villageBuilding));
    }

    @Test
    public void testGetImage() {
        VillageBuilding building = new VillageBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        new JFXPanel();
        Image image = building.getImage();
        assertTrue(image instanceof Image);
    }
}
