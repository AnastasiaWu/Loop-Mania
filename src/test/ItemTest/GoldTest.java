package test.ItemTest;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;

import org.javatuples.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.Character.Character;
import unsw.Items.Gold;
import unsw.Items.HealthPotion;
import unsw.Items.Sword;

public class GoldTest {
    private List<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();
    private LoopManiaWorld loopManiaWorld;
    private Character character;

    public LoopManiaWorld initAworld() {
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
        return loopManiaWorld;
    }

    @Test
    public void goldDecreaseTest() {
        // gold should not less than 0
        LoopManiaWorld d = initAworld();
        for (int i = 0; i < 8; i++)
            loopManiaWorld.runTickMoves();
        Character c = d.getCharacter();
        Sword sword = new Sword();
        c.increaseGold(100);
        int before = c.getGold();
        System.out.println(c.getGold());
        d.buyItems(sword);

        assert c.getGold() > 0;
        int after = c.getGold();
        assert before - 20 == after;
        before = c.getGold();
        try {
            c.decreaseGold(20);
        } catch (Exception e) {
            System.out.println("Erro caught in goldDecreaseTest");
        }
        after = c.getGold();
        assert before - 20 == after;

    }

    @Test
    public void goldIncreaseTest() {
        LoopManiaWorld d = initAworld();
        Character c = d.getCharacter();
        int before = c.getGold();
        c.increaseGold(5);

        System.out.println(c.getGold());

        int after = c.getGold();
        assert before + 5 == after;
    }

    @Test
    public void setGoldTest() {
        initAworld();
        Gold gold = new Gold(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        gold.setGold(1);
        assertEquals(1, gold.getGold());
        ;
    }

    @Test
    public void typeCheck() {
        Gold gold = new Gold(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        assertTrue(gold.equals(gold));
        assertFalse(gold.equals(null));
        assertTrue(gold.equals(new Gold()));
        assertFalse(gold.equals(new HealthPotion()));
    }

    @Test
    public void testGetImage() throws InterruptedException {
        Gold gold = new Gold(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        new JFXPanel();
        Image image = gold.getImage();
        assertTrue(image instanceof Image);
    }
}
