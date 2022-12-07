package Tests;

import Game.Save.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SaveTests {

    //Test saving a file.
    @Test
    void saveAFileTest() {
        String[] first = Load.getSaves();
        Game.Model.GameController game = new Game.Model.GameController(4, 120, true, 4, "asd", "asd");
        game.playRound();
        game.playRound();
        Save.saveGameController(game);
        String[] end = Load.getSaves();
        int sizeOne = first.length;
        int sizeTwo = end.length;
        assertEquals(sizeOne +1, sizeTwo);
    }

    //Test saving a file.
    @Test
    void loadAFileTest() {
        Game.Model.GameController game = new Game.Model.GameController(4, 120, true, 4, "asd", "asd");
        game.playRound();
        game.playRound();
        game.timer();
        game.timer();
        Save.saveGameController(game);
        String[] end = Load.getSaves();
        String filename = Load.getSaves()[0];
        Game.Model.GameController loaded = Load.loadSave("Game/Save/SaveFiles/" + filename);
        assertEquals(loaded.rounds, game.rounds);
    }

}