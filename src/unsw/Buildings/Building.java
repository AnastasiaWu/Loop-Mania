package unsw.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.StaticEntity;
/**
 * create a class named building which extends StaticEntity
 */
public abstract class Building extends StaticEntity {
    public Building(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public Building() {
    }

    @Override
    public abstract boolean equals(Object obj);

    public abstract boolean checkIfAvailablePosition(LoopManiaWorld loopManiaWorld);

    public abstract boolean checkIfDisappear(int round);

    public abstract void setRound(int round);

}
