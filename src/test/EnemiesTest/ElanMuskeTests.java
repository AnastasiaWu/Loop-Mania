package test.EnemiesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.Character.Character;
import unsw.Enemies.ElanMuske;
import unsw.Enemies.Vampire;
import unsw.Items.Armour;
import unsw.Items.Helmet;
import unsw.Items.Shield;
import unsw.Items.BuildingCard.CampfireCard;
import unsw.Items.BuildingCard.TrapCard;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import javafx.embed.swing.JFXPanel;

public class ElanMuskeTests {
    private List<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();
    private LoopManiaWorld loopManiaWorld;
    private Character character;
    private ElanMuske elanMuske;

    public void setupEnemy() {
        PathPosition vampirePosition = new PathPosition(2, path);
        this.elanMuske = new ElanMuske(vampirePosition);
        loopManiaWorld.addEnemy(elanMuske);
    }

    /**
     * Initialize the sample world state.
     */
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
        PathPosition characterPosition = new PathPosition(1, path);
        Character character = new Character(characterPosition);
        loopManiaWorld.setCharacter(character);
        this.character = loopManiaWorld.getCharacter();
        setupEnemy();
    }

    @Test
    public void creationTest() {
        init();
        ElanMuske elanMuske = new ElanMuske();
        assertTrue(elanMuske instanceof ElanMuske);
    }

    @Test
    public void healthTest() {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        ElanMuske elanMuske = new ElanMuske(targetPosition);
        Vampire vampire = new Vampire(targetPosition);
        loopManiaWorld.addEnemy(elanMuske);
        loopManiaWorld.addEnemy(vampire);
        loopManiaWorld.runBattles();
        elanMuske.heal(vampire);
        assertEquals(18, vampire.getHealth());
    }

    @Test
    public void testInitState() {
        init();
        // Testing the initial state of elanMuske.
        assertEquals(elanMuske.getHealth(), 75);
        assertEquals(elanMuske.getDamage(), 15);
        assertEquals(elanMuske.getGold(), 500);
        assertEquals(elanMuske.getExperience(), 1000);
        assertTrue(elanMuske.getPosition() instanceof PathPosition);
        assertEquals(elanMuske.getPosition().getX().get(), 2);
        assertEquals(elanMuske.getPosition().getY().get(), 0);
    }

    @Test
    public void attackDamageTest() {
        init();
        loopManiaWorld.runBattles();

        assertTrue(character.getHealth() < 100);
    }

    @Test
    public void attackRangeTest() {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        ElanMuske elanMuske = new ElanMuske(targetPosition);
        loopManiaWorld.addEnemy(elanMuske);
        loopManiaWorld.runBattles();
        assertTrue(character.getHealth() < 100);
    }

    // Support range test
    @Test
    public void supportRangeTest() {
        init();
        PathPosition targetPosition = new PathPosition(5, path);
        ElanMuske elanMuske = new ElanMuske(targetPosition);
        elanMuske.setTurnToSoldier(true);
        loopManiaWorld.addEnemy(elanMuske);
        PathPosition targetPosition2 = new PathPosition(9, path);
        ElanMuske elanMuske2 = new ElanMuske(targetPosition2);
        loopManiaWorld.addEnemy(elanMuske2);
        loopManiaWorld.runBattles();
        assertTrue(character.getHealth() < 100);
    }

    @Test
    public void testSameType() {
        init();
        PathPosition targetPosition = new PathPosition(5, path);
        ElanMuske elanMuske = new ElanMuske(targetPosition);
        ElanMuske elanMuske2 = new ElanMuske(targetPosition);
        assertTrue(elanMuske.sameType(elanMuske2));
        assertFalse(elanMuske.sameType(null));
        assertTrue(elanMuske.sameType(elanMuske));
        assertFalse(elanMuske.sameType(new Vampire()));
    }

    @Test
    public void getImageTest() {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        ElanMuske elan = new ElanMuske(targetPosition);
        new JFXPanel();
        elan.getImage();
    }

    @Test
    public void testAttackArmour() {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        ElanMuske elanMuske = new ElanMuske(targetPosition);
        loopManiaWorld.addEnemy(elanMuske);
        character.getEquippedBoard().setArmour(new Armour());
        loopManiaWorld.runBattles();
        assertTrue(character.getHealth() < 100);
    }

    @Test
    public void testAttackShield() {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        ElanMuske elanMuske = new ElanMuske(targetPosition);
        character.getEquippedBoard().setShield(new Shield());
        elanMuske.attack(character);
        assertEquals(93, character.getHealth());

    }

    @Test
    public void testAttackHelmet() {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        ElanMuske elanMuske = new ElanMuske(targetPosition);
        character.getEquippedBoard().setHelmet(new Helmet());
        loopManiaWorld.runBattles();
        assertTrue(character.getHealth() < 100);
    }

}
