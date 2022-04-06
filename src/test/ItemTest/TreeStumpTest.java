package test.ItemTest;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import unsw.Items.Sword;
import unsw.Items.TreeStump;
import unsw.loopmania.LoopManiaWorld;
import java.util.List;
import unsw.loopmania.PathPosition;
import unsw.Character.Character;
import org.javatuples.Pair;
import unsw.Enemies.Vampire;
import unsw.loopmania.EquippedBoard;
import unsw.Enemies.ElanMuske;
import unsw.Items.Anduril;
import unsw.Items.Shield;

public class TreeStumpTest {
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

    // @Test
    // public void wreckTest() {
    //     init();
    //     loopManiaWorld.runBattles();
    //     PathPosition pathPosition = new PathPosition(1, path);
    //     ElanMuske elanMuske = new ElanMuske(pathPosition);
    //     TreeStump treeStump = new TreeStump();
    //     EquippedBoard equippedBoard = character.getEquippedBoard();
    //     equippedBoard.setShield(treeStump);
    //     loopManiaWorld.addEnemy(elanMuske);
    //     loopManiaWorld.runBattles();
    //     loopManiaWorld.addEnemy(elanMuske);
    //     loopManiaWorld.runBattles();
        
    //     assertEquals(null, equippedBoard.getShield());
    // }

    
    @Test
    public void againstNormalAtttackTest() {
        init();
        loopManiaWorld.runBattles();
        PathPosition pathPosition = new PathPosition(1, path);
        ElanMuske elanMuske = new ElanMuske(pathPosition);
        TreeStump treeStump = new TreeStump();
        EquippedBoard equippedBoard = character.getEquippedBoard();
        equippedBoard.setShield(treeStump);
        Anduril anduril = new Anduril();
        equippedBoard.setWeapon(anduril);
        loopManiaWorld.addEnemy(elanMuske);
        loopManiaWorld.runBattles();
        assertEquals(100, character.getHealth());
    }

    @Test
    public void TreeStumpTypeTest() {
        init();
        TreeStump treeStump = new TreeStump(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertTrue(treeStump.equals(treeStump));
        assertFalse(treeStump.equals(null));
        assertTrue(treeStump.equals(new TreeStump()));
        assertFalse(treeStump.equals(new Sword()));
    }

    @Test
    public void testGetImage() {
        TreeStump treeStump = new TreeStump();
        new JFXPanel();
        Image image = treeStump.getImage();
        assertTrue(image instanceof Image);
    }

    @Test
    public void testGetSlotImage() {
        TreeStump treeStump = new TreeStump();
        new JFXPanel();
        Image image = treeStump.getSlotImage();
        assertTrue(image instanceof Image);
    }

    @Test
    public void testEquip() {
        init();
        TreeStump treeStump = new TreeStump();
        EquippedBoard equippedBoard = character.getEquippedBoard();
        equippedBoard.setShield(treeStump);
        treeStump.equip(loopManiaWorld.getCharacter().getEquippedBoard(), new Shield());
        assertTrue(loopManiaWorld.getCharacter().getEquippedBoard().getShield() instanceof Shield);
    }

    @Test
    public void testEquipment() {
        init();
        TreeStump treeStump = new TreeStump();
        treeStump.increAttackCnt();
        EquippedBoard equippedBoard = character.getEquippedBoard();
        equippedBoard.setShield(treeStump);
        assertTrue(treeStump.getEquipment(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0)) instanceof TreeStump);
    }

    @Test
    public void buyPriceTest() {
        TreeStump treeStump = new TreeStump();
        assertEquals(treeStump.getBuyingPrice(), 9999999);
    }


    @Test
    public void sellingPriceTest() {
        TreeStump treeStump = new TreeStump();
        assertTrue(treeStump.getSellingPrice() != 0);
    }

    @Test
    public void bossDefTest() {
        TreeStump treeStump = new TreeStump();
        assertEquals(treeStump.BossDef(0), 0);
    }


}
