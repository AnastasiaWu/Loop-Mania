package unsw.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.EquippedBoard;

import java.io.File;
import javafx.scene.image.Image;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends Weapon {
    private final int buyingPrice = 20;
    private final int sellingPrice = 15;
    private int attcnt = 0;
    private final int ATTDAMAGE = 5;

    /**
     * create a sword
     * 
     * @param x
     * @param y
     */
    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /** create the sword */
    public Sword() {
        super();
    }

    /** check if the sword need to be destroyed */
    @Override
    public boolean wreckcheck(Object obj) {
        // if attacked 3 times or over, wreck
        // else keep the sword
        if (obj == null)
            return true;
        if (attcnt >= 4 && equals(obj))
            return true;
        return false;
    }

    /** increment the attack count */
    public void increAttackCnt() {
        attcnt++;
    }

    /** get the damage */
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
        if (obj instanceof Sword)
            return true;
        return false;
    }

    /** get buying price */
    @Override
    public int getBuyingPrice() {
        return buyingPrice;
    }

    /** get selling price */
    @Override
    public int getSellingPrice() {
        return sellingPrice;
    }

    /** equipt the item */
    @Override
    public void equip(EquippedBoard equippedBoard, Equipment equipment) {
        equippedBoard.setWeapon(equipment);
    }

    /** get the equipemnt */
    @Override
    public Equipment getEquipment(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new Sword(x, y);
    }

    /** get the image */
    @Override
    public Image getImage() {
        return new Image((new File("src/images/basic_sword.png")).toURI().toString());
    }

}
