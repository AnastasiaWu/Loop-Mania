package test.ItemTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;

import org.javatuples.Pair;

import unsw.Enemies.Slug;
import unsw.Items.Helmet;
import unsw.Items.Sword;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.Character.Character;

public class HelmetTest {
    private List<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();
    private LoopManiaWorld loopManiaWorld;
    private Character character;
    private Slug slug;

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
        character.getEquippedBoard().setHelmet(new Helmet());
        loopManiaWorld.setHeroCastle(0, 0);
    }

    @Test
    public void typeTest() {
        Helmet helmet = new Helmet();
        assertTrue(helmet.equals(helmet));
        assertTrue(helmet.equals(new Helmet()));
        assertFalse(helmet.equals(null));
        assertFalse(helmet.equals(new Sword()));
    }

    @Test
    public void buyingPriceTest() {
        init();
        loopManiaWorld.setRound(1);
        character.increaseGold(100);
        loopManiaWorld.buyItems(new Helmet());
        assertEquals(70, character.getGold());
    }

    @Test
    public void sellingPriceTest() {
        init();
        loopManiaWorld.setRound(1);
        try {
            loopManiaWorld.addUnequipped(new Helmet());
        } catch (Exception e) {
            System.out.println("Exception of addUnequipped caught in sellingPriceTest");
        }
        loopManiaWorld.sellItems(new Helmet());
        assertEquals(25, character.getGold());
    }

    @Test
    public void wreckTest() {
        init();
        loopManiaWorld.runBattles();
        PathPosition slugPathPosition = new PathPosition(1, path);
        Slug slug = new Slug(slugPathPosition);
        loopManiaWorld.addEnemy(slug);
        loopManiaWorld.runBattles();
        loopManiaWorld.addEnemy(slug);
        loopManiaWorld.runBattles();

        assertEquals(null, character.getEquippedBoard().getHelmet());
    }

    @Test
    public void againstNormalAtttackTest() {
        init();
        loopManiaWorld.runBattles();
        PathPosition slugPathPosition = new PathPosition(1, path);
        slug = new Slug(slugPathPosition);
        loopManiaWorld.addEnemy(slug);
        loopManiaWorld.runBattles();
        assertEquals(99, character.getHealth());
    }

    @Test
    public void testGetImage() {
        Helmet helmet = new Helmet(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        new JFXPanel();
        Image image = helmet.getImage();
        assertTrue(image instanceof Image);
    }

    @Test
    public void testGetSlotImage() {
        Helmet helmet = new Helmet(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        new JFXPanel();
        Image image = helmet.getSlotImage();
        assertTrue(image instanceof Image);
    }

    @Test
    public void testEquip() {
        init();
        Helmet helmet = (Helmet)loopManiaWorld.getCharacter().getEquippedBoard().getHelmet();
        helmet.equip(loopManiaWorld.getCharacter().getEquippedBoard(), new Helmet());
        assertTrue(loopManiaWorld.getCharacter().getEquippedBoard().getHelmet() instanceof Helmet);
    }

    @Test
    public void testEquipment() {
        init();
        Helmet helmet = (Helmet)loopManiaWorld.getCharacter().getEquippedBoard().getHelmet();
        assertTrue(helmet.getEquipment(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0)) instanceof Helmet);
    }


}
