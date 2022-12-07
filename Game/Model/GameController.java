package Game.Model;

import java.io.Serializable;
import java.util.*;

/**
 * The GameController class.
 * GameController will actually control the game, keep track of all data.
 */
public class GameController implements Serializable {
    private String name1, name2;

    /**
     * stores the size of board the player wants
     */
    public int size;
    /**
     * stores how many seconds the player wants the round to end
     */
    public int timer;
    /**
     * stores how many rounds the player wants
     */
    public int rounds;
    /**
     * keeps track of what round is currently being played
     */
    public int curRounds;
    /**
     * keeps track of whether it's the player's or opponent's term
     */
    private State currentState;
    /**
     * stores the chosen gamemode
     */
    public Gamemode gamemode;
    /**
     * stores points and played words
     */

    private NewStats stats;
    /**
     * stores the grid played on
     */
    public BoggleGrid grid;
    /**
     * stores all acceptable words
     */
    private Dictionary dict;
    /**
     * stores all acceptable words for this board
     */
    private HashMap<String, ArrayList<Position>> allWords;
    /**
     * stores unique id for this specific game
     */
    private int id;


    /* GameController constructor
     * ----------------------
     * Generates random id.
     * Converts gamemode bool to enum class.
     * Initializes NewStats, currentState, Dictionary and allWords.
     */ 
    public GameController(int size, int timer, boolean gameMode, int rounds, String name1, String name2){
        this.size = size;
        this.timer = timer;
        this.rounds = rounds;
        this.curRounds = 1;
        this.currentState = State.You;
        if(gameMode) {this.gamemode = Gamemode.Human;}
        else{this.gamemode = Gamemode.Computer;}
        this.stats = new NewStats();
        this.dict = new Dictionary("wordlist.txt");
        this.allWords = new HashMap<String, ArrayList<Position>>();
        this.id = new Random().nextInt(999999) + 100000;
        this.name1 = name1;
        this.name2 = name2;
    }

    /**
     * dice used to randomize letter assignments for a small grid
     */
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

    /*
     * @param int that's the size of the board
     * @return String a String of random letters (length 16 or 25 depending on the size of the grid)
     */
    private String randomizeLetters(int size){
        if(size == 5){
            List<String> clone = new ArrayList<String>(List.of(dice_big_grid));
            Collections.shuffle(clone);

            String result = "";
            int dice_sides = clone.get(0).length();
            Random randomize_index = new Random();

            for (String s : clone) {
                int random_index = randomize_index.nextInt(clone.get(0).length());
                result += s.charAt(random_index);
            }

            return result;}

        else{
            List<String> clone = new ArrayList<String>(List.of(dice_small_grid));
            Collections.shuffle(clone);

            String result = "";
            int dice_sides = clone.get(0).length();
            Random randomize_index = new Random();

            for (int i = 0; i < clone.size(); i++){
                int random_index =  randomize_index.nextInt(clone.get(0).length());
                result += clone.get(i).charAt(random_index);}

            return result;}
    }

    /*
     * A function that finds all valid words on the boggle board.
     *
     * @param allWords A mutable list of all legal words that can be found, given the boggleGrid grid letters
     * @param boggleDict A dictionary of legal words
     * @param boggleGrid A boggle grid, with a letter at each position on the grid
     */
    private void findAllWords(Map<String,ArrayList<Position>> allWords, Dictionary boggleDict, BoggleGrid boggleGrid) {

        for (int i = 0; i < this.grid.numRows(); i++) {
            for (int j = 0; j < this.grid.numCols(); j++) {
                ArrayList<Position> positions = new ArrayList<Position>();
                Position searching = new Position(i, j);
                String starter = Character.toString(this.grid.getCharAt(i, j));
                eachPosition(allWords, this.grid, searching, starter, positions);
            }
        }
    }


    /*
     * A function that finds all valid words on a specific position.
     *
     * @param allWords A mutable list of all legal words that can be found, given the boggleGrid grid letters
     * @param boggleDict A dictionary of legal words
     * @param boggleGrid A boggle grid, with a letter at each position on the grid
     * @param Position The position being searched.
     * @param Position Word found so far.
     */
    private void eachPosition(Map<String,ArrayList<Position>> allWords, BoggleGrid boggleGrid,
                               Position searching, String starter, ArrayList<Position> positions){
        positions.add(searching);
        boolean valid = this.dict.isPrefix(starter);

        if(this.dict.containsWord(starter) & (starter.length() > 3)){
            allWords.put(starter, positions);}

        if (valid){
            int row = searching.getRow();
            int col = searching.getCol();
            int updater = searching.getRow() + 1;
            int updatec = searching.getCol() + 1;
            int minusr = searching.getRow() - 1;
            int minusc = searching.getCol() - 1;


            if(updater < boggleGrid.numRows() & !hasP(updater, col, positions)){
                eachPosition(allWords, boggleGrid, new Position(updater, col),
                        starter + boggleGrid.getCharAt(updater, col), new ArrayList<>(positions));}

            if(updatec < boggleGrid.numCols() & !hasP(row, updatec, positions)){
                eachPosition(allWords, boggleGrid, new Position(row, updatec),
                        starter + boggleGrid.getCharAt(row, updatec), new ArrayList<>(positions));}

            if(minusr >= 0 & !hasP(minusr, col, positions)){
                eachPosition(allWords, boggleGrid, new Position(minusr, col),
                        starter + boggleGrid.getCharAt(minusr, col), new ArrayList<>(positions));}

            if(minusc >= 0 & !hasP(row, minusc, positions)){
                eachPosition(allWords, boggleGrid, new Position(row, minusc),
                        starter + boggleGrid.getCharAt(row, minusc), new ArrayList<>(positions));}

            if(minusr >= 0 & minusc >= 0 & !hasP(minusr, minusc, positions)){
                eachPosition(allWords, boggleGrid, new Position(minusr, minusc),
                        starter + boggleGrid.getCharAt(minusr, minusc), new ArrayList<>(positions));}

            if(minusr >= 0 & updatec < boggleGrid.numCols() & !hasP(minusr, updatec, positions)){
                eachPosition(allWords, boggleGrid, new Position(minusr, updatec),
                        starter + boggleGrid.getCharAt(minusr, updatec), new ArrayList<>(positions));}

            if(updater < boggleGrid.numRows() & minusc >= 0 & !hasP(updater, minusc, positions)){
                eachPosition(allWords, boggleGrid, new Position(updater, minusc),
                        starter + boggleGrid.getCharAt(updater, minusc), new ArrayList<>(positions));}

            if(updater < boggleGrid.numRows() & updatec < boggleGrid.numCols() & !hasP(updater, updatec, positions)){
                eachPosition(allWords, boggleGrid, new Position(updater, updatec),
                        starter + boggleGrid.getCharAt(updater, updatec), new ArrayList<>(positions));}

        }
    }

    /*
     * A function that checks if the position is valid.
     *
     * @param int Desired column.
     * @param int Desired row.
     * @param ArrayList<Position> List of valid positions.
     *
     * @return boolean If the position is valid.
     */
    private boolean hasP(int row, int col, ArrayList<Position> positions) {
        for (Position position : positions) {
            if (position.getRow() == row & col == position.getCol()) {
                return true;
            }
        }
        return false;
    }


    /*
     * Creates a new board.
     *
     * @param int Size of desired board.
     */
    private void initializeBoard(int size){
        BoggleGrid grid = new BoggleGrid(size);
        String letters = randomizeLetters(size);
        grid.initalizeBoard(letters);
        this.grid = grid;
    }

    /*
     * Start a new round.
     */
    public void playRound(){
        //Clear past stats
        this.stats.newRound();
        // Initialize a board
        initializeBoard(this.size);
        //Get all legal words
        findAllWords(this.allWords, this.dict, this.grid);
    }


    /*
     * Change the round when the timer ends.
     */
    public int timer(){
        //If the gamemode is Computer, allow the Computer to play, and end the round.
        if(this.gamemode == Gamemode.Computer) {
            this.currentState = State.Opponent;
            computerMove();
            this.stats.endRound();
            this.curRounds += 1;}

        //If it's the player's turn, switch to the opponent's turn.
        else if(this.currentState == State.You) {
            this.currentState = State.Opponent;
            return 0;
        }

        // If it's the opponent's turn, end the round.
        else {
            this.stats.endRound();
            this.curRounds += 1;
        }


        //If there are more rounds to be played, start a new round.
        if (this.curRounds < this.rounds+1) {
                this.currentState = State.You;
                return 1;
            }
        //Otherwise, end the game.
        else {return 2;}
    }

    /*
     * Gets words from the computer.
     * @param allWords A mutable list of all legal words that can be found, given the boggleGrid grid letters
     */
    private void computerMove(){
        for (String word : this.allWords.keySet()){
            if(!this.stats.getPlayerWords().contains(word.toLowerCase())){
                this.stats.addWord(word.toLowerCase(), NewStats.Player.Other);
            }
        }
    }


    /*
     * Takes user word input.
     *
     * @param String the verb inputted.
     *
     * @return boolean If the word was valid.
     */
    public boolean inputWord(String word){
        // create a verify function
        if (!allWords.containsKey(word.toUpperCase())){
            return false;
        }
        if (this.stats.getPlayerWords().contains(word.toLowerCase()) || this.stats.getOpponentWords().contains(word.toLowerCase())){
            return false;
        }
        if(this.currentState == State.You){
            NewStats.Player player = NewStats.Player.Human;
            this.stats.addWord(word.toLowerCase(), player);

        }
        else if(this.currentState == State.Opponent){
            NewStats.Player player = NewStats.Player.Other;
            this.stats.addWord(word.toLowerCase(), player);
        }
        return true;
    }

    /*
     * Display new words.
     */
    public Set<String> wordlist(){
       if(this.currentState == State.You){
           return this.stats.getPlayerWords();
       }
       else{return this.stats.getOpponentWords();}
    }

    public int getPoints(){
        if(this.currentState == State.You){
            return this.stats.getScorePlayer();
        }
        return this.stats.getScoreOther();
    }
    public int getPoints(int marker){
        if(marker == 0){
            return this.stats.getScorePlayer();
        }
        return this.stats.getScoreOther();
    }

    /*
     * Returns the current player.
     */
    public String getPlayerName(){
        if(this.gamemode == Gamemode.Computer){
            if(this.currentState == State.You){
                return name1;
            }
            else{return "Computer";}
        }

        if(this.currentState == State.You){
            return name1;
        }
        return name2;
    }

    /*
     * Returns specified player. .
     */
    public String getPlayerName(int marker){
        if(marker==0){
            if(this.gamemode == Gamemode.Computer){
                return name1;
            }
            else{return name1;}
        }
        else{
            if(this.gamemode == Gamemode.Computer){
                return "Computer";
            }
            else{return name2;}
        }
    }

    public int getTotalScore(){
        if(this.currentState == State.You){
            return this.stats.getTotalPlayer();
        }
        return this.stats.getTotalOther();
    }

    public int getTotalScore(int marker){
        if(marker == 0){
            return this.stats.getTotalPlayer();
        }
        return this.stats.getTotalOther();
    }

    /**
     * enumarable types of players (human or computer)
     */
    public enum Gamemode {
        Human("Human"),
        Computer("Computer");
        private final String gamemode;

        Gamemode(final String player) {
            this.gamemode = player;
        }
    }

    /**
     * enumarable types of state (You or Opponent)
     */
    public enum State {
        You("You"),
        Opponent("Opponent");
        private final String state;

        State(final String state) {
            this.state = state;
        }

    }

    public int getID(){
        return this.id;
    }

    public HashMap<String, ArrayList<Position>> getAllWords(){
        return this.allWords;
    }

    public NewStats getStats(){
        return this.stats;
    }



}
