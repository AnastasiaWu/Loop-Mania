package test.LoopmaniaTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.Character.Character;
import unsw.Items.BuildingCard.VampireCastleCard;
import unsw.Items.BuildingCard.ZombiepitCard;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;

public class CardInventoryTests {

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
    public void testMaximumCard() throws Exception {
        // Initialize the world.
        init();
        // Check that the card will be lost as the
        // card inventory is full and a new card
        // needs to be added in.

        // Add cards into the card inventory until maximum.
        for (int i = 0; i < loopManiaWorld.getWidth(); i++) {
            loopManiaWorld.loadCard(new VampireCastleCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        }
        loopManiaWorld.loadCard(new ZombiepitCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));

        assertTrue(loopManiaWorld.getNewestCard() instanceof ZombiepitCard);

    }

    @Test
    public void testGoldExperienceItems() throws Exception {
        // Initialize the world.
        init();
        // Check that the player will
        // gain 50 gold, 20 experience,
        // and random items.

        // Record the initial gold, experience
        // and the number of items the character has.
        int beforeGold = character.getGold();
        int beforeExperience = character.getExperience();
        int beforenumItems = loopManiaWorld.getNumItems();

        // Add cards into the card inventory until maximum.
        for (int i = 0; i < loopManiaWorld.getWidth(); i++) {
            loopManiaWorld.loadCard(new VampireCastleCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        }
        loopManiaWorld.loadCard(new ZombiepitCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));

        assertEquals(beforeGold + 50, character.getGold());
        assertEquals(beforeExperience + 20, character.getExperience());
        assertEquals(beforenumItems + 1, loopManiaWorld.getNumItems());
    }

}
