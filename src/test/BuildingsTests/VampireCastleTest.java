package test.BuildingsTests;

import org.javatuples.Pair;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.Character.Character;
import unsw.Enemies.Vampire;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import unsw.Buildings.VampireCastleBuilding;
import unsw.Items.BuildingCard.Card;
import unsw.Items.BuildingCard.VampireCastleCard;

public class VampireCastleTest {
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

    }

    @Test
    public void VampireSpawnTest() {
        init();
        loopManiaWorld = new LoopManiaWorld(3, 3, path);
        loopManiaWorld.setHeroCastle(0, 0);
        PathPosition pathPosition = new PathPosition(0, path);
        character = new Character(pathPosition);
        loopManiaWorld.setCharacter(character);
        VampireCastleBuilding vcb = new VampireCastleBuilding();
        vcb.setVCastlePath(2, 1);
        loopManiaWorld.setHeroCastle(0, 0);

        for (int i = 0; i < 40; i++) {
            loopManiaWorld.runTickMoves();
        }

        Vampire v = vcb.spawn(pathPosition, loopManiaWorld);
        System.out.println(loopManiaWorld.getRound());
        loopManiaWorld.addEnemy(v);
        assertEquals(1, loopManiaWorld.getEnemyNum());
        for (int i = 0; i < 40; i++)
            loopManiaWorld.runTickMoves();
        assertEquals(1, loopManiaWorld.checkEnemyNumByType(new Vampire()));

    }

    @Test
    public void VampireSpawnTest2() {
        // vampire spawns every 5 cycles completed by the character
        init();
        loopManiaWorld = new LoopManiaWorld(3, 3, path);
        PathPosition pathPosition = new PathPosition(0, path);
        character = new Character(pathPosition);
        loopManiaWorld.setCharacter(character);
        VampireCastleBuilding vcb = new VampireCastleBuilding();
        vcb.setVCastlePath(2, 1);
        Vampire v = vcb.spawn(pathPosition, loopManiaWorld);
        assertEquals(null, v);
    }

    @Test
    public void CardSpawnTest() {
        init();
        loopManiaWorld = new LoopManiaWorld(3, 3, path);
        PathPosition pathPosition = new PathPosition(0, path);
        character = new Character(pathPosition);
        loopManiaWorld.setCharacter(character);
        loopManiaWorld.setHeroCastle(0, 0);
        try {
            loopManiaWorld.loadCard(new VampireCastleCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        } catch (Exception e) {
            System.out.println("Error caught in cardSpawnTest as loading the VampireCastle Card.");
        }

        Card vampirecard = new VampireCastleCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        loopManiaWorld.convertCardToBuildingByCoordinates(vampirecard.getX(), vampirecard.getY(), 1, 1);
        assertEquals(true, loopManiaWorld.checkIfBuildingExistsInMap(new VampireCastleBuilding()));
    }

    @Test
    public void vampireCastleBuildingDisappear() throws Exception {
        init();
        loopManiaWorld = new LoopManiaWorld(3, 3, path);
        PathPosition pathPosition = new PathPosition(0, path);
        character = new Character(pathPosition);
        loopManiaWorld.setCharacter(character);
        loopManiaWorld.setHeroCastle(0, 0);
        VampireCastleCard vampirecard = (VampireCastleCard) loopManiaWorld
                .loadCard(new VampireCastleCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        loopManiaWorld.convertCardToBuildingByCoordinates(vampirecard.getX(), vampirecard.getY(), 1, 1);
        assertEquals(true, loopManiaWorld.checkIfBuildingExistsInMap(new VampireCastleBuilding()));
        for (int i = 0; i < 56; i++)
            loopManiaWorld.runTickMoves();
        assertEquals(false, loopManiaWorld.checkIfBuildingExistsInMap(new VampireCastleBuilding()));
    }

    @Test
    public void vampireCastleTypeCheck() {
        init();
        VampireCastleBuilding vampireCastleBuilding = new VampireCastleBuilding(new SimpleIntegerProperty(1),
                new SimpleIntegerProperty(1));
        assertTrue(vampireCastleBuilding.equals(new VampireCastleBuilding()));
        assertFalse(vampireCastleBuilding.equals(null));
        assertTrue(vampireCastleBuilding.equals(vampireCastleBuilding));
    }

    @Test
    public void testGetImage() {
        VampireCastleBuilding building = new VampireCastleBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        new JFXPanel();
        Image image = building.getImage();
        assertTrue(image instanceof Image);
    }
}
