package test.ItemTest;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import unsw.Items.Staff;
import unsw.Items.Sword;
import unsw.loopmania.LoopManiaWorld;
import java.util.List;
import unsw.loopmania.PathPosition;
import unsw.Character.Character;
import org.javatuples.Pair;
import unsw.Enemies.Slug;
import unsw.Enemies.Zombie;

public class StaffTests {
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
        PathPosition slugPathPosition = new PathPosition(1, path);
        Slug slug = new Slug(slugPathPosition);
        loopManiaWorld.addEnemy(slug);
        Staff staff = new Staff();
        character.getEquippedBoard().setWeapon(staff);
        loopManiaWorld.setHeroCastle(0, 0);
    }

    @Test
    public void damageTest() {
        init();
        loopManiaWorld.runBattles();
        assertEquals(100 - 2, character.getHealth());
    }

    @Test
    public void tranceInflictTest() {
        init();
        int characterHealth = character.getHealth();
        loopManiaWorld.runBattles();
        while (characterHealth - 2 == character.getHealth()) {
            Staff staff = new Staff();
            character.getEquippedBoard().setWeapon(staff);
            PathPosition slugPathPosition = new PathPosition(1, path);
            Slug slug = new Slug(slugPathPosition);
            loopManiaWorld.addEnemy(slug);
            loopManiaWorld.runBattles();
            characterHealth = character.getHealth();
        }
        // If until the character dead, there's still no chance to inflict the trance
        if (character.getHealth() < 0) {
            assertEquals(false, true);
        }
    }

    @Test
    public void transferDeadEnemiesToSoldiersTest() {
        init();
        PathPosition zombiePathPosition1 = new PathPosition(2, path);
        Zombie zombieAttack = new Zombie(zombiePathPosition1);
        loopManiaWorld.addEnemy(zombieAttack);
        PathPosition zombiePathPosition2 = new PathPosition(3, path);
        Zombie zombieSupport = new Zombie(zombiePathPosition2);
        loopManiaWorld.addEnemy(zombieSupport);
        loopManiaWorld.runBattles();
        assertEquals(15 - 2 - 3 - 2, zombieAttack.getHealth());
    }

    @Test
    public void wreckTest() {
        init();
        loopManiaWorld.runBattles();
        PathPosition pathPosition = new PathPosition(0, path);
        Slug slug = new Slug(pathPosition);
        loopManiaWorld.addEnemy(slug);
        loopManiaWorld.runBattles();
        assertEquals(null, character.getEquippedBoard().getWeapon());
    }

    @Test
    public void staffTypeCheck() {
        Staff staff = new Staff();
        assertTrue(staff.equals(staff));
        assertTrue(staff.equals(new Staff()));
        assertFalse(staff.equals(null));
        assertFalse(staff.equals(new Sword()));
    }

    @Test
    public void staffBuyingSellingPriceCheck() {
        init();
        loopManiaWorld.setRound(1);
        character.increaseGold(100);
        loopManiaWorld.buyItems(new Staff());
        assertEquals(100 - 10, character.getGold());
        loopManiaWorld.sellItems(new Staff());
        assertEquals(100 - 10 + 5, character.getGold());
    }

    @Test
    public void testGetImage() {
        Staff staff = new Staff(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        new JFXPanel();
        Image image = staff.getImage();
        assertTrue(image instanceof Image);
    }

    @Test
    public void testEquip() {
        init();
        Staff staff = (Staff)loopManiaWorld.getCharacter().getEquippedBoard().getWeapon();
        staff.equip(loopManiaWorld.getCharacter().getEquippedBoard(), new Sword());
        assertTrue(loopManiaWorld.getCharacter().getEquippedBoard().getWeapon() instanceof Sword);
    }

    @Test
    public void testEquipment() {
        init();
        Staff staff = (Staff)loopManiaWorld.getCharacter().getEquippedBoard().getWeapon();
        assertTrue(staff.getEquipment(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0)) instanceof Staff);
    }
}
