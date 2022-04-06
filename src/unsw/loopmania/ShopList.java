package unsw.loopmania;

import java.util.ArrayList;
import java.util.Arrays;

public class ShopList {
    static private ArrayList<String> shopList = new ArrayList<>(
            Arrays.asList("Armour", "HealthPotion", "Helmet", "Shield", "Staff", "Stake", "Sword"));

    private ShopList() {
    }

    static public ArrayList<String> getShopList() {
        return shopList;
    }

}
