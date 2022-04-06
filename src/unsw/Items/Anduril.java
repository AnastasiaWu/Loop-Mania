package unsw.Items;

import javafx.beans.property.SimpleIntegerProperty;
import java.io.File;
import javafx.scene.image.Image;

import java.util.Random;
import unsw.Character.Character;
import unsw.loopmania.EquippedBoard;

/**
 * creates a class named anduril
 * A very high damage sword which 
 * causes triple damage against bosses
 */
public class Anduril extends Weapon {
    private int buyingPrice = 999999;
    private int sellingPrice = 0;
    private final int ATTDAMAGE = 5;
    private int attcnt = 0;
/**
 * 
 * @param x
 * @param y
 */
    public Anduril(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public Anduril() {
        super();
    }
/** 
 * get image
 */
    @Override
    public Image getImage() {
        return new Image((new File("src/images/anduril_flame_of_the_west.png")).toURI().toString());
    }
/**
 * override equals
 * @param obj
 */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof Anduril)
            return true;
        return false;
    }
/**
 * get Buying price
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
        Random rand = new Random();
        sellingPrice = rand.nextInt(999);
        return sellingPrice;
    }
/**
 * get damage
 */
    @Override
    public int getDamage() {
        return ATTDAMAGE;
    }
/**
 * incre AttackCnt
 */
    @Override
    public void increAttackCnt() {
       attcnt++;
    }
/**
 * wreck check
 */
    @Override
    public boolean wreckcheck(Object obj) {
        if (obj == null)
            return true;
        if (attcnt >= 5 && equals(obj))
            return true;
        return false;
    }
/**
 * equip
 * @param EquippednBoard
 * @param Equipment
 */
    @Override
    public void equip(EquippedBoard equippedBoard, Equipment equipment) {
        equippedBoard.setWeapon(equipment);
        
    }
/**
 * get Equipment
 * @param x
 * @param y
 */
    @Override
    public Equipment getEquipment(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new Anduril(x, y);
    }
/**
 * Boss ATT
 * @return
 */
    public int BossAtt() {
        return ATTDAMAGE * 3;
    }

}
