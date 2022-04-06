package unsw.Items.BuildingCard;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.Buildings.*;
import java.io.File;
import javafx.scene.image.Image;
/**
 * create a class named CampfireCard
 */
public class CampfireCard extends Card {
    public CampfireCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public Building getBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new CampfireBuilding(x, y);
    }

    @Override
    public Image getImage() {
        return new Image((new File("src/images/campfire_card.png")).toURI().toString());
    }
}
