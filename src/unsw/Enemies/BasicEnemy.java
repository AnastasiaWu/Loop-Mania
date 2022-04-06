package unsw.Enemies;

import java.util.Random;

import unsw.loopmania.MovingEntity;
import unsw.loopmania.PathPosition;
import unsw.Character.Character;
// Requirement Analysis

// When the enemies not in battle, they have:
// 1. moving around - specialMove - use pathPosition to check the next step, randomly moved
// 2. check at each step if the charater is in the attack range or support range - ifBattle, ifSupport

// When character battle with enemies, one of the following things happens:
// 1. Enemies deal damage or special attacks to the character - attack - the character's status, reducing health
// 2. Enemies takes damage from character - takeDamage - enemies status, reducing health
// 3. The enemies are killed - killed - Drop items, add random items and cards to character's equipment and so on
// 4. Character killed - win - Character vanishes

/**
 * a basic form of enemy in the world
 */
public abstract class BasicEnemy extends MovingEntity {
    
    /**
     * Constructor for Basic Enemy
     * @param position
     */
    public BasicEnemy(PathPosition position) {
        super(position);
    }

    /**
     * default
     * Constructor for Basic Enemy
     */
    public BasicEnemy() {
        super();
    }

    /**
     * move the enemy
     */
    public void move() {
        // TODO = modify this, since this implementation doesn't provide the expected
        // enemy behaviour
        // this basic enemy moves in a random direction... 25% chance up or down, 50%
        // chance not at all...
        int directionChoice = (new Random()).nextInt(2);
        if (directionChoice == 0) {
            moveUpPath();
        } else if (directionChoice == 1) {
            moveDownPath();
        }
    }

    /**
     * abstract method attack, for enemy attack the character
     * @param character
     */
    public abstract void attack(Character character);

    /**
     * 
     * @param character
     * @return boolean value
     */
    public abstract boolean ifBattle(Character character);

    /**
     * abstract method check if there's allie in the support range
     * @param character
     * @return boolean value
     */
    public abstract boolean ifSupport(Character character);

    /**
     * get health of the enemy object
     * @return health
     */
    public abstract int getHealth();

    /**
     * get experience of the enemy object
     * @return experience
     */
    public abstract int getExperience();

    /**
     * get gold of the enemy object 
     * @return gold
     */
    public abstract int getGold();

    /**
     * get position of enemy objection
     * @return pathPostion
     */
    public abstract PathPosition getPosition();

    /**
     * get damage caused by enemy object
     * @return damage
     */
    public abstract int getDamage();

    /**
     * decrease enemy's health
     * @param health
     */
    public abstract void decreaseHealth(int health);

    /**
     * check if enemy object will be turned into soldier
     * @return boolean value
     */
    public abstract boolean checkIfTurnToSoldier();

    /**
     * set enemy object to soldier
     * @param turnToSoldier
     */
    public abstract void setTurnToSoldier(boolean turnToSoldier);

    /**
     * check if object and current class are the same type
     * @param obj
     * @return boolean value
     */
    public abstract boolean sameType(Object obj);

    /**
     * set health to enemy object
     * @param health
     */
    public abstract void setHealth(int health);
}
