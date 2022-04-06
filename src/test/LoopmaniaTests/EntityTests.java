package test.LoopmaniaTests;

import unsw.Items.Sword;
import unsw.loopmania.Entity;
import unsw.loopmania.StaticEntity;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;

public class EntityTests {
    @Test
    public void entityShouldExist() {
        Sword entity = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        assertTrue(entity.shouldExist().get());

    }
}
