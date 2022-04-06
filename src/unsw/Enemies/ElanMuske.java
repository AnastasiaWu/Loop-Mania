package unsw.Enemies;

import javafx.scene.image.Image;
import unsw.Character.Character;
import unsw.loopmania.PathPosition;
import java.io.File;
import javafx.scene.image.Image;

public class ElanMuske extends BasicEnemy {
    private int health;
    private int damage;
    private int experience;
    private int gold;
    private PathPosition position;
    private boolean turnToSoldier = false;

    /**
     * default constructor for ElanMuske class
     */
    public ElanMuske() {

    }

    /**
     * Constructor for ElanMuske class
     * @param position
     */
    public ElanMuske(PathPosition position) {
        super(position);
        // Assume that ElanMuske:
        // - has a health of 75.
        // - has damage of 15.
        // - has an attack range of 3 tiles.
        // - has a support range of 4 tiles.
        this.health = 75;
        this.damage = 15;
        this.gold = 500;
        this.experience = 1000;
        this.position = position;
    }

    /**
     * implemented attack method
     * @param character
     */
    @Override
    public void attack(Character character) {
        int damage = this.damage;
        if (character.checkIfArmourEquipped())
            damage = this.damage / 2;
        if (character.checkIfShieldEquipped())
            damage = damage / 2;
        if (character.checkIfTreeStumpEquipped())
            damage = 0;
        if (character.checkIfHelmetEquipped())
            damage -= Math.round((float) this.damage * 0.4);
        if (damage <= 0)
            return;
        character.decreaseHealth(damage);
        
    }

    /**
     * implemented checkIfTurnToSoldier method
     * @return boolean value
     */
    @Override
    public boolean checkIfTurnToSoldier() {
        return turnToSoldier;
    }

    /**
     * implemented decreaseHealth method
     * @param health
     */
    @Override
    public void decreaseHealth(int health) {
        this.health -= health;
        
    }

    /**
     * implemented getDamage method
     * @return damage
     */
    @Override
    public int getDamage() {
        return damage;
    }

    /**
     * implemented getExperience method
     * @return experience
     */
    @Override
    public int getExperience() {
        return experience;
    }

    /**
     * implemented getGold method
     * @return gold
     */
    @Override
    public int getGold() {
        return gold;
    }

    /**
     * implemented getHealth method
     * @return health
     */
    @Override
    public int getHealth() {
        return health;
    }

    /**
     * implemented getPosition method
     * @return position
     */
    @Override
    public PathPosition getPosition() {
       return position;
    }

    /**
     * implemented ifBattle method
     * @param character
     */
    @Override
    public boolean ifBattle(Character character) {
        if (Math.pow((character.getX() - this.getX()), 2) + Math.pow((character.getY() - this.getY()), 2) <= 1) {
            return true;
        }
        return false;
    }

    /**
     * implemented ifSupport method
     * @param character
     */
    @Override
    public boolean ifSupport(Character character) {
        if (Math.pow((character.getX() - this.getX()), 2) + Math.pow((character.getY() - this.getY()), 2) <= 1) {
            return true;
        }
        return false;
    }

    /**
     * implemented sameType method
     * @param obj
     * @return boolean value
     */
    @Override
    public boolean sameType(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj instanceof ElanMuske)
            return true;
        return false;
    }

    /**
     * implemented setIfTurnToSoldier method
     * @param turnToSoldier
     */
    @Override
    public void setTurnToSoldier(boolean turnToSoldier) {
        this.turnToSoldier = turnToSoldier;
        
    }

    /**
     * heal enemy sharing same location with elanMuske
     * @param basicEnemy
     */
    public void heal(BasicEnemy basicEnemy) {
        if (basicEnemy.getPosition() == position) {
            basicEnemy.setHealth(3);
        }   
    }

    /**
     * set health method setting enemy object's health
     * @param health
     */
    @Override
    public void setHealth(int health) {
        if (this.health + health <= 75) {
            this.health += health;
        } else {
            this.health = 75;
        }

    }

    /**
     * get image from image folder
     * @return Image
     */
    @Override
    public Image getImage() {
        return new Image((new File("src/images/ElanMuske.png")).toURI().toString());
    }
    
}
