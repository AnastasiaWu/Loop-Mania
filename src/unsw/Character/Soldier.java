package unsw.Character;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import unsw.Enemies.BasicEnemy;
import unsw.loopmania.StaticEntity;
/**
 * creates a class named soldier which extends StaticEntity
 */
public class Soldier extends StaticEntity {
    private int soldierBattleRound = 0;
/**
 * 
 * @param x
 * @param y
 */
    public Soldier(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public Soldier() {
        super();
    }
/**
 * attack method
 * @param enemy
 */
    public void attack(BasicEnemy enemy) {
        enemy.decreaseHealth(2);
        soldierBattleRound++;
    }
/**
 * check if dead
 * @return
 */
    public boolean ifDead() {
        // System.out.println(soldierBattleRound);
        return soldierBattleRound >= 2 ? true : false;
    }
/**
 * getImage
 */
    @Override
    public Image getImage() {
        return new Image((new File("src/images/soldier.png")).toURI().toString());
    }
}
