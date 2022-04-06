package unsw.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;
/**
 * create abstract class Item extends StaticEntity
 */
public abstract class Item extends StaticEntity {
    public Item(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public Item() {
        super();
    }

    public abstract int getBuyingPrice();

    public abstract int getSellingPrice();

    @Override
    public abstract boolean equals(Object obj);

}
