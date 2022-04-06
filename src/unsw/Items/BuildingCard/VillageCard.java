package unsw.Items.BuildingCard;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.Buildings.Building;
import unsw.Buildings.VillageBuilding;
import java.io.File;
import javafx.scene.image.Image;
/**
 * creates a card named VillageCard which extends card
 */
public class VillageCard extends Card {
/**
 * 
 * @param x
 * @param y
 */
    public VillageCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public Building getBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new VillageBuilding(x, y);
    }

    @Override
    public Image getImage() {
        return new Image((new File("src/images/village_card.png")).toURI().toString());
    }

}
