package unsw.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.EquippedBoard;
import unsw.loopmania.ShopList;

import java.io.File;
import javafx.scene.image.Image;

public class Stake extends Weapon {

    private final int buyingPrice = 15;
    private final int sellingPrice = 10;
    private int attcnt = 0;
    private final int ATTDAMAGE = 4;

    /**
     * create a stake
     * 
     * @param x
     * @param y
     */
    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /** for comparing type */
    public Stake() {
        super();
    }

    /** check if the stake need to be destroyed */
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
    @Override
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
        if (obj instanceof Stake)
            return true;
        return false;
    }

    /** get the price of the item */
    @Override
    public int getBuyingPrice() {
        return buyingPrice;
    }

    /** get the selling price */
    @Override
    public int getSellingPrice() {
        return sellingPrice;
    }

    /** equip the item */
    @Override
    public void equip(EquippedBoard equippedBoard, Equipment equipment) {
        equippedBoard.setWeapon(equipment);
    }

    /** get equipment */
    @Override
    public Equipment getEquipment(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new Stake(x, y);
    }

    /** get image */
    @Override
    public Image getImage() {
        return new Image((new File("src/images/stake.png")).toURI().toString());
    }
}
