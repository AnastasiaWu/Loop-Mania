package unsw.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.EquippedBoard;
import unsw.loopmania.ShopList;

import java.io.File;
import javafx.scene.image.Image;
/**
 * create a class named helmet
 * Defends against enemy attacks, enemy attacks are reduced by a scalar value. 
 * The damage inflicted by the Character against enemies is reduced 
 * (since it is harder to see)
 * @param buyingPrice
 * @param sellingPrice
 * @param attcnt
 */
public class Helmet extends Equipment {
    private final int buyingPrice = 30;
    private final int sellingPrice = 25;
    private int attcnt;
/**
 * 
 * @param x
 * @param y
 */
    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public Helmet() {
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
        if (obj instanceof Helmet)
            return true;
        return false;
    }
/**
 * get buying price
 */
    @Override
    public int getBuyingPrice() {
        return buyingPrice;
    }
/**
 * get selling price
 */
    @Override
    public int getSellingPrice() {
        return sellingPrice;
    }
/**
 * wreckheck 
 * @param obj
 */
    // @Override
    public boolean wreckcheck(Object obj) {
        // if attacked 3 times or over, wreck
        // else keep the sword
        if (attcnt >= 3 && equals(obj))
            return true;
        return false;
    }
/**
 * incre Attack Cnt
 */
    // @Override
    public void increAttackCnt() {
        attcnt++;
    }
/**
 * equip
 * @param equippedBoard
 * @param equipment
 */
    @Override
    public void equip(EquippedBoard equippedBoard, Equipment equipment) {
        equippedBoard.setHelmet(equipment);
    }
/**
 * get equipment
 */
    @Override
    public Equipment getEquipment(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new Helmet(x, y);
    }
/**
 * get Image
 */
    @Override
    public Image getImage() {
        return new Image((new File("src/images/helmet.png")).toURI().toString());
    }

    @Override
    public Image getSlotImage() {
        return new Image(new File("src/images/helmet_slot.png").toURI().toString());
    }
}
