package unsw.Buildings;

import java.io.File;
import javafx.scene.image.Image;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.Enemies.Vampire;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;

import org.javatuples.Pair;

/**
 * a basic form of building in the world
 * @param thisRoundSpawned
 * @param iniRound
 */
public class VampireCastleBuilding extends Building {
    private int iniRound;

    private Pair<Integer, Boolean> thisRoundSpawned = new Pair<Integer, Boolean>(0, false);

    // TODO = add more types of building, and make sure buildings have effects on
    // entities as required by the spec

    private Pair<Integer, Integer> path;
    // private Image image = new Image(
    // (new
    // File("src/images/vampire_castle_building_purple_background.png")).toURI().toString());

    public VampireCastleBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public VampireCastleBuilding() {
        super();
    }
/**
 * override equal method
 */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof VampireCastleBuilding)
            return true;
        return false;
    }
/**
 * set vcastlepath
 * @param x
 * @param y
 */
    public void setVCastlePath(int x, int y) {
        path = new Pair<>(x, y);
    }
/**
 * spawn
 * @param position
 * @param lpmw
 * @return
 */
    public Vampire spawn(PathPosition position, LoopManiaWorld lpmw) {
        if (lpmw.getRound() >= 5 && !thisRoundSpawned.getValue1()) {
            Vampire v = new Vampire(position);
            thisRoundSpawned = new Pair<Integer, Boolean>(lpmw.getRound(), true);
            return v;
        }
        return null;
    }
/**
 * check if it an available Position
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
 * check if it is disappear
 */
    @Override
    public boolean checkIfDisappear(int round) {
        if (round - iniRound >= 7)
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
 * get image
 */
    @Override
    public Image getImage() {
        return new Image((new File("src/images/vampire_castle_building_purple_background.png")).toURI().toString());
    }
}
