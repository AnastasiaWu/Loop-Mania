package unsw.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.EquippedBoard;
import java.io.File;
import javafx.scene.image.Image;
/**
 * create a class named equipment that extends Item
 */
public abstract class Equipment extends Item {
    public Equipment(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public Equipment() {
        super();
    }

    public abstract void equip(EquippedBoard equippedBoard, Equipment equipment);

    public abstract boolean wreckcheck(Object obj);

    public abstract Equipment getEquipment(SimpleIntegerProperty x, SimpleIntegerProperty y);

    public abstract Image getSlotImage();
}
