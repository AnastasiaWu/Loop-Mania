package test.CharacterTest;

import org.junit.Test;

import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;

import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;
import unsw.Character.Character;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CharacterTest {

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
        loopManiaWorld.getBuildingsNum();
        PathPosition pathPosition = new PathPosition(1, path);
        character = new Character(pathPosition);
        loopManiaWorld.setCharacter(character);
        this.character = this.loopManiaWorld.getCharacter();
    }

    @Test
    public void healthLimitTest() {
        init();
        Character check = loopManiaWorld.getCharacter();
        assertEquals(check.getHEALTHLIMIT(), 100);
    }

    @Test
    public void goldLimitTest() {
        init();
        Character check = loopManiaWorld.getCharacter();
        assertEquals(check.getGOLDLIMIT(), 10000);
    }

    @Test
    public void XPLimitTest() {
        init();
        Character check = loopManiaWorld.getCharacter();
        assertEquals(check.getEXPLIMIT(), 12000);
    }

    @Test
    public void testGetImage() {
        init();
        Character check = loopManiaWorld.getCharacter();
        new JFXPanel();
        Image image = check.getImage();
        assertTrue(image instanceof Image);
    }

}
