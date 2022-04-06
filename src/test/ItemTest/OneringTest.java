package test.ItemTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import unsw.loopmania.LoopManiaWorld;
import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import unsw.loopmania.PathPosition;
import unsw.Character.Character;
import unsw.Items.HealthPotion;
import unsw.Items.Onering;

public class OneringTest {
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
    public void ReviveTest() {
        init();
        character.setHealth(0);
        Onering onering = new Onering();
        onering.revive(character);
        assertEquals(100, character.getHealth());
    }

    @Test
    public void oneringTypeCheckTest() {
        Onering onering = new Onering();
        assertTrue(onering.equals(onering));
        assertTrue(onering.equals(new Onering()));
        assertFalse(onering.equals(null));
        assertFalse(onering.equals(new HealthPotion()));
    }

    @Test
    public void buyPriceTest() {
        Onering onering = new Onering();
        assertEquals(onering.getBuyingPrice(), 9999999);
    }

    @Test
    public void oneringBuyingPriceTest() {
        init();
        Onering onering = new Onering();
        character.increaseGold(100);
        loopManiaWorld.buyItems(onering);
        assertEquals(0, loopManiaWorld.getNumItems());
    }

    @Test
    public void oneringSellingPriceTest() throws Exception {
        init();
        for (int i = 0; i < 8; i++)
            loopManiaWorld.runTickMoves();
        Onering onering = new Onering();
        loopManiaWorld.addUnequipped(new Onering());
        loopManiaWorld.sellItems(onering);
        assertTrue(character.getGold() > 0);
    }

    @Test
    public void testGetImage() throws InterruptedException {
        Onering ring = new Onering(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        new JFXPanel();
        Image image = ring.getImage();
        assertTrue(image instanceof Image);
    }
}
