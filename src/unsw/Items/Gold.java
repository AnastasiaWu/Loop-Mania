package unsw.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;
import java.io.File;
import javafx.scene.image.Image;
/**
 * creates a class named gold which extends staticEntity
 * Money, used to buy things
 */
public class Gold extends StaticEntity {
    public int gold;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof Gold)
            return true;
        return false;
    }
/**
 * 
 * @param x
 * @param y
 */
    public Gold(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public Gold() {
        super();
    }
/**
 * get gold
 * @return
 */
    public int getGold() {
        return gold;
    }
/** set gold */
    public void setGold(int gold) {
        this.gold = gold;
    }
/**
 * get image
 */
    public Image getImage() {
        return new Image((new File("src/images/gold_pile.png")).toURI().toString());
    }
}
