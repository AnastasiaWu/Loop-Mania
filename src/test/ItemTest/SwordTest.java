package test.ItemTest;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import unsw.Items.Staff;
import unsw.Items.Sword;
import unsw.loopmania.LoopManiaWorld;
import java.util.List;
import unsw.loopmania.PathPosition;
import unsw.Character.Character;
import org.javatuples.Pair;

import unsw.Enemies.Slug;
import unsw.Enemies.Vampire;
import unsw.loopmania.EquippedBoard;
import unsw.Enemies.Slug;

public class SwordTest {
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
    public void swordCreateTest() {
        init();
        Sword s = new Sword();
        EquippedBoard eBoard = character.getEquippedBoard();
        eBoard.setWeapon(s);
        assertEquals(s, eBoard.getWeapon());
    }

    @Test
    public void swordDamageTest() {
        init();
        Sword s = new Sword();
        EquippedBoard eBoard = character.getEquippedBoard();
        eBoard.setWeapon(s);
        PathPosition pathPosition = new PathPosition(0, path);
        Slug slug = new Slug(pathPosition);
        loopManiaWorld.addEnemy(slug);
        loopManiaWorld.runBattles();

        assertTrue(slug.getHealth() < 0);
    }

    @Test
    public void swordWreckTest() {
        init();
        Sword s = new Sword();
        EquippedBoard eBoard = character.getEquippedBoard();

        eBoard.setWeapon(s);
        assertTrue(s.wreckcheck(null));

        PathPosition pathPosition = new PathPosition(1, path);
        Slug slug1 = new Slug(pathPosition);
        loopManiaWorld.addEnemy(slug1);
        loopManiaWorld.runBattles();
        Slug slug2 = new Slug(pathPosition);
        loopManiaWorld.addEnemy(slug2);
        loopManiaWorld.runBattles();

        assertEquals(null, eBoard.getWeapon());

    }

    @Test
    public void swordPurchaseTest() {
        init();
        // for (int i = 0; i < 8; i++)
        //     loopManiaWorld.runTickMoves();
        loopManiaWorld.setRound(1);
        Sword s = new Sword(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        character.setGold(100);
        loopManiaWorld.buyItems(s);
        assertEquals(80, character.getGold());
        assertEquals(1, loopManiaWorld.getNumItems());
        loopManiaWorld.sellItems(new Sword());
        assertEquals(80 + 15, character.getGold());
    }

    @Test
    public void swordTypeCheck() {
        init();
        Sword s = new Sword(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertTrue(s.equals(s));
        assertFalse(s.equals(null));
        assertTrue(s.equals(new Sword()));
        assertFalse(s.equals(new Staff()));
    }

    @Test
    public void testGetImage() {
        Sword sword = new Sword(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        new JFXPanel();
        Image image = sword.getImage();
        assertTrue(image instanceof Image);
    }

    @Test
    public void testEquip() {
        init();
        Sword staff = (Sword)loopManiaWorld.getCharacter().getEquippedBoard().getWeapon();
        staff.equip(loopManiaWorld.getCharacter().getEquippedBoard(), new Staff());
        assertTrue(loopManiaWorld.getCharacter().getEquippedBoard().getWeapon() instanceof Staff);
    }

    @Test
    public void testEquipment() {
        init();
        Sword staff = (Sword)loopManiaWorld.getCharacter().getEquippedBoard().getWeapon();
        assertTrue(staff.getEquipment(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0)) instanceof Sword);
    }
}
