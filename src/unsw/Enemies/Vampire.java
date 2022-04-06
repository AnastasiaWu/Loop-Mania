package unsw.Enemies;

import unsw.Character.Character;
import unsw.Items.Armour;
import unsw.Items.Helmet;
import unsw.Items.Shield;
import unsw.loopmania.EquippedBoard;
import unsw.loopmania.PathPosition;
import java.io.File;
import javafx.scene.image.Image;

/**
 * Vampires have high damage, are susceptible to the stake weapon, 
 * and run away from campfires. They have a higher battle radius than slugs, 
 * and an even higher support radius. A critical bite (which has a random chance of occurring) 
 * from a vampire causes random additional damage with every vampire attack, 
 * for a random number of vampire attacks
 * @param health
 * @param damage
 * @param experience
 * @param gold
 * @param position
 * @param turnToSoldier
 */
public class Vampire extends BasicEnemy {
    
    private int health;
    private int damage;
    private int experience;
    private int gold;
    private PathPosition position;
    private boolean turnToSoldier = false;

    public Vampire() {
    }

    public Vampire(PathPosition position) {
        super(position);
        // Assume that vampire:
        // - has a health of 25.
        // - has damage of 5.
        // - has an attack range of 3 tiles.
        // - has a support range of 4 tiles.
        this.health = 15;
        this.damage = 5;
        this.gold = 50;
        this.experience = 100;
        this.position = position;
    }
    /**
     * @param character
     * override method attack 
     */
    @Override
    public void attack(Character character) {
        EquippedBoard equippedBoard = character.getEquippedBoard();
        int damage = this.damage;
        // check if armour equipped: yes - damage scale to 50%
        if (character.checkIfArmourEquipped()) {
            damage = this.damage / 2;
            Armour armour = (Armour) equippedBoard.getArmour();
            armour.increAttackCnt();
        }
        double chance = Math.random();
        // check if shield equipped: yes - chance decreased by 0.6
        if (character.checkIfShieldEquipped()) {
            chance -= 0.6;
            if (chance <= 0.3)
                return;
            specialAttack(character);
            Shield shield = (Shield) equippedBoard.getShield();
            shield.increAttackCnt();
        }
        if (chance <= 0.3) {
            // normal attack
            if (character.checkIfHelmetEquipped()) {
                // check if helmet equipped: yes - damage decreases by 40% of the origin damage
                // notice: if armour and helmet both equipped:
                // damage = original damage * (1 - 0.5 - 0.4)
                damage -= Math.round((float) this.damage * 0.4);
            }
            if (damage <= 0)
                return;
            character.decreaseHealth(damage);
        } else {
            specialAttack(character);
        }
    }
    /**
     * check if satisfy the ifBattleCondition
     * @param character
     * @return boolean 
     */
    @Override
    public boolean ifBattle(Character character) {
        if (Math.pow((character.getX() - this.getX()), 2) + Math.pow((character.getY() - this.getY()), 2) <= 9) {
            return true;
        }
        return false;
    }

    @Override
    public boolean ifSupport(Character character) {
        if (Math.pow((character.getX() - this.getX()), 2) + Math.pow((character.getY() - this.getY()), 2) <= 16) {
            return true;
        }
        return false;
    }
    /**
     * 
     * @param character
     */
    public void specialAttack(Character character) {
        character.decreaseHealth(10);
    }

    @Override
    public void decreaseHealth(int health) {
        this.health -= health;
    }

    // ======================== Getter ===================================
    @Override
    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
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
    public PathPosition getPosition() {
        return position;
    }

    @Override
    public boolean checkIfTurnToSoldier() {
        return turnToSoldier;
    }

    @Override
    public void setTurnToSoldier(boolean turnToSoldier) {
        this.turnToSoldier = turnToSoldier;
    }

    @Override
    public boolean sameType(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj instanceof Vampire)
            return true;
        return false;
    }

    @Override
    public void setHealth(int health) {
        if (this.health + health <= 20) {
            this.health += health;
        } else {
            this.health = 20;
        }
    }
/**
 * return Image
 */
    public Image getImage() {
        return new Image((new File("src/images/vampire.png")).toURI().toString());

    }
}
