package unsw.Items.BuildingCard;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.Buildings.Building;
import unsw.Buildings.TrapBuilding;
import java.io.File;
import javafx.scene.image.Image;
/**
 * creates a card named trapcard which extends Card
 */
public class TrapCard extends Card {

    public TrapCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public Building getBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        TrapBuilding trap = new TrapBuilding(x, y);
        return trap;
    }

    @Override
    public Image getImage() {
        return new Image((new File("src/images/trap_card.png")).toURI().toString());
    }

}
