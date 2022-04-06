package unsw.Buildings;

import org.javatuples.Pair;

import java.io.File;
import javafx.scene.image.Image;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.Enemies.Zombie;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
/**
 * create a class which extends Building 
 * @param iniRound
 * @param thisRoundSpawned
 */
public class ZombiePitBuilding extends Building {
    private int iniRound;

    private Pair<Integer, Boolean> thisRoundSpawned = new Pair<Integer, Boolean>(0, false);
    // private Image image = new Image((new
    // File("src/images/zombie_pit.png")).toURI().toString());

    public ZombiePitBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public ZombiePitBuilding() {
        super();
    }
/**
 * override equal
 */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof ZombiePitBuilding)
            return true;
        return false;
    }
/**
 * check if available position
 */
    @Override
    public boolean checkIfAvailablePosition(LoopManiaWorld loopManiaWorld) {
        // Only on non-path tiles adjacent to the path
        if (loopManiaWorld.getEntityOnPathWhichIndex(this) == -1
                && loopManiaWorld.checkEntityAdjacentPathWhichIndex(this))
            return true;
        return false;
    }
/**
 * check if is disappear
 */
    @Override
    public boolean checkIfDisappear(int round) {
        if (round - iniRound >= 2)
            return true;
        return false;
    }
/**
 * set round
 */
    @Override
    public void setRound(int round) {
        this.iniRound = round;
    }
/**
 * spawn
 * @param position
 * @param lpmw
 * @return
 */
    public Zombie spawn(PathPosition position, LoopManiaWorld lpmw) {
        if (lpmw.getRound() >= 1 && !thisRoundSpawned.getValue1()) {
            Zombie zombie = new Zombie(position);
            thisRoundSpawned = new Pair<Integer, Boolean>(lpmw.getRound(), true);
            return zombie;
        }
        return null;
    }
/**
 * get image
 */
    @Override
    public Image getImage() {
        return new Image((new File("src/images/zombie_pit.png")).toURI().toString());
    }
}
