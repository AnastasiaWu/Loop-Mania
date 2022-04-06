package unsw.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.EquippedBoard;
import unsw.loopmania.ShopList;

import java.io.File;
import javafx.scene.image.Image;
/**
 * creates a class named Armour which extends Equipment
 * Body armour, provides defence and halves enemy attack
 * @param defcnt
 * @param buyingPrice
 * @param sellingPrice
 */
public class Armour extends Equipment {

    private static int defcnt = 0;
    private final int buyingPrice = 20;
    private final int sellingPrice = 15;

    public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public Armour() {
        super();
    }
/**
 * override a function
 * @param Obj
 */
    @Override
    public boolean wreckcheck(Object obj) {
        // return true if armour is broken
        // return false if armour is not broken
        if (defcnt >= 5 && equals(obj))
            return true;

        return false;
    }
/**
 * override a function
 * @param Obj
 */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof Armour)
            return true;
        return false;
    }
/**
 * get Buying Price
 */
    @Override
    public int getBuyingPrice() {
        return buyingPrice;
    }
/**
 * getSelling Price
 */
    @Override
    public int getSellingPrice() {
        return sellingPrice;
    }
/**
 * equip
 * @param equippedBoard
 * @param equipment
 */
    @Override
    public void equip(EquippedBoard equippedBoard, Equipment equipment) {
        equippedBoard.setArmour(new Armour());
    }
/**
 * get equipment
 */
    @Override
    public Equipment getEquipment(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new Armour(x, y);
    }
/** 
 * increAttackCNT
 */
    public void increAttackCnt() {
        defcnt++;
    }
/**
 * getImage
 */
    @Override
    public Image getImage() {
        return new Image((new File("src/images/armour.png")).toURI().toString());
    }
/**
 * getSlotImage
 */
    @Override
    public Image getSlotImage() {
        return new Image(new File("src/images/empty_slot.png").toURI().toString());
    }

}
