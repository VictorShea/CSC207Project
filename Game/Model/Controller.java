package Game.Model;

import java.util.*;
import java.util.Dictionary;

public class Controller {
    public int size;
    public int timer;
    public boolean gameMode;
    public int rounds;
    public int curRounds;
    public BoggleGrid boggleGrid;

    public void playRound(){

        //step 1. initialize the grid
        boggleGrid = new BoggleGrid(size);
        boggleGrid.initalizeBoard("1234512345123451234512345");
        //step 2. initialize the dictionary of legal words
        //step 3. find all legal words on the board, given the dictionary and grid arrangement.
    }

    public Controller(int size, int timer, boolean gameMode, int rounds){
        this.size = size;
        this.timer = timer;
        this.gameMode = gameMode;
        this.rounds = rounds;
        this.curRounds = 0;
    }

    public int timer(){
        System.out.println("timer reached");
        return 1;
    }

    public boolean inputWord(String word){
        System.out.println(word);
        return true;
    }

    public String getCurrentPlayer(){
        return "Test";
    }

    public String[] wordList(){
        return new String[]{"asd", "aasd2", "asd23"};
    }

    public int getPoint(){
        return 1;
    }

    public int getPoint(int marker){
        return 0;
    }
    public String getPlayerName(){
        return "afg";
    }

    public String getPlayerName(int marker){
        return "asAS";
    }
    public int getTotalScore(int marker){
        return marker;
    }
    public int getTotalScore(){
        return 1;
    }
}
