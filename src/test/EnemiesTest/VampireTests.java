package test.EnemiesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.Character.Character;
import unsw.Enemies.Slug;
import unsw.Enemies.Vampire;
import unsw.Items.Armour;
import unsw.Items.Helmet;
import unsw.Items.Shield;
import unsw.Items.BuildingCard.CampfireCard;
import unsw.Items.BuildingCard.TrapCard;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.Enemies.ElanMuske;
import javafx.embed.swing.JFXPanel;

public class VampireTests {

    private List<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();
    private LoopManiaWorld loopManiaWorld;
    private Character character;
    private Vampire vampire;

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

    public void setupEnemy() {
        PathPosition vampirePosition = new PathPosition(2, path);
        this.vampire = new Vampire(vampirePosition);
        loopManiaWorld.addEnemy(vampire);
    }

    @Test
    public void testInitState() {
        init();
        // Testing the initial state of vampire.
        assertEquals(vampire.getHealth(), 15);
        assertEquals(vampire.getDamage(), 5);
        assertEquals(vampire.getGold(), 50);
        assertEquals(vampire.getExperience(), 100);
        assertTrue(vampire.getPosition() instanceof PathPosition);
        assertEquals(vampire.getPosition().getX().get(), 2);
        assertEquals(vampire.getPosition().getY().get(), 0);
    }

    @Test
    public void attackDamageTest() {
        init();
        loopManiaWorld.runBattles();

        assertTrue(character.getHealth() < 100);
    }

    // Attack range test
    @Test
    public void attackRangeTest() {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        Vampire vampire = new Vampire(targetPosition);
        loopManiaWorld.addEnemy(vampire);
        loopManiaWorld.runBattles();
        assertTrue(character.getHealth() < 100);
    }

    // Support range test
    @Test
    public void supportRangeTest() {
        init();
        PathPosition targetPosition = new PathPosition(5, path);
        Vampire vampire = new Vampire(targetPosition);
        vampire.setTurnToSoldier(true);
        loopManiaWorld.addEnemy(vampire);
        PathPosition targetPosition2 = new PathPosition(9, path);
        Vampire vampire2 = new Vampire(targetPosition2);
        loopManiaWorld.addEnemy(vampire2);
        loopManiaWorld.runBattles();
        assertTrue(character.getHealth() < 100);
    }

    @Test
    public void testSameType() {
        init();
        Vampire vampire = new Vampire();
        Vampire vampire2 = new Vampire();
        assertTrue(vampire.sameType(vampire2));
        assertFalse(vampire.sameType(null));
        assertTrue(vampire.sameType(vampire));
        assertFalse(vampire.sameType(new Slug()));
    }

    // Loop drop test
    @Test
    public void loopDropTest() throws Exception {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        Vampire vampire = new Vampire(targetPosition);
        loopManiaWorld.addEnemy(vampire);

        int itemsNumber = loopManiaWorld.getUnequippedInventoryItems().size();
        loopManiaWorld.loadCard(new TrapCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        TrapCard tempCard = (TrapCard) loopManiaWorld.getCardEntities().get(0);
        loopManiaWorld.convertCardToBuildingByCoordinates(tempCard.getX(), tempCard.getY(), 2, 0);
        loopManiaWorld.runBattles();

        assertEquals(itemsNumber + 2, loopManiaWorld.getUnequippedInventoryItems().size());
    }

    @Test
    public void testAttackArmour() {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        Vampire vampire = new Vampire(targetPosition);
        loopManiaWorld.addEnemy(vampire);
        character.getEquippedBoard().setArmour(new Armour());
        loopManiaWorld.runBattles();
        assertTrue(character.getHealth() < 100);
    }

    @Test
    public void testAttackShield() {
        for (int i = 0; i < 5; i++) {
            init();
            PathPosition targetPosition = new PathPosition(2, path);
            Vampire vampire = new Vampire(targetPosition);
            loopManiaWorld.addEnemy(vampire);
            character.getEquippedBoard().setShield(new Shield());
            loopManiaWorld.runBattles();
            assertTrue(character.getHealth() < 100);
        }
    }

    @Test
    public void testAttackHelmet() {
        for (int i = 0; i < 5; i++) {
            init();
            PathPosition targetPosition = new PathPosition(2, path);
            Vampire vampire = new Vampire(targetPosition);
            loopManiaWorld.addEnemy(vampire);
            character.getEquippedBoard().setHelmet(new Helmet());
            loopManiaWorld.runBattles();
            assertTrue(character.getHealth() < 100);
        }
    }

    @Test
    public void testMoveAwayCampfire() throws Exception {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        Vampire vampire = new Vampire(targetPosition);
        loopManiaWorld.addEnemy(vampire);
        loopManiaWorld.setHeroCastle(0, 0);

        loopManiaWorld.loadCard(new CampfireCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        CampfireCard tempCard = (CampfireCard) loopManiaWorld.getCardEntities().get(0);
        loopManiaWorld.convertCardToBuildingByCoordinates(tempCard.getX(), tempCard.getY(), 2, 1);

        loopManiaWorld.runTickMoves();
        assertEquals(vampire.getX(), 4);
        assertEquals(vampire.getY(), 0);
    }

    @Test
    public void healTest() {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        Vampire vampire = new Vampire(targetPosition);
        ElanMuske elan = new ElanMuske(targetPosition);
        character.attack(vampire);
        elan.heal(vampire);
        assertEquals(13, vampire.getHealth());

    }

    @Test
    public void getImageTest() {
        init();
        PathPosition targetPosition = new PathPosition(2, path);
        Vampire vampire = new Vampire(targetPosition);
        new JFXPanel();
        vampire.getImage();
    }
}
