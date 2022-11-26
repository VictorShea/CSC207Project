package Game.Model;

import java.util.*;
import java.util.Dictionary;

public class Controller {
    public int size;
    public int timer;
    public String gameMode;
    public int rounds;
    public int curRounds;
    public String playerName;
    public BoggleGrid boggleGrid;
    private final String[] dice_small_grid= //dice specifications, for small and large grids
            {"AAEEGN", "ABBJOO", "ACHOPS", "AFFKPS", "AOOTTW", "CIMOTU", "DEILRX", "DELRVY",
                    "DISTTY", "EEGHNW", "EEINSU", "EHRTVW", "EIOSST", "ELRTTY", "HIMNQU", "HLNNRZ"};
    /**
     * dice used to randomize letter assignments for a big grid
     */
    private final String[] dice_big_grid =
            {"AAAFRS", "AAEEEE", "AAFIRS", "ADENNN", "AEEEEM", "AEEGMU", "AEGMNN", "AFIRSY",
                    "BJKQXZ", "CCNSTW", "CEIILT", "CEILPT", "CEIPST", "DDLNOR", "DDHNOT", "DHHLOR",
                    "DHLNOR", "EIIITT", "EMOTTT", "ENSSSU", "FIPRSY", "GORRVW", "HIPRRY", "NOOTUW", "OOOTTU"};

    private String randomizeLetters(int size){ //implementation not sure
        List<String> dice;
        if (size == 4){
            dice = Arrays.asList(dice_small_grid);
        }
        else{
            dice = Arrays.asList(dice_big_grid);
        }
        Collections.shuffle(dice);
        StringBuilder out = new StringBuilder();
        Random random = new Random();
        for (String var: dice){
            out.append(var.charAt(random.nextInt(var.length())));
        }
        return out.toString();
    }
    public void playRound(){

        //step 1. initialize the grid
        boggleGrid = new BoggleGrid(size);
        boggleGrid.initalizeBoard(randomizeLetters(size));
        //step 2. initialize the dictionary of legal words
        //step 3. find all legal words on the board, given the dictionary and grid arrangement.
        Map<String, ArrayList<Position>> allWords = new HashMap<String, ArrayList<Position>>();
    }

    public Controller(int size, int timer, String gameMode, int rounds){
        this.size = size;
        this.timer = timer;
        this.gameMode = gameMode;
        this.rounds = rounds;
        this.curRounds = 0;
        this.playerName = "asd";
    }

    //Called every timer seconds
    public boolean timer(){
        System.out.println("timer reached");
        return true;
    }

    //input word (unchecked)
    public boolean inputWord(String word){
        System.out.println(word);
        return true;
    }
}
