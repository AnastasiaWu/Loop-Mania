package unsw.Buildings;

import java.io.File;
import javafx.scene.image.Image;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;

/**
 * Create a class named TrapBuilding which extends Building
 * @param iniRound
 */
public class TrapBuilding extends Building {
    private int iniRound;

    public TrapBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public TrapBuilding() {
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
        if (obj instanceof TrapBuilding)
            return true;
        return false;
    }
/**
 * check if disappear
 */
    @Override
    public boolean checkIfDisappear(int round) {
        return round - iniRound >= 2 ? true : false;
    }
/**
 * set round
 */
    @Override
    public void setRound(int round) {
        this.iniRound = round;
    }
/**
 * check if it is an available position
 * @param loopMnaniaWorld
 */
    @Override
    public boolean checkIfAvailablePosition(LoopManiaWorld loopManiaWorld) {
        // Only on path tiles
        return loopManiaWorld.getEntityOnPathWhichIndex(this) != -1 ? true : false;
    }

    @Override
    public Image getImage() {
        return new Image((new File("src/images/trap.png")).toURI().toString());
    }
}
