package unsw.Items;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;

import java.util.Random;
import unsw.Character.Character;
import java.io.File;
/**
 * create a class named DoggieCoin
 * A revolutionary asset type, 
 * which randomly fluctuates in sellable price to an extraordinary extent. 
 * Can sell at shop
 */
public class DoggieCoin extends Item {
   
    private int sellingPrice = 0;
    private int buyingPrice = 99999999;
    private static boolean ElanifExist = false;
    
/**
 * set IF Elan is exist
 * @param elanifExist
 */
    public static void setElanifExist(boolean elanifExist) {
        ElanifExist = elanifExist;
    }
/**
 * get buying price
 */
    public int getBuyingPrice() {
        return buyingPrice;
    }
/**
 * set Buying price
 * @param buyingPrice
 */
    public void setBuyingPrice(int buyingPrice) {
        this.buyingPrice = buyingPrice;
    }
/**
 * get selling price
 */
    public int getSellingPrice() {
        Random rand = new Random();
        sellingPrice = rand.nextInt(999);
        return sellingPrice;
    }
/**
 * set selling price
 * @param sellingPrice
 */
    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
/**
 * 
 * @param x
 * @param y
 */
    public DoggieCoin(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public DoggieCoin() {
        super();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof DoggieCoin)
            return true;
        return false;
    }
/**
 * get image
 */
    @Override
    public Image getImage() {
        // TODO Auto-generated method stub
        return new Image((new File("src/images/doggiecoin.png")).toURI().toString());
    }
  

   

 

}
