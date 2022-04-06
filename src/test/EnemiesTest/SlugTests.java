package test.EnemiesTest;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import javafx.embed.swing.JFXPanel;

import org.javatuples.Pair;
import unsw.Enemies.Slug;
import unsw.Enemies.Vampire;
import unsw.Items.Sword;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.Character.Character;
import unsw.Enemies.ElanMuske;
import unsw.Items.Armour;
import javafx.embed.swing.JFXPanel;

public class SlugTests {
    private List<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();
    private LoopManiaWorld loopManiaWorld;
    private Character character;
    private Slug slug;

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
        PathPosition characterPosition = new PathPosition(1, path);
        Character character = new Character(characterPosition);
        loopManiaWorld.setCharacter(character);
        this.character = loopManiaWorld.getCharacter();
        loopManiaWorld.setHeroCastle(0, 0);

    }

    // Attack range test
    @Test
    public void attackRangeTest() {
        init();
        PathPosition slugPosition = new PathPosition(2, path);
        Slug slug = new Slug(slugPosition);
        loopManiaWorld.addEnemy(slug);
        loopManiaWorld.runBattles();
        assertEquals(98, character.getHealth());

    }

    // Loop drop test
    @Test
    public void loopDropTest() {
        init();
        PathPosition slugPosition = new PathPosition(2, path);
        Slug slug = new Slug(slugPosition);
        loopManiaWorld.addEnemy(slug);

        character.getEquippedBoard().setWeapon(new Sword());

        int itemsNumber = loopManiaWorld.getNumItems();
        loopManiaWorld.runBattles();
        loopManiaWorld.lootDrop(slug);

        assertTrue(loopManiaWorld.getNumItems() >= itemsNumber + 1);
    }

    @Test
    public void testSameType() {
        init();
        Slug slug = new Slug();
        Slug slug2 = new Slug();
        assertTrue(slug.sameType(slug2));
        assertFalse(slug.sameType(null));
        assertTrue(slug.sameType(slug));
        assertFalse(slug.sameType(new Vampire()));
    }

    // Attack damage test
    @Test
    public void attackDamageTest() {
        init();
        PathPosition slugPosition = new PathPosition(2, path);
        Slug slug = new Slug(slugPosition);
        loopManiaWorld.addEnemy(slug);
        slug.getPosition();
        int goldBefore = character.getGold();
        int expBefore = character.getExperience();
        loopManiaWorld.runBattles();
        assertEquals(98, this.character.getHealth());
        int goldAfter = character.getGold();
        int expAfter = character.getExperience();
        assertEquals(goldBefore + slug.getGold(), goldAfter);
        assertEquals(expBefore + slug.getExperience(), expAfter);
    }

    // Support range test
    @Test
    public void supportRangeTest() {
        init();
        PathPosition slugPosition = new PathPosition(1, path);
        Slug slug = new Slug(slugPosition);
        loopManiaWorld.addEnemy(slug);
        PathPosition slugPosition2 = new PathPosition(5, path);
        Slug slug2 = new Slug(slugPosition2);
        loopManiaWorld.addEnemy(slug2);
        loopManiaWorld.runBattles();
        assertTrue(slug.getHealth() < 0);

    }

    @Test
    public void healTest() {
        init();
        PathPosition pathPosition = new PathPosition(1, path);
        Slug slug = new Slug(pathPosition);
        ElanMuske elan = new ElanMuske(pathPosition);
        loopManiaWorld.addEnemy(slug);
        loopManiaWorld.addEnemy(elan);

        character.attack(slug);
        elan.heal(slug);
        assertEquals(4, slug.getHealth());
    }

    @Test
    public void testAttackArmour() {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        Slug slug = new Slug(targetPosition);
        loopManiaWorld.addEnemy(slug);
        character.getEquippedBoard().setArmour(new Armour());
        loopManiaWorld.runBattles();
        assertTrue(character.getHealth() < 100);
    }

    @Test
    public void getImageTest() {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        Slug slug = new Slug(targetPosition);
        new JFXPanel();
        slug.getImage();
    }
}