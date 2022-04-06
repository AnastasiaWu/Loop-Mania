package unsw.Items;

import javafx.beans.property.SimpleIntegerProperty;
import java.util.Random;
import unsw.Character.Character;
import java.io.File;
import javafx.scene.image.Image;

public class Onering extends Item {
    private int buyingPrice = 9999999;
    private int sellingPrice = 0;

    public Onering(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public Onering() {
        super();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof Onering)
            return true;
        return false;
    }

    @Override
    public int getBuyingPrice() {

        return buyingPrice;
    }

    @Override
    public int getSellingPrice() {
        Random rand = new Random();
        sellingPrice = rand.nextInt(999);
        return sellingPrice;
    }

    public void revive(Character c) {
        if (c.getHealth() <= 0) {
            c.setHealth(100);
        }
    }

    @Override
    public Image getImage() {
        return new Image((new File("src/images/the_one_ring.png")).toURI().toString());
    }
}
