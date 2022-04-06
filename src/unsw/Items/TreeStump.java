package unsw.Items;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import java.io.File;

import java.util.Random;
import unsw.Character.Character;
import unsw.loopmania.EquippedBoard;

public class TreeStump extends Equipment {
    private int buyingPrice = 9999999;
    private int sellingPrice = 0;
    private int attcnt = 0;

    /** create the tree stump */
    public TreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /** create the tree stump */
    public TreeStump() {
        super();
    }

    /** equip the tree stump */
    @Override
    public void equip(EquippedBoard equippedBoard, Equipment equipment) {
        equippedBoard.setShield(equipment);

    }

    /** get the equipment */
    @Override
    public Equipment getEquipment(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new TreeStump(x, y);
    }

    /** check if the item need to be destroyed */
    @Override
    public boolean wreckcheck(Object obj) {
        if (obj == null)
            return true;
        if (attcnt >= 8 && equals(obj))
            return true;
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof TreeStump)
            return true;
        return false;
    }

    /** get the buying price */
    @Override
    public int getBuyingPrice() {

        return buyingPrice;
    }

    /** increment the attack count */
    public void increAttackCnt() {
        attcnt++;
    }

    /** get the selling price */
    @Override
    public int getSellingPrice() {
        Random rand = new Random();
        sellingPrice = rand.nextInt(999);
        return sellingPrice;
    }

    /**
     * define the boss
     * 
     * @param damage
     * @return
     */
    public int BossDef(int damage) {
        return 0;
    }

    /** get the slot image */
    @Override
    public Image getSlotImage() {
        return new Image(new File("src/images/shield_unequipped.png").toURI().toString());
    }

    /** get the image */
    @Override
    public Image getImage() {
        return new Image((new File("src/images/tree_stump.png")).toURI().toString());
    }

}
