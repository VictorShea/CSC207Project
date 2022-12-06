package Game;

import Game.Model.GameController;
import Game.View.View;
import javafx.stage.Stage;



public class StartGame {
    View view;
    public StartGame(Stage stage, int size, int timer, boolean gameMode, int rounds, GameMenu menu, boolean voice, String name, String name2){
        GameController model = new GameController(size, timer, gameMode, rounds);
        view = new View(stage, model, menu, voice, name, name2);
    }
}
