package Game;

import Game.Model.GameController;
import Game.View.View;
import javafx.stage.Stage;


public class StartGame {
    public static void StartGame(Stage stage, int size, int timer, boolean gameMode, int rounds, GameMenu menu){
        GameController model = new GameController(size, timer, gameMode, rounds);
        new View(stage, model, menu);
    }
    public static void loadGame(Stage stage, GameMenu menu, String filepath){
        GameController model = Game.Save.Load.loadSave(filepath);
        new View(stage, model, menu);
    }
}
