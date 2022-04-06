package test.LoopmaniaTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import unsw.Character.Character;
import unsw.Items.Armour;
import unsw.Items.Helmet;
import unsw.Items.Shield;
import unsw.Items.Sword;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;

public class EquippedBoardTests {
    private List<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();
    private LoopManiaWorld loopManiaWorld;
    private Character character;

    /**
     * Initialize the sample world state.
     */
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
        character = new Character(characterPosition);
        loopManiaWorld.setCharacter(character);
        this.character = loopManiaWorld.getCharacter();
    }

    @Test
    public void testSetWeapon() {
        init();
        assertTrue(character.checkIfWeaponEquipped());
        character.getEquippedBoard().setWeapon(new Sword());
        assertTrue(character.checkIfWeaponEquipped());

    }

    @Test
    public void testSetHelmet() {
        init();
        assertFalse(character.checkIfHelmetEquipped());
        character.getEquippedBoard().setHelmet(new Helmet());
        assertTrue(character.checkIfHelmetEquipped());

    }

    @Test
    public void testSetShield() {
        init();
        assertFalse(character.checkIfShieldEquipped());
        character.getEquippedBoard().setShield(new Shield());
        assertTrue(character.checkIfShieldEquipped());

    }

    @Test
    public void testSetArmour() {
        init();
        assertFalse(character.checkIfArmourEquipped());
        character.getEquippedBoard().setArmour(new Armour());
        assertTrue(character.checkIfArmourEquipped());

    }
}
