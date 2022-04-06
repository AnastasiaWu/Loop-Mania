package test.EnemiesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import org.javatuples.Pair;

import unsw.Enemies.Slug;
import unsw.Enemies.Vampire;
import unsw.Enemies.Zombie;
import unsw.Items.Armour;
import unsw.Items.Helmet;
import unsw.Items.Shield;
import unsw.Items.Sword;
import unsw.loopmania.EquippedBoard;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.Character.Character;
import unsw.Enemies.ElanMuske;
import javafx.embed.swing.JFXPanel;

public class zombieTests {
    private List<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();
    private LoopManiaWorld loopManiaWorld;
    private Character character;

    public void init() {
        path.add(new Pair<Integer, Integer>(0, 0));
        path.add(new Pair<Integer, Integer>(1, 0));
        path.add(new Pair<Integer, Integer>(2, 0));
        path.add(new Pair<Integer, Integer>(3, 0));
        path.add(new Pair<Integer, Integer>(4, 0));
        path.add(new Pair<Integer, Integer>(5, 0));
        path.add(new Pair<Integer, Integer>(5, 1));
        path.add(new Pair<Integer, Integer>(5, 2));
        path.add(new Pair<Integer, Integer>(5, 3));
        path.add(new Pair<Integer, Integer>(5, 4));
        path.add(new Pair<Integer, Integer>(5, 5));
        path.add(new Pair<Integer, Integer>(4, 5));
        path.add(new Pair<Integer, Integer>(4, 5));
        path.add(new Pair<Integer, Integer>(3, 5));
        path.add(new Pair<Integer, Integer>(2, 5));
        path.add(new Pair<Integer, Integer>(1, 5));
        path.add(new Pair<Integer, Integer>(0, 5));
        path.add(new Pair<Integer, Integer>(0, 4));
        path.add(new Pair<Integer, Integer>(0, 3));
        path.add(new Pair<Integer, Integer>(0, 2));
        path.add(new Pair<Integer, Integer>(0, 1));
        path.add(new Pair<Integer, Integer>(0, 0));
        loopManiaWorld = new LoopManiaWorld(5, 5, path);
        PathPosition pathPosition = new PathPosition(0, path);
        Character character = new Character(pathPosition);
        loopManiaWorld.setCharacter(character);
        PathPosition zombiePosition = new PathPosition(1, path);
        Zombie zombie = new Zombie(zombiePosition);
        loopManiaWorld.addEnemy(zombie);
        this.character = loopManiaWorld.getCharacter();

    }

    // Attack damage test
    @Test
    public void attackDamageTest() {
        init();
        loopManiaWorld.runBattles();
        assertEquals(94, character.getHealth());
    }

    // Attack range test
    @Test
    public void attackRangeTest() {
        init();
        loopManiaWorld.runBattles();
        PathPosition zombiePosition = new PathPosition(2, path);
        Zombie zombie = new Zombie(zombiePosition);
        loopManiaWorld.addEnemy(zombie);
        zombie.getPosition();
        zombie.setTurnToSoldier(true);
        EquippedBoard equippedBoard = character.getEquippedBoard();
        equippedBoard.setWeapon(new Sword());
        loopManiaWorld.runBattles();
        assertEquals(94 - 6, character.getHealth());

    }

    @Test
    public void testSameType() {
        init();
        Zombie zombie = new Zombie();
        Zombie zombie2 = new Zombie();
        assertTrue(zombie.sameType(zombie2));
        assertFalse(zombie.sameType(null));
        assertTrue(zombie.sameType(zombie));
        assertFalse(zombie.sameType(new Vampire()));
    }

    // Support range test
    @Test
    public void supportRangeTest() {
        init();
        loopManiaWorld.runBattles();
        assertEquals(94, character.getHealth());
        PathPosition slugPosition = new PathPosition(1, path);
        Slug slug = new Slug(slugPosition);
        loopManiaWorld.addEnemy(slug);
        PathPosition zombiePosition = new PathPosition(3, path);
        Zombie zombie = new Zombie(zombiePosition);
        loopManiaWorld.addEnemy(zombie);

        PathPosition zombiePosition2 = new PathPosition(7, path);
        Zombie zombie2 = new Zombie(zombiePosition2);
        loopManiaWorld.addEnemy(zombie2);

        EquippedBoard equippedBoard = character.getEquippedBoard();
        equippedBoard.setWeapon(new Sword());
        loopManiaWorld.runBattles();
        assertEquals(94 - slug.getDamage() - zombie.getDamage(), character.getHealth());

    }

    // Loop drop test
    @Test
    public void loopDropTest() {
        init();
        int itemsNumber = loopManiaWorld.getUnequippedInventoryItems().size();
        loopManiaWorld.runBattles();
        assertNotEquals(itemsNumber, loopManiaWorld.getUnequippedInventoryItems().size());

    }

    @Test
    public void testAttackArmour() {
        init();
        character.getEquippedBoard().setArmour(new Armour());
        loopManiaWorld.runBattles();
        assertTrue(character.getHealth() < 100);
    }

    @Test
    public void testAttackShield() {
        init();
        character.getEquippedBoard().setShield(new Shield());
        loopManiaWorld.runBattles();
        assertEquals(100, character.getHealth());

    }

    @Test
    public void testAttackHelmet() {
        init();
        character.getEquippedBoard().setHelmet(new Helmet());
        loopManiaWorld.runBattles();
        assertTrue(character.getHealth() < 100);
    }

    @Test
    public void healTest() {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        Zombie zombie = new Zombie(targetPosition);
        ElanMuske elan = new ElanMuske(targetPosition);
        loopManiaWorld.addEnemy(zombie);
        loopManiaWorld.addEnemy(elan);
        character.attack(zombie);
        elan.heal(zombie);
        assertEquals(10, zombie.getHealth());

    }

    @Test
    public void getImageTest() {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        Zombie zombie = new Zombie(targetPosition);
        new JFXPanel();
        zombie.getImage();
    }
}
