package test.BuildingsTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;

import org.javatuples.Pair;
import unsw.Items.BuildingCard.CampfireCard;
import unsw.Items.BuildingCard.Card;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.Character.Character;
import unsw.Enemies.Zombie;
import unsw.Buildings.*;

public class CampFireTest {
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
    public void damageIncreaseTest() {
        init();
        try {
            loopManiaWorld.loadCard(new CampfireCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        } catch (Exception e) {
            System.out.println("Error caught in cardSpawnTest as loading the CampFire Card.");
        }
        Card CampfireCard = new CampfireCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        loopManiaWorld.convertCardToBuildingByCoordinates(CampfireCard.getX(), CampfireCard.getY(), 1, 1);
        assertEquals(true, loopManiaWorld.checkIfBuildingExistsInMap(new CampfireBuilding()));
        PathPosition zpathPosition = new PathPosition(1, path);
        Zombie zombie = new Zombie(zpathPosition);
        loopManiaWorld.addEnemy(zombie);
        System.out.println(character.getHealth());
        loopManiaWorld.runBattles();
        System.out.println(character.getHealth());
        assertEquals(-5, zombie.getHealth());

    }

    @Test
    public void campfireDisappearTest() {
        init();
        try {
            loopManiaWorld.loadCard(new CampfireCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        } catch (Exception e) {
            System.out.println("Error caught in cardSpawnTest as loading the CampFire Card.");
        }
        Card CampfireCard = new CampfireCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        loopManiaWorld.convertCardToBuildingByCoordinates(CampfireCard.getX(), CampfireCard.getY(), 1, 1);
        assertEquals(true, loopManiaWorld.checkIfBuildingExistsInMap(new CampfireBuilding()));
        for (int i = 0; i < 8; i++) {
            loopManiaWorld.runTickMoves();
        }
        assertEquals(false, loopManiaWorld.checkIfBuildingExistsInMap(new CampfireBuilding()));
    }

    @Test
    public void campfireTypeCheck() {
        init();
        CampfireBuilding campfireBuilding = new CampfireBuilding(new SimpleIntegerProperty(1),
                new SimpleIntegerProperty(1));
        assertTrue(campfireBuilding.equals(new CampfireBuilding()));
        assertFalse(campfireBuilding.equals(null));
        assertTrue(campfireBuilding.equals(campfireBuilding));
    }

    @Test
    public void testGetImage() {
        CampfireBuilding building = new CampfireBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        new JFXPanel();
        Image image = building.getImage();
        assertTrue(image instanceof Image);
    }
}
