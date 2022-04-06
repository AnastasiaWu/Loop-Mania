package unsw.Items.BuildingCard;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.Buildings.Building;
import unsw.Buildings.VampireCastleBuilding;
import java.io.File;
import javafx.scene.image.Image;

/**
 * represents a vampire castle card in the backend game world
 */
public class VampireCastleCard extends Card {
    public VampireCastleCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public Building getBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new VampireCastleBuilding(x, y);
    }

    @Override
    public Image getImage() {
        return new Image((new File("src/images/vampire_castle_card.png")).toURI().toString());
    }
}
