package unsw.Enemies;

import unsw.loopmania.EquippedBoard;
import unsw.loopmania.PathPosition;

import unsw.Character.Character;
import unsw.Items.Armour;
import unsw.Items.Helmet;
import unsw.Items.Shield;
import java.io.File;
import javafx.scene.image.Image;

public class Slug extends BasicEnemy {
    private int health;
    private int damage;
    private int experience;
    private int gold;
    private PathPosition position;
    private boolean turnToSoldier = false;

    /**
     * Constructor for Slug class
     * @param position
     */
    public Slug(PathPosition position) {
        super(position);
        health = 6;
        damage = 2;
        experience = 10;
        gold = 5;
        this.position = position;
    }

    /**
     * default constructor for Slug class
     */
    public Slug() {
    }

    /**
     * implemented attack method
     * @param character
     */
    @Override
    public void attack(Character character) {
        int damage = this.damage;
        EquippedBoard equippedBoard = character.getEquippedBoard();
        if (character.checkIfArmourEquipped()) {
            damage = this.damage / 2;
            Armour armour = (Armour) equippedBoard.getArmour();
            armour.increAttackCnt();
        }
        if (character.checkIfShieldEquipped()) {
            damage = 0;
            Shield shield = (Shield) equippedBoard.getShield();
            shield.increAttackCnt();
        }
        if (character.checkIfHelmetEquipped()) {
            damage -= Math.round((float) this.damage * 0.4);
        }
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
        // Pythagoras: a^2+b^2 < radius^2 to see if within radius
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
        // Pythagoras: a^2+b^2 < radius^2 to see if within radius
        if (Math.pow((character.getX() - this.getX()), 2) + Math.pow((character.getY() - this.getY()), 2) <= 1) {
            return true;
        }
        return false;
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
     * implemented getDamage method
     * @return damage
     */
    @Override
    public int getDamage() {
        return damage;
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
     * implemented decreaseHealth method
     * @param health
     */
    @Override
    public void decreaseHealth(int health) {
        this.health -= health;
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
        if (obj instanceof Slug)
            return true;
        return false;
    }


    /**
     * set health method setting enemy object's health
     * @param health
     */
    @Override
    public void setHealth(int health) {
        if (this.health + health <= 6) {
            this.health += health;
        } else {
            this.health = 6;
        }

    }

    /**
     * get image from image folder
     * @return Image
     */
    public Image getImage() {
        return new Image((new File("src/images/slug.png")).toURI().toString());
    }
}
