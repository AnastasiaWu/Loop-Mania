package test.ItemTest;

import org.junit.Test;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.Character.Character;
import unsw.Enemies.*;

public class ExperienceTest {

    public LoopManiaWorld initAworld() {
        List path = new ArrayList<Pair<Integer, Integer>>();
        path.add(new Pair<Integer, Integer>(96, 0));
        path.add(new Pair<Integer, Integer>(96, 0));
        path.add(new Pair<Integer, Integer>(96, 0));
        path.add(new Pair<Integer, Integer>(96, 0));
        LoopManiaWorld d = new LoopManiaWorld(3, 3, path);
        PathPosition pathPosition = new PathPosition(0, path);
        Character character = new Character(pathPosition);
        d.setCharacter(character);

        PathPosition slugp = new PathPosition(1, path);
        Slug enemySlug = new Slug(slugp);
        PathPosition zombiep = new PathPosition(2, path);
        Zombie enemyZombie = new Zombie(zombiep);

        PathPosition vampirep = new PathPosition(3, path);
        Vampire enemyV = new Vampire(vampirep);
        return d;
    }

    @Test
    public void experienceIncreaseTest() {
        LoopManiaWorld d = initAworld();
        Character c = d.getCharacter();
        int before = c.getExperience();
        c.increaseExperience(5);

        System.out.println(c.getExperience());

        int after = c.getExperience();
        assert before + 5 == after;

    }

}
