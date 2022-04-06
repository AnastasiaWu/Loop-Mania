package unsw.Buildings;

import java.io.File;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import unsw.loopmania.LoopManiaWorld;

/**
 * A backend world of Barracks
 */
public class BarracksBuilding extends Building {
    Pair<SimpleIntegerProperty, SimpleIntegerProperty> position;
    private int timesPassedThrough = 0;
    private int iniRound;
    /**
     * Create a BarracksBuilding with coordinates
     */
    public BarracksBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        position = new Pair<>(x, y);
    }

    public BarracksBuilding() {
        super();
    }
    /**
     * override equals function
     * @param obj
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof BarracksBuilding)
            return true;
        return false;
    }
    /**
     * Override function
     * @param loopManiaWorld
     */
    @Override
    public boolean checkIfAvailablePosition(LoopManiaWorld loopManiaWorld) {
        return loopManiaWorld.getEntityOnPathWhichIndex(this) == -1 ? false : true;
    }
    /**
     * Overrigde function
     */
    public void increTimePassedThrough() {
        timesPassedThrough++;
    }
    /**
     * override function
     * @return
     */
    public boolean checkIfDisappear() {
        if (timesPassedThrough >= 2)
            return true;
        return false;
    }
    /**
     * override checkifDisappear function
     */
    @Override
    public boolean checkIfDisappear(int round) {
        return false;
    }
    /**
     * override setRound function
     */
    @Override
    public void setRound(int round) {
        this.iniRound = round;
    }
    /**
     * override getImage function
     */
    @Override
    public Image getImage() {
        return new Image((new File("src/images/barracks.png")).toURI().toString());
    }
}
