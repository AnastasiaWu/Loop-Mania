package unsw.Enemies;

import javafx.scene.image.Image;
import unsw.Character.Character;
import unsw.loopmania.PathPosition;
import unsw.Items.*;
import java.io.File;
import javafx.scene.image.Image;
public class Doggie extends BasicEnemy {

    private int health;
    private int damage;
    private int experience;
    private int gold;
    private PathPosition position;
    private boolean turnToSoldier = false;
    
    /**
     * default constructor for Doggie class
     */
    public Doggie() {
    }

    /**
     * Constructor for Doggie class
     * @param position
     */
    public Doggie(PathPosition position) {
        super(position);
        // Assume that vampire:
        // - has a health of 10.
        // - has damage of 10.
        this.health = 10;
        this.damage = 10;
        this.gold = 50;
        this.experience = 100;
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
            damage = 0;
        if (character.checkIfHelmetEquipped())
            damage -= Math.round((float) this.damage * 0.4);
        if (damage <= 0)
            return;
        character.decreaseHealth(damage);
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
     * implemented specialAttack method
     * @param character
     */
    public void specialAttack(Character character) {
        character.decreaseHealth(10);
    }

    /**
     * implemented decreaseHealth method
     * @param health
     */
    @Override
    public void decreaseHealth(int health) {
        this.health -= health;
    }

    // ======================== Getter ===================================
    /**
     * implemented getHealth method
     * @return health
     */
    @Override
    public int getHealth() {
        return health;
    }

    /**
     * implemented getDamage method
     * @return damage
     */
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
     * implemented getPosition method
     * @return position
     */
    @Override
    public PathPosition getPosition() {
        return position;
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
     * implemented setIfTurnToSoldier method
     * @param turnToSoldier
     */
    @Override
    public void setTurnToSoldier(boolean turnToSoldier) {
        this.turnToSoldier = turnToSoldier;
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
        if (obj instanceof Doggie)
            return true;
        return false;
    }

    /**
     * get image from image folder
     * @return Image
     */
    @Override
    public Image getImage() {
        // TODO Auto-generated method stub
        return new Image((new File("src/images/doggie.png")).toURI().toString());
    }

    /**
     * set health method setting enemy object's health
     * @param health
     */
    @Override
    public void setHealth(int health) {
        if (this.health + health <= 10) {
            this.health += health;
        } else {
            this.health = 10;
        }
        
    }


    

}
