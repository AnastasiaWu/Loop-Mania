package test.CharacterTest;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import unsw.loopmania.LoopManiaWorld;
import java.util.List;
import unsw.loopmania.PathPosition;
import unsw.Character.Character;
import org.javatuples.Pair;

import unsw.Enemies.Slug;
import unsw.Character.Soldier;

public class SoldierTests {
    private List<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();
    private LoopManiaWorld loopManiaWorld;
    private Character character;

    private void init() {
        path.add(new Pair<Integer, Integer>(0, 0));
        path.add(new Pair<Integer, Integer>(1, 0));
        path.add(new Pair<Integer, Integer>(2, 0));
        path.add(new Pair<Integer, Integer>(2, 1));
        path.add(new Pair<Integer, Integer>(2, 2));
        path.add(new Pair<Integer, Integer>(1, 2));
        path.add(new Pair<Integer, Integer>(0, 2));
        path.add(new Pair<Integer, Integer>(0, 1));
        loopManiaWorld = new LoopManiaWorld(3, 3, path);
        PathPosition pathPosition = new PathPosition(0, path);
        character = new Character(pathPosition);
        loopManiaWorld.setCharacter(character);
        loopManiaWorld.setHeroCastle(0, 0);
    }

    @Test
    public void soldierBornAttackTest() {
        init();
        PathPosition slugPosition = new PathPosition(0, path);
        Slug slug = new Slug(slugPosition);
        loopManiaWorld.addEnemy(slug);
        loopManiaWorld.addSoldiers();
        loopManiaWorld.runBattles();
        assertEquals(6 - 5 - 2 - 5, slug.getHealth());
    }

    @Test
    public void soldierDeadTest() {
        init();
        PathPosition slugPosition = new PathPosition(0, path);
        Slug slug1 = new Slug(slugPosition);
        loopManiaWorld.addEnemy(slug1);
        loopManiaWorld.addSoldiers();
        loopManiaWorld.runBattles();
        Slug slug2 = new Slug(slugPosition);
        loopManiaWorld.addEnemy(slug2);
        loopManiaWorld.runBattles();
        assertEquals(0, loopManiaWorld.getSoldiers().size());
    }

    @Test
    public void soldierConstructorTest() {
        Soldier soldier = new Soldier(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertTrue(soldier != null);
    }

    @Test
    public void testGetImage() {
        Soldier soldier = new Soldier();
        new JFXPanel();
        Image image = soldier.getImage();
        assertTrue(image instanceof Image);
    }

}
