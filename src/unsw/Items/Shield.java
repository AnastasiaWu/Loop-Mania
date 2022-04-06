package unsw.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.EquippedBoard;
import unsw.loopmania.ShopList;

import java.io.File;
import javafx.scene.image.Image;

public class Shield extends Equipment {
    private final int buyingPrice = 25;
    private final int sellingPrice = 20;
    private int attcnt = 0;

    /**
     * Shield Constructor
     * 
     * @param x
     * @param y
     */
    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /** Using for comparing */
    public Shield() {
        super();
    }

    /**
     * @param Object
     * 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof Shield)
            return true;
        return false;
    }

    /** get buy price */
    @Override
    public int getBuyingPrice() {
        return buyingPrice;
    }

    /** get price for sell */
    @Override
    public int getSellingPrice() {
        return sellingPrice;
    }

    /** check if need to be destroyed */
    public boolean wreckcheck(Object obj) {
        if (obj == null)
            return true;
        if (attcnt >= 3 && equals(obj))
            return true;
        return false;
    }

    /** increment the attack count */
    public void increAttackCnt() {
        attcnt++;
    }

    /** equipped the shield */
    @Override
    public void equip(EquippedBoard equippedBoard, Equipment equipment) {
        equippedBoard.setShield(equipment);
    }

    /** get the shield */
    @Override
    public Equipment getEquipment(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new Shield(x, y);
    }

    /** get the image */
    @Override
    public Image getImage() {
        return new Image((new File("src/images/shield.png")).toURI().toString());
    }

    /** get the imgae of the empty slot */
    @Override
    public Image getSlotImage() {
        return new Image(new File("src/images/shield_unequipped.png").toURI().toString());
    }
}
