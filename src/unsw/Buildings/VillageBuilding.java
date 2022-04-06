package unsw.Buildings;

import java.io.File;
import javafx.scene.image.Image;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.List;
import org.javatuples.Pair;
import unsw.Character.Character;
import unsw.loopmania.LoopManiaWorld;

/**
 * create a class named VillageBuilding which extends Building 
 * @param path
 * @param iniRound
 */
public class VillageBuilding extends Building {
    private Pair<Integer, Integer> path;
    private int iniRound;

    public VillageBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public VillageBuilding() {
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
        if (obj instanceof VillageBuilding)
            return true;
        return false;
    }
/**
 * set Village Path
 * @param x
 * @param y
 */
    public void setVillagePath(int x, int y) {
        path = new Pair<>(x, y);
    }
/**
 * check if on the path
 * @param p
 * @return
 */
    public boolean checkifonpath(List<Pair<Integer, Integer>> p) {
        for (Pair path : p) {
            if (path.equals(this.path)) {
                return true;
            }
        }
        return false;
    }
/**
 * heal
 * @param c
 */
    public void Heal(Character c) {
        c.increaseHealth(100);
    }
/**
 * check if it is an available position
 */
    @Override
    public boolean checkIfAvailablePosition(LoopManiaWorld loopManiaWorld) {
        return loopManiaWorld.getEntityOnPathWhichIndex(this) == -1 ? false : true;
    }
/**
 * check if disappear
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
 * get image
 */
    @Override
    public Image getImage() {
        return new Image((new File("src/images/village.png")).toURI().toString());
    }
}
