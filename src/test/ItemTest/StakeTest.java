package test.ItemTest;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import unsw.Items.Stake;
import unsw.Items.Sword;
import unsw.loopmania.LoopManiaWorld;
import java.util.List;
import unsw.loopmania.PathPosition;
import unsw.Character.Character;
import org.javatuples.Pair;
import unsw.Enemies.Slug;
import unsw.loopmania.EquippedBoard;

public class StakeTest {
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
    public void StakeCreateTest() {
        Stake st = new Stake();
        EquippedBoard eBoard = new EquippedBoard(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        eBoard.setWeapon(st);
        assertEquals(st, eBoard.getWeapon());
    }

    @Test
    public void StakeDamageTest() {
        init();
        Stake st = new Stake();
        PathPosition pathPosition = new PathPosition(1, path);
        Slug slug = new Slug(pathPosition);
        loopManiaWorld.addEnemy(slug);
        loopManiaWorld.runBattles();
        assertEquals(98, character.getHealth());

    }

    @Test
    public void StakeWreckTest() {
        init();
        character.getEquippedBoard().setWeapon(new Stake());
        Stake stake = (Stake)character.getEquippedBoard().getWeapon();
        assertTrue(stake.wreckcheck(null));
        loopManiaWorld.addEnemy(new Slug(new PathPosition(1, path)));
        loopManiaWorld.runBattles();
        loopManiaWorld.addEnemy(new Slug(new PathPosition(1, path)));
        loopManiaWorld.runBattles();
        assertEquals(null, character.getEquippedBoard().getWeapon());
    }

    @Test
    public void stakeTypeCheck() {
        Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        assertTrue(stake.equals(stake));
        assertFalse(stake.equals(null));
        assertTrue(stake.equals(new Stake()));
        assertFalse(stake.equals(new Sword()));
    }

    @Test
    public void stakeBuyingAndSellingPriceCheck() {
        init();
        loopManiaWorld.setRound(1);
        character.increaseGold(100);
        loopManiaWorld.buyItems(new Stake());
        assertEquals(100 - 15, character.getGold());
        loopManiaWorld.sellItems(new Stake());
        assertEquals(100 - 15 + 10, character.getGold());
    }

    @Test
    public void testGetImage() {
        Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        new JFXPanel();
        Image image = stake.getImage();
        assertTrue(image instanceof Image);
    }

    @Test
    public void testEquip() {
        init();
        character.getEquippedBoard().setWeapon(new Stake());
        Stake stake = (Stake)loopManiaWorld.getCharacter().getEquippedBoard().getWeapon();
        stake.equip(loopManiaWorld.getCharacter().getEquippedBoard(), new Sword());
        assertTrue(loopManiaWorld.getCharacter().getEquippedBoard().getWeapon() instanceof Sword);
    }

    @Test
    public void testEquipment() {
        init();
        character.getEquippedBoard().setWeapon(new Stake());
        Stake stake = (Stake)loopManiaWorld.getCharacter().getEquippedBoard().getWeapon();
        assertTrue(stake.getEquipment(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0)) instanceof Stake);
    }
}
