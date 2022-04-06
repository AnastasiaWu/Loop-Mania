package unsw.Items;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;

public abstract class Weapon extends Equipment {
    public Weapon(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public Weapon() {
        super();
    }

    /**
     * get the damage of the weapon
     * 
     * @return the damage
     */
    public abstract int getDamage();

    /** check if the weapon need to be destroyed */
    public abstract boolean wreckcheck(Object obj);

    /** increment the attack count */
    public abstract void increAttackCnt();

    @Override
    public Image getSlotImage() {
        return new Image(new File("src/images/sword_unequipped.png").toURI().toString());
    }
}
