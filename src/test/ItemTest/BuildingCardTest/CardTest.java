package test.ItemTest.BuildingCardTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.Character.Character;
import unsw.Enemies.Slug;

public class CardTest {

    private List<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();
    private LoopManiaWorld loopManiaWorld;
    private Character character;
    private Slug slug;

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
        PathPosition slugPosition = new PathPosition(2, path);
        this.slug = new Slug(slugPosition);
        loopManiaWorld.addEnemy(slug);
        this.character = loopManiaWorld.getCharacter();
    }

    @Test
    public void testGet1NewCard() {
        // Initialize the world.
        init();
        // Check that charact will get a new card
        // after killing enemies.
        int sizeBefore = loopManiaWorld.getCardEntitiesSize();
        loopManiaWorld.runBattles();
        assertEquals(loopManiaWorld.getCardEntitiesSize(), sizeBefore + 1);
    }
}
