package test.ItemTest;

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

import unsw.Enemies.Slug;
import unsw.Items.Shield;
import unsw.Items.Staff;
import unsw.Items.Sword;
import unsw.Items.Weapon;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.Character.Character;

public class ShieldTest {
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
        character.getEquippedBoard().setShield(new Shield());
        loopManiaWorld.setHeroCastle(0, 0);
    }

    @Test
    public void buyingPriceTest() {
        init();
        loopManiaWorld.setRound(1);
        character.increaseGold(100);
        loopManiaWorld.buyItems(new Shield());
        assertEquals(75, character.getGold());
    }

    @Test
    public void sellingPriceTest() {
        init();
        loopManiaWorld.setRound(1);
        try {
            loopManiaWorld.addUnequipped(new Shield());
        } catch (Exception e) {
            System.out.println("Exception of addUnequipped caught in sellingPriceTest");
        }
        loopManiaWorld.sellItems(new Shield());
        assertEquals(20, character.getGold());
    }

    @Test
    public void typeTest() {
        init();
        Shield s = new Shield(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertTrue(s.equals(s));
        assertFalse(s.equals(null));
        assertTrue(s.equals(new Shield()));
        assertFalse(s.equals(new Staff()));
    }

    @Test
    public void wreckTest() {
        init();
        Shield s = new Shield();
        assertTrue(s.wreckcheck(null));
        loopManiaWorld.runBattles();
        PathPosition slugPathPosition = new PathPosition(1, path);
        Slug slug1 = new Slug(slugPathPosition);
        character.getEquippedBoard().setWeapon(new Sword());
        loopManiaWorld.addEnemy(slug1);
        loopManiaWorld.runBattles();
        Slug slug2 = new Slug(slugPathPosition);
        loopManiaWorld.addEnemy(slug2);
        loopManiaWorld.runBattles();
        character.getEquippedBoard().setWeapon(new Sword());
        Slug slug3 = new Slug(slugPathPosition);
        loopManiaWorld.addEnemy(slug3);
        loopManiaWorld.runBattles();
        assertEquals(null, character.getEquippedBoard().getShield());
    }

    @Test
    public void againstNormalAtttackTest() {
        init();
        loopManiaWorld.runBattles();
        assertEquals(100, character.getHealth());
    }

    @Test
    public void testGetImage() {
        Shield shield = new Shield(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        new JFXPanel();
        Image image = shield.getImage();
        assertTrue(image instanceof Image);
    }

    @Test
    public void testGetSlotImage() {
        Shield shield = new Shield(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        new JFXPanel();
        Image image = shield.getSlotImage();
        assertTrue(image instanceof Image);
    }

    @Test
    public void testEquip() {
        init();
        Shield shield = (Shield)loopManiaWorld.getCharacter().getEquippedBoard().getShield();
        shield.equip(loopManiaWorld.getCharacter().getEquippedBoard(), new Shield());
        assertTrue(loopManiaWorld.getCharacter().getEquippedBoard().getShield() instanceof Shield);
    }

    @Test
    public void testEquipment() {
        init();
        Shield shield = (Shield)loopManiaWorld.getCharacter().getEquippedBoard().getShield();
        assertTrue(shield.getEquipment(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0)) instanceof Shield);
    }





    // @Test
    // public void againstVampireAtttackTest() {
    // init();
    // int characterHealth = character.getHealth();
    // loopManiaWorld.runBattles();
    // while (characterHealth - 2 == character.getHealth()) {
    // character.getEquippedBoard().setShield(new Shield());
    // character.getEquippedBoard().setWeapon(new Sword());
    // PathPosition slugPathPosition = new PathPosition(1, path);
    // Slug slug = new Slug(slugPathPosition);
    // loopManiaWorld.addEnemy(slug);
    // }
    // }
}
