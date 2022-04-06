package unsw.Items.BuildingCard;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.Buildings.Building;
import unsw.Buildings.ZombiePitBuilding;
import java.io.File;
import javafx.scene.image.Image;
/**
 * creates a card named ZombiepitCard which extends card
 */
public class ZombiepitCard extends Card {
    public ZombiepitCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public Building getBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new ZombiePitBuilding(x, y);
    }

    @Override
    public Image getImage() {
        return new Image((new File("src/images/zombie_pit_card.png")).toURI().toString());
    }
}