package unsw.Character;

import unsw.loopmania.MovingEntity;
import unsw.loopmania.PathPosition;

import java.io.File;

import javafx.scene.image.Image;
import unsw.Enemies.BasicEnemy;
import unsw.Items.Anduril;
import unsw.Items.Armour;
import unsw.Items.Helmet;
import unsw.Items.Shield;
import unsw.Items.Staff;
import unsw.Items.Weapon;
import unsw.loopmania.EquippedBoard;
import unsw.Items.TreeStump;
import unsw.Items.Anduril;
import unsw.Enemies.ElanMuske;

/**
 * represents the main character in the backend of the game world
 * @param gold
 * @param experience
 * @param HEALTHLIMIT
 * @param GOLDLIMIT
 * @param EXPLIMIT
 * @param ATTACK
 * @param health
 * @param equippedBoard
 * @param characterUnderCampfire
 * @param characterUnderTower
 */
public class Character extends MovingEntity {
    // classes

    private int gold;
    private int experience;
    private int HEALTHLIMIT = 100;
    private int GOLDLIMIT = 10000;
    private int EXPLIMIT = 12000;
    private int ATTACK = 1;
    private int health = HEALTHLIMIT;
    private EquippedBoard equippedBoard;
    private boolean characterUnderCampfire = false;
    private boolean characterUnderTower = false;

    /**
     * create a character
     * 
     * @param position
     */
    public Character(PathPosition position) {
        super(position);
        equippedBoard = new EquippedBoard(null, null);
    }

    /**
     * set the health for the character
     * 
     * @param health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * get the gold amount
     * 
     * @return
     */
    public int getGold() {
        return gold;
    }

    /**
     * set the gold amount
     * 
     * @param gold
     */
    public void setGold(int gold) {
        this.gold = gold;
    }

    /**
     * increase the gold amount
     * 
     * @param gold
     */
    public void increaseGold(int gold) {
        if (this.gold + gold <= GOLDLIMIT) {
            this.gold = this.gold + gold;
        } else {
            this.gold = GOLDLIMIT;
        }
    }

    /**
     * decrease the gold amount
     * 
     * @param gold
     * @throws Exception
     */
    public void decreaseGold(int gold) throws Exception {
        if (this.gold - gold <= 0) {
            throw new Exception("Not Enough Gold!");
        } else {
            this.gold = this.gold - gold;
        }
    }

    /**
     * get the character's experience
     * 
     * @return the experience
     */
    public int getExperience() {
        return experience;
    }

    /**
     * increase the experience of the character
     * 
     * @param experience
     */
    public void increaseExperience(int experience) {
        if (this.experience + experience <= EXPLIMIT) {
            this.experience = this.experience + experience;
        } else {
            this.experience = EXPLIMIT;
        }
    }

    /**
     * get the health of the character
     * 
     * @return
     */
    public int getHealth() {
        return health;
    }

    /**
     * increase the health of the character
     * 
     * @param health
     */
    public void increaseHealth(int health) {
        if (this.health + health <= HEALTHLIMIT) {
            this.health = this.health + health;
        } else {
            this.health = HEALTHLIMIT;
        }
    }

    /**
     * decrease the health of the character
     * 
     * @param health
     */
    public void decreaseHealth(int health) {
        this.health = this.health - health;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    /** use the health potion to increase character's health */
    public void useHealthPotion() {
        this.health = HEALTHLIMIT;
    }

    /**
     * attack the enemy
     * 
     * @param enemy
     */
    public void attack(BasicEnemy enemy) {
        // debug
        // System.out.println("under campfire " + characterUnderCampfire);
        // System.out.println("under tower " + characterUnderTower);

        if (characterUnderTower == true) {
            enemy.decreaseHealth(enemy.getHealth());
            // System.out.println("enemy killed by tower");
        }
        if (!checkIfWeaponEquipped()) {
            // enemy.decreaseHealth(ATTACK);
            return;
        }

        Weapon weapon = (Weapon) equippedBoard.getWeapon();
        int damage = weapon.getDamage();

        if (weapon instanceof Anduril && enemy instanceof ElanMuske) {
            damage = damage * 3;
        }

        if (checkIfHelmetEquipped()) {
            damage = (int) Math.round(damage * 0.8);
            Helmet helmet = (Helmet) equippedBoard.getHelmet();
            helmet.increAttackCnt();
        }
        if (characterUnderCampfire == true) {
            enemy.decreaseHealth(weapon.getDamage() * 2);
            // System.out.println("character double damage by campfire");
        } else {
            enemy.decreaseHealth(damage);
        }
        weapon.increAttackCnt();

        // Check if enemy is defeated by staff, if yes, turn it into a soldier
        if (weapon instanceof Staff && enemy.getHealth() <= 0)
            enemy.setTurnToSoldier(true);

        equippedBoard.update();
    }

    /**
     * check if armour equipped
     * 
     * @return if armour equipped
     */
    public boolean checkIfArmourEquipped() {
        if (equippedBoard.getArmour() != null)
            return true;
        return false;
    }

    /**
     * check if weapon equipped
     * 
     * @return weapon equipped or not
     */
    public boolean checkIfWeaponEquipped() {
        if (equippedBoard.getWeapon() != null)
            return true;
        return false;
    }

    /**
     * check if helmet equipped
     * 
     * @return helmet equipped or not
     */
    public boolean checkIfHelmetEquipped() {
        if (equippedBoard.getHelmet() != null)
            return true;
        return false;
    }

    /**
     * check if the shield equipped
     * 
     * @return shield equipped or not
     */
    public boolean checkIfShieldEquipped() {
        if (equippedBoard.getShield() != null)
            return true;
        return false;
    }

    /**
     * check if tree stump equipped
     * 
     * @return tree stump equipped
     */
    public boolean checkIfTreeStumpEquipped() {
        if (equippedBoard.getShield() instanceof TreeStump)
            return true;
        return false;
    }

    /**
     * get the equipped board
     * 
     * @return equipped board
     */
    public EquippedBoard getEquippedBoard() {
        return equippedBoard;
    }

    /**
     * check if the character under the campfire
     * 
     * @return if the character under the campfire
     */
    public boolean isCharacterUnderCampfire() {
        return characterUnderCampfire;
    }

    /**
     * set the character under the campfire
     * 
     * @param characterUnderCampfire
     */
    public void setCharacterUnderCampfire(boolean characterUnderCampfire) {
        this.characterUnderCampfire = characterUnderCampfire;
    }

    /**
     * if the character under tower
     * 
     * @return if the character under the tower
     */
    public boolean isCharacterUnderTower() {
        return characterUnderTower;
    }

    /**
     * set character under tower
     * 
     * @param characterUnderTower
     */
    public void setCharacterUnderTower(boolean characterUnderTower) {
        this.characterUnderTower = characterUnderTower;
    }

    /** get the image */
    @Override
    public Image getImage() {
        return new Image((new File("src/images/human_new.png")).toURI().toString());
    }

    /**
     * get the health limitation
     * 
     * @return health limitation
     */
    public int getHEALTHLIMIT() {
        return HEALTHLIMIT;
    }

    /**
     * get the gold limit
     * 
     * @return gold limitation
     */
    public int getGOLDLIMIT() {
        return GOLDLIMIT;
    }

    /**
     * get the exprience limit
     * 
     * @return experience limitation
     */
    public int getEXPLIMIT() {
        return EXPLIMIT;
    }

}
