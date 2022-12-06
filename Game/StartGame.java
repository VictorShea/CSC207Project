package Game;

import Game.Model.GameController;
import Game.View.View;
import javafx.stage.Stage;



public class StartGame {
    View view;
    public StartGame(Stage stage, int size, int timer, boolean gameMode, int rounds, GameMenu menu, boolean voice, String name, String name2, boolean color){
        System.out.println(name + " " + name2);
        GameController model = new GameController(size, timer, gameMode, rounds, name, name2);
        view = new View(stage, model, menu, voice, name, name2, color);
    }
}
