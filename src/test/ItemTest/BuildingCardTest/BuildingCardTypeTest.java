package test.ItemTest.BuildingCardTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.Character.Character;
import unsw.Items.BuildingCard.BarracksCard;
import unsw.Items.BuildingCard.CampfireCard;
import unsw.Items.BuildingCard.TowerCard;
import unsw.Items.BuildingCard.TrapCard;
import unsw.Items.BuildingCard.VampireCastleCard;
import unsw.Items.BuildingCard.ZombiepitCard;
import unsw.Items.BuildingCard.VillageCard;

public class BuildingCardTypeTest {

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
        character = new Character(characterPosition);
        loopManiaWorld.setCharacter(character);
        this.character = loopManiaWorld.getCharacter();
    }

    @Test
    public void getImageTestBarracksCard() {
        init();
        BarracksCard card = new BarracksCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        new JFXPanel();
        card.getImage();
    }

    @Test
    public void getImageTestCampfireCard() {
        init();
        CampfireCard card = new CampfireCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        new JFXPanel();
        card.getImage();
    }

    @Test
    public void getImageTestTowerCard() {
        init();
        TowerCard card = new TowerCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        new JFXPanel();
        card.getImage();
    }

    @Test
    public void getImageTestTrapCard() {
        init();
        TrapCard card = new TrapCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        new JFXPanel();
        card.getImage();
    }

    @Test
    public void getImageTestVampireCastleCard() {
        init();
        VampireCastleCard card = new VampireCastleCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        new JFXPanel();
        card.getImage();
    }

    @Test
    public void getImageTestVillageCard() {
        init();
        VillageCard card = new VillageCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        new JFXPanel();
        card.getImage();
    }

    @Test
    public void getImageTestZombiepitCard() {
        init();
        ZombiepitCard card = new ZombiepitCard(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        new JFXPanel();
        card.getImage();
    }
}
