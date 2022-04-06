package unsw.Buildings;

import java.io.File;
import javafx.scene.image.Image;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Pair;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.MovingEntity;
import unsw.Character.Character;

/**
 * During a battle within its shooting radius, enemies will be attacked by the
 * tower
 */
public class TowerBuilding extends Building {
    Pair<SimpleIntegerProperty, SimpleIntegerProperty> position;
    private int iniRound;
    // private Image image = new Image((new
    // File("src/images/tower.png")).toURI().toString());

    public TowerBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        position = new Pair<>(x, y);
    }

    public TowerBuilding() {

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof TowerBuilding)
            return true;
        return false;
    }
/**
 * check if it an available position
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
 * check if entity building radius
 * @param entity
 * @return
 */
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
 * setImage
 */
    @Override
    public Image getImage() {
        return new Image((new File("src/images/tower.png")).toURI().toString());
    }
}
