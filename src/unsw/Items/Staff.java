package unsw.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.EquippedBoard;
import unsw.loopmania.ShopList;

import java.io.File;
import javafx.scene.image.Image;

public class Staff extends Weapon {
    private final int buyingPrice = 10;
    private final int sellingPrice = 5;
    private int attcnt = 0;
    private final int ATTDAMAGE = 3;

    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public Staff() {
        super();
    }

    @Override
    public boolean wreckcheck(Object obj) {
        if (attcnt >= 3 && equals(obj))
            return true;
        return false;
    }

    public void increAttackCnt() {
        attcnt++;
    }

    @Override
    public int getDamage() {
        return ATTDAMAGE;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof Staff)
            return true;
        return false;
    }

    @Override
    public int getBuyingPrice() {
        return buyingPrice;
    }

    @Override
    public int getSellingPrice() {
        return sellingPrice;
    }

    @Override
    public void equip(EquippedBoard equippedBoard, Equipment equipment) {
        equippedBoard.setWeapon(equipment);
    }

    @Override
    public Equipment getEquipment(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new Staff(x, y);
    }

    @Override
    public Image getImage() {
        return new Image((new File("src/images/staff.png")).toURI().toString());
    }
}
