package test.BuildingsTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.Buildings.TrapBuilding;
import unsw.Character.Character;
import unsw.Enemies.Slug;
import unsw.Items.BuildingCard.TrapCard;

public class TrapTest {

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
        character = new Character(pathPosition);
        loopManiaWorld.setCharacter(character);
        this.character = this.loopManiaWorld.getCharacter();
    }

    @Test
    public void testkillAndDestory() throws Exception {
        init();
        PathPosition slugPosition = new PathPosition(1, path);
        Slug slug = new Slug(slugPosition);
        loopManiaWorld.addEnemy(slug);

        // TrapCard trapCard = new TrapCard(trapPosition.getX(), trapPosition.getY());
        // loopManiaWorld.getCardEntities().add(trapCard);
        loopManiaWorld.loadCard(new TrapCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        TrapCard tempCard = (TrapCard) loopManiaWorld.getCardEntities().get(0);
        loopManiaWorld.convertCardToBuildingByCoordinates(tempCard.getX(), tempCard.getY(), 1, 0);
        loopManiaWorld.runBattles();

        assertEquals(loopManiaWorld.getEnemyNum(), 0);
        assertEquals(loopManiaWorld.getBuildingsNum(), 0);
    }

    @Test
    public void trapDisappearTest() throws Exception {
        init();
        loopManiaWorld.setHeroCastle(0, 0);
        loopManiaWorld.loadCard(new TrapCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        TrapCard tempCard = (TrapCard) loopManiaWorld.getCardEntities().get(0);
        loopManiaWorld.convertCardToBuildingByCoordinates(tempCard.getX(), tempCard.getY(), 1, 0);
        for (int i = 0; i < 16; i++)
            loopManiaWorld.runTickMoves();
        assertEquals(loopManiaWorld.getBuildingsNum(), 1);
    }

    @Test
    public void trapTypeCheck() {
        init();
        TrapBuilding trapBuilding = new TrapBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertTrue(trapBuilding.equals(new TrapBuilding()));
        assertFalse(trapBuilding.equals(null));
        assertTrue(trapBuilding.equals(trapBuilding));
    }

    @Test
    public void testGetImage() {
        TrapBuilding building = new TrapBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        new JFXPanel();
        Image image = building.getImage();
        assertTrue(image instanceof Image);
    }
}
