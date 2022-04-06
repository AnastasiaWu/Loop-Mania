package unsw.Items.BuildingCard;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.Buildings.*;
import java.io.File;
import javafx.scene.image.Image;
/**
 * creates a class named TowerCard which extends card
 */
public class TowerCard extends Card {
    public TowerCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public Building getBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new TowerBuilding(x, y);
    }

    @Override
    public Image getImage() {
        return new Image((new File("src/images/tower_card.png")).toURI().toString());
    }
}
