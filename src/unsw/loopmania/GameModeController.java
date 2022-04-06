package unsw.loopmania;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameModeController {

    private MenuSwitcher gameSwitcher;
    private LoopManiaWorld world;
    // private MenuSwitcher gameSwitcherBersek;
    // private MenuSwitcher gameSwitcherSurvival;

    @FXML
    private Button Normal;

    @FXML
    private Button Bersek;

    @FXML
    private Button Survival;

    @FXML
    private Button Confusing;

    @FXML
    public void BersekMode(ActionEvent event) {
        world.setGameMode("Bersek");
        gameSwitcher.switchMenu();
    }

    @FXML
    public void NormalMode(ActionEvent event) {
        world.setGameMode("Normal");
        gameSwitcher.switchMenu();
    }

    @FXML
    public void SurvivalMode(ActionEvent event) {
        world.setGameMode("Survival");
        gameSwitcher.switchMenu();
    }

    @FXML
    public void ConfusingMode(ActionEvent event) {
        world.setGameMode("Confusing");
        gameSwitcher.switchMenu();
    }

    // @FXML
    // void BersekMode(ActionEvent event) {
    //     gameSwitcherBersek.switchMenu();
    // }

    // @FXML
    // void NormalMode(ActionEvent event) {
    //     gameSwitcherNormal.switchMenu();
    // }

    // @FXML
    // void SurvivalMode(ActionEvent event) {
    //     gameSwitcherSurvival.switchMenu();
    // }

    public void setGameSwitcher(MenuSwitcher gameSwitcher, LoopManiaWorld world) {
        this.world = world;
        this.gameSwitcher = gameSwitcher;
    }

    // public void setGameSwitcherNormal(MenuSwitcher gameSwitcher) {
    //     this.gameSwitcherNormal = gameSwitcher;
    // }

    // public void setGameSwitcherBersek(MenuSwitcher gameSwitcher) {
    //     this.gameSwitcherBersek = gameSwitcher;
    // }

    // public void setGameSwitcherSurvival(MenuSwitcher gameSwitcher) {
    //     this.gameSwitcherSurvival = gameSwitcher;
    // }

}

