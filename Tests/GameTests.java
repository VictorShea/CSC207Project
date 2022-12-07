package Tests;

import java.util.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import Game.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {

    //GameController Test
    @Test
    void findAllWordsSmallGrid() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Game.Model.GameController game = new Game.Model.GameController(4, 60, true, 4);
        Method method = game.getClass().getDeclaredMethod("findAllWords", Map.class, Game.Model.Dictionary.class, Game.Model.BoggleGrid.class);
        method.setAccessible(true);

        Game.Model.Dictionary boggleDict = new Game.Model.Dictionary("wordlist.txt");
        Map<String, ArrayList<Game.Model.Position>> allWords = new HashMap<>();
        Game.Model.BoggleGrid grid = new Game.Model.BoggleGrid(4);
        grid.initalizeBoard("RHLDNHTGIPHSNMJO");
        game.grid = grid;
        Object r = method.invoke(game, allWords, boggleDict, grid);

        Set<String> expected = new HashSet<>(Arrays.asList("GHOST", "HOST", "THIN"));
        assertEquals(expected, allWords.keySet());
        System.out.println(allWords.keySet());
    }

    @Test
    void findAllWordsBigGridSize() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Game.Model.GameController game = new Game.Model.GameController(5, 60, true, 4);
        Method method = game.getClass().getDeclaredMethod("findAllWords", Map.class, Game.Model.Dictionary.class, Game.Model.BoggleGrid.class);
        method.setAccessible(true);

        Game.Model.Dictionary boggleDict = new Game.Model.Dictionary("wordlist.txt");
        Map<String, ArrayList<Game.Model.Position>> allWords = new HashMap<>();
        Game.Model.BoggleGrid grid = new Game.Model.BoggleGrid(5);
        grid.initalizeBoard("IRSODPEKSESTAENIORGRCKNWO");
        game.grid = grid;
        Object r = method.invoke(game, allWords, boggleDict, grid);

        int size = allWords.keySet().size();
        assertEquals(315, size);
    }

    @Test
    void findAllWordsSmallGridShuffled() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Game.Model.GameController game = new  Game.Model.GameController(4, 60, true, 4);
        Method method = game.getClass().getDeclaredMethod("findAllWords", Map.class, Game.Model.Dictionary.class, Game.Model.BoggleGrid.class);
        method.setAccessible(true);

        Game.Model.Dictionary boggleDict = new Game.Model.Dictionary("wordlist.txt");
        Map<String, ArrayList<Game.Model.Position>> allWords = new HashMap<>();
        Game.Model.BoggleGrid grid = new Game.Model.BoggleGrid(4);
        grid.initalizeBoard("PIALTPFAETORSSXOMHHVESIAA");
        game.grid = grid;
        Object r = method.invoke(game, allWords, boggleDict, grid);

        assertEquals(53, allWords.keySet().size());
        System.out.println(allWords.keySet());
    }

    //Dictionary Test
    @Test
    void containsWord() {
        Game.Model.Dictionary dict = new Game.Model.Dictionary("./wordlist.txt");
        assertTrue(dict.containsWord("ENZYME"));
        assertTrue(dict.isPrefix("pench"));
    }

    //BoggleGrid Test
    @Test
    void setupBoard() {
        Game.Model.BoggleGrid grid = new Game.Model.BoggleGrid(10);
        String letters = "";
        for (int i = 0; i < 10; i++) {
            letters = letters + "0123456789";
        }

        grid.initalizeBoard(letters);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertEquals(letters.charAt(i * 10 + j), grid.getCharAt(i, j));
            }
        }
    }

    //NewStats Test
    @Test
    void endRoundTest() {
        Game.Model.NewStats stats = new Game.Model.NewStats();
        stats.endRound();
        stats.endRound();
        stats.endRound();
        assertEquals(3, stats.getRound());
    }


    //playRound Test
    @Test
    void playRoundTest() {
        Game.Model.GameController game = new Game.Model.GameController(4, 60, true, 4);
        game.playRound();
        Game.Model.BoggleGrid gridOne = game.grid;
        game.playRound();
        Game.Model.BoggleGrid gridTwo = game.grid;
        assertNotEquals(gridOne, gridTwo);
    }

    //Testing timer resets properly
    @Test
    void timerTest() {
        Game.Model.GameController game = new Game.Model.GameController(4, 60, true, 4);
        game.playRound();
        int current = game.curRounds;
        assertEquals(1, current);
        game.timer();
        int currentUpdated = game.curRounds;
        assertEquals(1, currentUpdated);
        game.timer();
        int currentUpdatedFinal = game.curRounds;
        assertEquals(2, currentUpdatedFinal);

    }
    //Checking that input word checks properly. 
    @Test
    void inputWordTest() {
        Game.Model.GameController game = new Game.Model.GameController(4, 120, true, 4);
        game.playRound();
        game.timer();
        boolean bool = game.inputWord("aardvarkee");
        assertEquals(false, bool);
        int points = game.getStats().getScorePlayer();
        assertEquals(0, points);

    }



}