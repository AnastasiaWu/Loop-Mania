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
import unsw.Enemies.*;

public class HealthPotionTest {

    private List<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();
    private LoopManiaWorld loopManiaWorld;
    private Character character;

    public LoopManiaWorld initAworld() {
        path.add(new Pair<Integer, Integer>(0, 0));
        path.add(new Pair<Integer, Integer>(1, 0));
        loopManiaWorld = new LoopManiaWorld(3, 3, path);
        PathPosition pathPosition = new PathPosition(0, path);
        character = new Character(pathPosition);
        loopManiaWorld.setCharacter(character);
        HealthPotion hp = new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        loopManiaWorld.addEntity(hp);
        PathPosition slugp = new PathPosition(1, path);
        Slug enemySlug = new Slug(slugp);
        loopManiaWorld.setHeroCastle(0, 0);
        return loopManiaWorld;

    }

    @Test
    public void reFillTest() throws Exception {
        LoopManiaWorld d = initAworld();
        Character c = d.getCharacter();
        c.decreaseHealth(10);
        loopManiaWorld.addUnequipped(new HealthPotion());
        loopManiaWorld.useHealthPotion();
        // use this potion need to find class call refill
        // we will call refill in usepotion
        assertEquals(100, c.getHealth());
    }

    @Test
    public void puchasingAndSellingPriceTest() {
        initAworld();
        loopManiaWorld.setRound(1);
        character.increaseGold(100);
        loopManiaWorld.buyItems(new HealthPotion());
        assertEquals(100 - 10, character.getGold());
        loopManiaWorld.sellItems(new HealthPotion());
        assertEquals(95, character.getGold());
    }

    @Test
    public void typeCheck() {
        HealthPotion healthPotion = new HealthPotion(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        assertTrue(healthPotion.equals(healthPotion));
        assertFalse(healthPotion.equals(null));
        assertTrue(healthPotion.equals(new HealthPotion()));
        assertFalse(healthPotion.equals(new Gold()));
    }

    @Test
    public void testGetImage() throws InterruptedException {
        HealthPotion healthPotion = new HealthPotion(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        new JFXPanel();
        Image image = healthPotion.getImage();
        assertTrue(image instanceof Image);
    }
}
