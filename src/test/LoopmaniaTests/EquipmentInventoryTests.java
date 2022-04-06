package test.LoopmaniaTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;

import org.javatuples.Pair;

import unsw.Character.Character;
import unsw.Items.Stake;
import unsw.Items.Sword;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;

public class EquipmentInventoryTests {

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
        Character character = new Character(characterPosition);
        loopManiaWorld.setCharacter(character);
        this.character = loopManiaWorld.getCharacter();
    }

    @Test
    public void testMaximumEquipment() {

        // Check that the unequipped inventor can contain 24 equipment
        // at most. (height = 4 && width = 6)
        assertEquals(4, LoopManiaWorld.getUnequippedinventoryheight());
        assertEquals(6, LoopManiaWorld.getUnequippedinventorywidth());

    }

    @Test
    public void testLostOldest() throws Exception {
        init();
        for (int i = 0; i < 24; i++) {
            // Add equipment to the unequipped inventor.
            loopManiaWorld.addUnequipped(new Sword());
        }
        loopManiaWorld.addUnequipped(new Stake());
        // System.err.println(loopManiaWorld.getOldestUnequippedInventoryItems());
        assertTrue(loopManiaWorld.getNewestUnequippedInventoryItems() instanceof Stake);
    }

    @Test
    public void testGoldExperience() throws Exception {
        init();
        int beforeGold = character.getGold();
        int beforeExperience = character.getExperience();
        for (int i = 0; i < 24; i++) {
            // Add equipment to the unequipped inventor.
            loopManiaWorld.addUnequipped(new Sword());
        }
        loopManiaWorld.addUnequipped(new Sword());
        assertEquals(beforeGold + 20, character.getGold());
        assertEquals(beforeExperience + 5, character.getExperience());
    }

}
