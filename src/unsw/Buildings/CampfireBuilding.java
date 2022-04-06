package unsw.Buildings;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.MovingEntity;

import java.io.File;
import javafx.scene.image.Image;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Pair;
/**
 * creates a class named CampfireBuilding which extends building
 * @param position
 * @param iniRound
 */
public class CampfireBuilding extends Building {
    Pair<SimpleIntegerProperty, SimpleIntegerProperty> position;
    private int iniRound;
    // private Image image = new Image((new
    // File("src/images/campfire.png")).toURI().toString());
/**
 * 
 * @param x
 * @param y
 */
    public CampfireBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

    }

    public CampfireBuilding() {
        super();
    }
/**
 * override method equals
 */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof CampfireBuilding)
            return true;
        return false;
    }
/**
 * check if it is an availablePosition
 * @param loopManiaWorld
 */
    @Override
    public boolean checkIfAvailablePosition(LoopManiaWorld loopManiaWorld) {
        return loopManiaWorld.getEntityOnPathWhichIndex(this) == -1 ? true : false;
    }

    public boolean checkIfEntityInBuildingRadius(MovingEntity entity) {
        if (Math.pow(entity.getX() - this.getX(), 2) + Math.pow(entity.getY() - this.getY(), 2) <= 4) {
            return true;
        }
        return false;
    }
/**
 * check if disappear
 */
    @Override
    public boolean checkIfDisappear(int round) {
        return round - iniRound >= 1 ? true : false;
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
        return new Image((new File("src/images/campfire.png")).toURI().toString());
    }
}
