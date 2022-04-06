package unsw.Items.BuildingCard;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.Buildings.BarracksBuilding;
import unsw.Buildings.Building;
import java.io.File;
import javafx.scene.image.Image;
/**
 * create a class named Barrackscard
 */
public class BarracksCard extends Card {
    public BarracksCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public Building getBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new BarracksBuilding(x, y);
    }

    @Override
    public Image getImage() {
        return new Image((new File("src/images/barracks_card.png")).toURI().toString());
    }
}
