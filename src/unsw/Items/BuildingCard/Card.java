package unsw.Items.BuildingCard;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.Buildings.Building;
import unsw.loopmania.StaticEntity;
import javafx.scene.image.Image;

/**
 * a Card in the world which doesn't move
 */
public abstract class Card extends StaticEntity {
    // TODO = implement other varieties of card than VampireCastleCard
    public Card(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public abstract Building getBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y);

    public abstract Image getImage();
}
