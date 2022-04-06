package unsw.Items;

import javafx.beans.property.SimpleIntegerProperty;
import java.io.File;
import javafx.scene.image.Image;
import unsw.loopmania.ShopList;
/**
 * creates a class named healthPotion which extends Item
 * Refills Character health
 */
public class HealthPotion extends Item {

    private int buyingPrice = 10;
    private int sellingPrice = 5;
/**
 * 
 * @param x
 * @param y
 */
    public HealthPotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        // TODO Auto-generated constructor stub
    }

    public HealthPotion() {
        super();
    }
/**
 * get buying Price
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
 * iverride method equals
 */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof HealthPotion)
            return true;
        return false;
    }
/** get image */
    @Override
    public Image getImage() {
        return new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString());
    }
}
