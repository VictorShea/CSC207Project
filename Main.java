import com.sun.webkit.Timer;
import javafx.application.Application;
import javafx.stage.Stage;

import Game.StartGame;
public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        new StartGame(stage, 5, 10, true, 5);
    }
}