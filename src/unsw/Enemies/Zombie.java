package unsw.Enemies;

import unsw.loopmania.EquippedBoard;
import unsw.loopmania.PathPosition;
import unsw.Character.Character;
import unsw.Items.Armour;
import unsw.Items.Helmet;
import unsw.Items.Shield;
import java.io.File;
import javafx.scene.image.Image;
/**
 * create a class named Zombie which extends BasicEnemy
 * @param health
 * @param damage
 * @param experience
 * @param gold
 * @param position
 * @param turnToSoldier
 */
public class Zombie extends BasicEnemy {
    private int health;
    private int damage;
    private int experience;
    private int gold;
    private PathPosition position;
    private boolean turnToSoldier = false;
    /**
     * assume zombie 
     * has health 15
     * has damage 3
     * has experience 50
     * has gold 25 
     * @param position
     */
    public Zombie(PathPosition position) {
        super(position);
        health = 15;
        damage = 3;
        experience = 50;
        gold = 25;
    }

    public Zombie() {
    }
    /**
     * @param character
     */
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
     * check if satisfy the battle condition
     * @param character
     * @return boolean
     */
    @Override
    public boolean ifBattle(Character character) {
        // Pythagoras: a^2+b^2 < radius^2 to see if within radius
        if (Math.pow((character.getX() - this.getX()), 2) + Math.pow((character.getY() - this.getY()), 2) <= 4) {
            return true;
        }
        return false;
    }
    /**
     * check if satisfy the support condition
     * @param character
     * @return boolean
     */
    @Override
    public boolean ifSupport(Character character) {
        // Pythagoras: a^2+b^2 < radius^2 to see if within radius
        if (Math.pow((character.getX() - this.getX()), 2) + Math.pow((character.getY() - this.getY()), 2) <= 9) {
            return true;
        }
        return false;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getExperience() {
        return experience;
    }

    @Override
    public int getGold() {
        return gold;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public PathPosition getPosition() {
        return position;
    }
    /**
     * @param health
     */
    @Override
    public void decreaseHealth(int health) {
        this.health -= health;
    }

    @Override
    public boolean checkIfTurnToSoldier() {
        return turnToSoldier;
    }
    /**
     * @param turntoSoldier
     */
    @Override
    public void setTurnToSoldier(boolean turnToSoldier) {
        this.turnToSoldier = turnToSoldier;
    }
    /**
     * @param obj
     * @return boolean 
     */
    @Override
    public boolean sameType(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj instanceof Zombie)
            return true;
        return false;
    }
    /**
     * @param health
     */
    @Override
    public void setHealth(int health) {
        if (this.health + health <= 15) {
            this.health += health;
        } else {
            this.health = 15;
        }

    }
    /**
     * get image
     */
    public Image getImage() {
        return new Image((new File("src/images/zombie.png")).toURI().toString());

    }
}
