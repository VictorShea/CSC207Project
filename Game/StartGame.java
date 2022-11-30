package Game;

import Game.Model.Controller;
import Game.View.View;
import javafx.application.Application;
import javafx.stage.Stage;


public class StartGame {
    public StartGame(Stage stage, int size, int timer, boolean gameMode, int rounds){
        Controller model = new Controller(size, timer, gameMode, rounds);
        View view = new View(stage, model);
    }
}
