package test.EnemiesTest;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import org.javatuples.Pair;
import unsw.Enemies.Doggie;
import unsw.Items.Sword;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.Character.Character;
import unsw.Character.Soldier;
import unsw.Enemies.ElanMuske;
import unsw.Enemies.Slug;
import unsw.Items.Armour;
import javafx.embed.swing.JFXPanel;
import unsw.Items.Shield;
import unsw.Items.Helmet;
import unsw.Character.Soldier;
import unsw.Enemies.Zombie;
import unsw.Enemies.Slug;
import unsw.loopmania.EquippedBoard;

public class DoggieTest {
    private List<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();
    private LoopManiaWorld loopManiaWorld;
    private Character character;
    private Doggie doggie;

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
        PathPosition pathPosition = new PathPosition(2, path);
        Doggie doggie = new Doggie(pathPosition);
        loopManiaWorld.addEnemy(doggie);
        loopManiaWorld.runBattles();
        assertEquals(90, character.getHealth());

    }

    // Loop drop test
    @Test
    public void loopDropTest() {
        init();
        PathPosition pathPosition = new PathPosition(2, path);
        Doggie doggie = new Doggie(pathPosition);
        loopManiaWorld.addEnemy(doggie);

        character.getEquippedBoard().setWeapon(new Sword());

        int itemsNumber = loopManiaWorld.getNumItems();
        loopManiaWorld.runBattles();
        loopManiaWorld.lootDrop(doggie);

        assertTrue(loopManiaWorld.getNumItems() >= itemsNumber + 1);
    }

    @Test
    public void testSameType() {
        init();
        Doggie doggie = new Doggie();
        Doggie doggie2 = new Doggie();
        assertTrue(doggie.sameType(doggie2));
        assertFalse(doggie.sameType(null));
        assertTrue(doggie.sameType(doggie));
        assertFalse(doggie.sameType(new ElanMuske()));
    }

    @Test
    public void getImageTest() {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        Doggie doggie = new Doggie(targetPosition);
        new JFXPanel();
        doggie.getImage();
    }

    @Test
    public void attackDamageTest() {
        init();
        PathPosition pathPosition = new PathPosition(2, path);
        Doggie doggie= new Doggie(pathPosition);
        loopManiaWorld.addEnemy(doggie);
        doggie.getPosition();
        int goldBefore = character.getGold();
        int expBefore = character.getExperience();
        loopManiaWorld.runBattles();
        assertEquals(90, this.character.getHealth());
        int goldAfter = character.getGold();
        int expAfter = character.getExperience();
        assertEquals(goldBefore + doggie.getGold(), goldAfter);
        assertEquals(expBefore + doggie.getExperience(), expAfter);
    }

    @Test
    public void testAttackArmour() {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        Doggie doggie = new Doggie(targetPosition);
        loopManiaWorld.addEnemy(doggie);
        character.getEquippedBoard().setArmour(new Armour());
        loopManiaWorld.runBattles();
        assertTrue(character.getHealth() < 100);
    }

    @Test
    public void testAttackShield() {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        Doggie doggie = new Doggie(targetPosition);
        loopManiaWorld.addEnemy(doggie);
        character.getEquippedBoard().setShield(new Shield());
        loopManiaWorld.runBattles();
        assertEquals(100, character.getHealth());

    }

    @Test
    public void testAttackHelmet() {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        Doggie doggie = new Doggie(targetPosition);
        loopManiaWorld.addEnemy(doggie);
        character.getEquippedBoard().setHelmet(new Helmet());
        loopManiaWorld.runBattles();
        assertTrue(character.getHealth() < 100);
    }

    @Test
    public void damageTest() {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        Doggie doggie = new Doggie(targetPosition);
        assertEquals(10, doggie.getDamage());
    }

    @Test
    public void turnToSoldierTest() {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        Doggie doggie = new Doggie(targetPosition);
        doggie.setTurnToSoldier(true);
        assertEquals(true, doggie.checkIfTurnToSoldier()); 
    }

    @Test
    public void supportRangeTest() {
        init();
        EquippedBoard equippedBoard = character.getEquippedBoard();
        loopManiaWorld.runBattles();

        PathPosition slugPosition = new PathPosition(1, path);
        Slug slug = new Slug(slugPosition);
        loopManiaWorld.addEnemy(slug);
        PathPosition zombiePosition = new PathPosition(3, path);
        Zombie zombie = new Zombie(zombiePosition);

        loopManiaWorld.addEnemy(zombie);

        PathPosition targetPosition = new PathPosition(2, path);
        Doggie doggie = new Doggie(targetPosition);
        loopManiaWorld.addEnemy(doggie);

        equippedBoard.setWeapon(new Sword());
        loopManiaWorld.runBattles();
        assertTrue(character.getHealth() < 100);

    }

    @Test
    public void ifsupportTest() {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        Doggie doggie = new Doggie(targetPosition);
        assertEquals(true, doggie.ifSupport(character));
    }

}
