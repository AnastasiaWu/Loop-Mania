package test.ItemTest;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import unsw.Items.Armour;
import unsw.Items.Sword;
import unsw.loopmania.LoopManiaWorld;
import java.util.List;
import unsw.loopmania.PathPosition;
import unsw.Character.Character;
import org.javatuples.Pair;
import unsw.loopmania.EquippedBoard;
import unsw.Enemies.Slug;

public class ArmourTest {
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
    public void ArmourCreateTest() {
        init();
        Armour a = new Armour();
        EquippedBoard eBoard = character.getEquippedBoard();
        eBoard.setArmour(a);
        assertEquals(a, eBoard.getArmour());

    }

    @Test
    public void ArmourDamageTest() {
        init();
        Armour a = new Armour();
        EquippedBoard eBoard = character.getEquippedBoard();
        eBoard.setArmour(a);
        PathPosition pathPosition = new PathPosition(1, path);
        Slug slug = new Slug(pathPosition);
        loopManiaWorld.addEnemy(slug);
        loopManiaWorld.runBattles();
        assertTrue(character.getHealth() < 100);
    }

    @Test
    public void ArmourWreckTest() {
        init();
        PathPosition pathPosition = new PathPosition(1, path);
        EquippedBoard eBoard = new EquippedBoard(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        Slug slug = new Slug(pathPosition);
        loopManiaWorld.addEnemy(slug);
        loopManiaWorld.runBattles();
        loopManiaWorld.runBattles();
        loopManiaWorld.runBattles();
        loopManiaWorld.runBattles();
        loopManiaWorld.runBattles();
        assertEquals(null, eBoard.getArmour());
        loopManiaWorld.runBattles();
        assertEquals(null, eBoard.getArmour());

    }

    @Test
    public void ArmourPurchaseTest() {
        init();
        loopManiaWorld.setRound(1);
        Armour a = new Armour();
        character.increaseGold(100);
        loopManiaWorld.buyItems(a);
        assertEquals(80, character.getGold());
        assertEquals(1, loopManiaWorld.getNumItems());
    }

    @Test
    public void ArmourSellingTest() throws Exception {
        init();
        loopManiaWorld.setRound(1);
        loopManiaWorld.addUnequipped(new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        loopManiaWorld.sellItems(new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        assertEquals(15, character.getGold());
        assertEquals(0, loopManiaWorld.getNumItems());
    }

    @Test
    public void typeCheck() {
        Armour gold = new Armour(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        assertTrue(gold.equals(gold));
        assertFalse(gold.equals(null));
        assertTrue(gold.equals(new Armour()));
        assertFalse(gold.equals(new Sword()));
    }

    @Test
    public void testGetImage() {
        Armour armour = new Armour(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        new JFXPanel();
        Image image = armour.getImage();
        assertTrue(image instanceof Image);
    }

    @Test
    public void testGetSlotImage() {
        Armour armour = new Armour(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        new JFXPanel();
        Image image = armour.getSlotImage();
        assertTrue(image instanceof Image);
    }

    @Test
    public void testEquip() {
        init();
        Armour a = new Armour();
        EquippedBoard eBoard = character.getEquippedBoard();
        eBoard.setArmour(a);
        Armour armour = (Armour)loopManiaWorld.getCharacter().getEquippedBoard().getArmour();
        armour.equip(loopManiaWorld.getCharacter().getEquippedBoard(), new Armour());
        assertTrue(loopManiaWorld.getCharacter().getEquippedBoard().getArmour() instanceof Armour);
    }

    @Test
    public void testEquipment() {
        init();
        Armour a = new Armour();
        EquippedBoard eBoard = character.getEquippedBoard();
        eBoard.setArmour(a);
        Armour armour = (Armour)loopManiaWorld.getCharacter().getEquippedBoard().getArmour();
        assertTrue(armour.getEquipment(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0)) instanceof Armour);
    }

}
