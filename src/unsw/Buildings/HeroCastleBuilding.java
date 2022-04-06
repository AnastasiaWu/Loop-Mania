package unsw.Buildings;

import java.io.File;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import unsw.Items.Armour;
import unsw.Items.HealthPotion;
import unsw.Items.Helmet;
import unsw.Items.Item;
import unsw.Items.Shield;
import unsw.loopmania.LoopManiaWorld;

// Requirements Analysis
// 1. HeroCastleBuilding must be settled at the begining - setHeroCastle.
// 2. The castle can buy items such as health potion. - buyItem() - the items' price are set in the class, we only provide the function of adding the items from the storage and decrease the gold.
// 3. The castle can sold items. - sellItem()
// 4. The shop windows - showshopList - this may need the drag action
// 5. The varible need to be set to check if the character reach the castle and which round it is - atCastle, ableToShop
// 6. In survival mode, the Human Player can only purchase 1 health potion each time. - we only consider the standard part for now - we can extends the standardCastle to survivalCastle and add the limitation to the function
// 7. In berserker mode, the Human Player cannot purchase more than 1 piece of protective gear (protective gear includes armour, helmets, and shields). - we only consider the standard part for now

public class HeroCastleBuilding extends Building {
    Pair<SimpleIntegerProperty, SimpleIntegerProperty> position;
    private int iniRound;
    private boolean buyHealthPotion = true;
    private boolean buyProtectedGear = true;
    /**
     * Variable used to determinate which game mode the player choose.
     */
    private String gameMode = "Normal";

    public HeroCastleBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        position = new Pair<>(x, y);
    }

    public HeroCastleBuilding() {
    }
/**
 * boolean method
 */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof HeroCastleBuilding)
            return true;
        return false;
    }
/**
 * check if  it is an available position
 */
    public boolean checkIfAvailablePosition(LoopManiaWorld loopManiaWorld) {
        return loopManiaWorld.getEntityOnPathWhichIndex(this) == -1 ? false : true;
    }
/**
 * check if disappear
 */
    @Override
    public boolean checkIfDisappear(int round) {
        return false;
    }
/** 
 * set round
 */
    @Override
    public void setRound(int round) {
        iniRound = round;
    }
/**
 * set game mode
 * @param mode
 */
    public void setGameMode(String mode) {
        this.gameMode = mode;
    }
/**
 * check if items available
 * @param item
 * @return
 */
    public boolean checkIfItemsAvailable(Item item) {
        if (item instanceof HealthPotion && gameMode.equals("Survival")) {
            if (buyHealthPotion) {
                buyHealthPotion = false;
                return true;
            }
            return false;
        }
        if ((item instanceof Helmet || item instanceof Armour || item instanceof Shield) && gameMode.equals("Bersek")) {
            if (buyProtectedGear) {
                buyProtectedGear = false;
                return true;
            }
            return false;
        }
        return true;
    }
/**
 * hero is leave
 */
    public void heroLeave() {
        buyHealthPotion = true;
        buyProtectedGear = true;
    }
/**
 * get image
 */
    @Override
    public Image getImage() {
        return new Image((new File("src/images/heros_castle.png")).toURI().toString());
    }
}
