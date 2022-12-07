package Game;

import Game.Model.GameController;
import Game.View.View;
import javafx.stage.Stage;



public class StartGame {
    public static void StartGame(Stage stage, int size, int timer, boolean gameMode, int rounds, GameMenu menu, boolean voice, String name, String name2, boolean color){
        GameController model = new GameController(size, timer, gameMode, rounds, name, name2);
        new View(stage, model, menu, voice, color);
    }
    public static void loadGame(Stage stage, GameMenu menu, String filepath, boolean voice, boolean color){
        GameController model = Game.Save.Load.loadSave(filepath);
        new View(stage, model, menu, voice, color);
    }
}
