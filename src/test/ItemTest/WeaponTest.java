package test.ItemTest;

import org.junit.Test;

import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import unsw.Items.Staff;
import unsw.Items.Weapon;

import static org.junit.jupiter.api.Assertions.assertTrue;



public class WeaponTest {

    @Test
    public void testGetImage() {
        Weapon we = new Staff();
        new JFXPanel();
        assertTrue(we.getImage() instanceof Image);
    }

    @Test
    public void testGetSlotImage() {
        Weapon we = new Staff();
        new JFXPanel();
        assertTrue(we.getSlotImage() instanceof Image);
    }
}
