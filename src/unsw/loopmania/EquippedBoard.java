package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import unsw.Items.*;

public class EquippedBoard extends StaticEntity {
    private Item weapon = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));
    private Item armour;
    private Item shield;
    private Item helmet;

    public EquippedBoard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public Item getWeapon() {
        return weapon;
    }

    public void setWeapon(Item weapon) {
        this.weapon = weapon;
    }

    public Item getArmour() {
        return armour;
    }

    public void setArmour(Item armour) {
        this.armour = armour;
    }

    public Item getShield() {
        return shield;
    }

    public void setShield(Item shield) {
        this.shield = shield;
    }

    public Item getHelmet() {
        return helmet;
    }

    public void setHelmet(Item helmet) {
        this.helmet = helmet;
    }

    // This will update the newest state of the board when battling happens
    public void update() {
        Weapon w = (Weapon) weapon;
        if (w != null && w.wreckcheck(w)) {
            setWeapon(null);
        }
        Armour armour = (Armour) this.armour;
        if (armour != null && armour.wreckcheck(armour)) {
            setArmour(null);
        }
        Helmet helmet = (Helmet) this.helmet;
        if (helmet != null && helmet.wreckcheck(helmet)) {
            setHelmet(null);
        }
        Shield shield = (Shield) this.shield;
        if (shield != null && shield.wreckcheck(shield)) {
            setShield(null);
        }
    }

    @Override
    public Image getImage() {
        return null;
    }
}
