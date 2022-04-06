package test.ItemTest;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import unsw.Items.Anduril;
import unsw.Items.Sword;
import unsw.loopmania.LoopManiaWorld;
import java.util.List;
import unsw.loopmania.PathPosition;
import unsw.Character.Character;
import org.javatuples.Pair;
import unsw.Enemies.ElanMuske;
import unsw.Enemies.Vampire;
import unsw.loopmania.EquippedBoard;

public class AndurilTest {
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
    public void AndurilCreateTest() {
        init();
        Anduril a = new Anduril();
        EquippedBoard eBoard = character.getEquippedBoard();
        eBoard.setWeapon(a);
        assertEquals(a, eBoard.getWeapon());
    }

    @Test
    public void damageTest() {
        init();
        Anduril a = new Anduril();
        EquippedBoard eBoard = character.getEquippedBoard();
        eBoard.setWeapon(a);
        PathPosition pathPosition = new PathPosition(1, path);
        Vampire v = new Vampire(pathPosition);
        loopManiaWorld.addEnemy(v);
        loopManiaWorld.runBattles();

        assertEquals(0, v.getHealth());
    }

    @Test
    public void SpecialDamageTest() {
        init();
        Anduril a = new Anduril();
        EquippedBoard eBoard = character.getEquippedBoard();
        eBoard.setWeapon(a);
        PathPosition pathPosition = new PathPosition(1, path);
        ElanMuske elanMuske = new ElanMuske(pathPosition);
        loopManiaWorld.addEnemy(elanMuske);
        loopManiaWorld.runBattles();

        assertTrue(elanMuske.getHealth() == 0);
    }

    @Test
    public void AndurilWreckTest() {
        init();
        Anduril a = new Anduril();
        EquippedBoard eBoard = character.getEquippedBoard();
        eBoard.setWeapon(a);
        assertTrue(a.wreckcheck(null));
        PathPosition pathPosition = new PathPosition(1, path);
        ElanMuske elanMuske = new ElanMuske(pathPosition);
        loopManiaWorld.addEnemy(elanMuske);
        loopManiaWorld.addEnemy(elanMuske);
        loopManiaWorld.runBattles();

        assertEquals(null, eBoard.getWeapon());
    }

    @Test
    public void AndurilTypeTest() {
        init();
        Anduril anduril = new Anduril(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertTrue(anduril.equals(anduril));
        assertFalse(anduril.equals(null));
        assertTrue(anduril.equals(new Anduril()));
        assertFalse(anduril.equals(new Sword()));
    }

    @Test
    public void buyPriceTest() {
        Anduril a = new Anduril();
        assertEquals(a.getBuyingPrice(), 999999);
    }


    @Test
    public void sellingPriceTest() {
        Anduril a = new Anduril();
        assertTrue(a.getSellingPrice() != 0);
    }

    @Test
    public void testGetImage() {
        Anduril a = new Anduril();
        new JFXPanel();
        Image image = a.getImage();
        assertTrue(image instanceof Image);
    }

    @Test
    public void testEquip() {
        init();
        Anduril a = new Anduril();
        EquippedBoard eBoard = character.getEquippedBoard();
        eBoard.setWeapon(a);
        a.equip(loopManiaWorld.getCharacter().getEquippedBoard(), new Anduril());
        assertTrue(loopManiaWorld.getCharacter().getEquippedBoard().getWeapon() instanceof Anduril);
    }

    @Test
    public void testEquipment() {
        init();
        Anduril a = new Anduril();
        EquippedBoard eBoard = character.getEquippedBoard();
        eBoard.setWeapon(a);
        assertTrue(a.getEquipment(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0)) instanceof Anduril);
    }

    @Test
    public void testBossAtt() {
        Anduril a = new Anduril();
        assertEquals(a.BossAtt(), 15);  
    }

    @Test
    public void getImageTest() {
        init();
        Anduril anduril = new Anduril(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        new JFXPanel();
        anduril.getImage();
    }
}

