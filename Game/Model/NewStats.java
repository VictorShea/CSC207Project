package Game.Model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The BoggleStats class for the first Assignment in CSC207, Fall 2022
 * The BoggleStats will contain statsitics related to game play Boggle 
 */
public class NewStats implements Serializable {

    /**
     * set of words the player finds in a given round 
     */  
    private Set<String> playerWords = new HashSet<String>();  
    /**
     * set of words the opponent finds in a given round
     */  
    private Set<String> opponentWords = new HashSet<String>();
    /**
     * the player's score for the current round
     */  
    private int pScore;
    /**
     * the opponent's score for the current round
     */  
    private int oScore;
    /**
     * the player's total score across every round
     */  
    private int pScoreTotal; 
    /**
     * the opponents's total score across every round
     */  
    private int oScoreTotal;
    /**
     * the average number of words, per round, found by the player
     */  
    private double pAverageWords; 
    /**
     * the average number of words, per round, found by the opponent
     */  
    private double cAverageWords; 
    /**
     * the current round being played
     */  
    private int round; 

    /**
     * enumarable types of players (human or other)
     */  
    public enum Player {
        Human("Human"),
        Other("Other");
        private final String player;
        Player(final String player) {
            this.player = player;
        }
    }

    /* BoggleStats constructor
     * ----------------------
     * Sets round, totals and averages to 0.
     * Initializes word lists (which are sets) for the opponent and human players.
     */
    public NewStats() {
        this.pScore = 0;
        this.pScoreTotal = 0;
        this.pAverageWords = 0;

        this.oScore = 0;
        this.oScoreTotal = 0;
        this.cAverageWords = 0;

        this.opponentWords = new HashSet<String>();
        this.playerWords = new HashSet<String>();

        this.round = 0;
    }

    /* 
     * Add a word to a given player's word list for the current round.
     * You will also want to increment the player's score, as words are added.
     *
     * @param word     The word to be added to the list
     * @param player  The player to whom the word was awarded
     */
    public void addWord(String word, Player player) {
        if (player.player == "Human" & word.length() > 3){
            this.playerWords.add(word);
            this.pScore += word.length()-3;
        }
        if (player.player == "Other" & word.length() > 3){
            this.opponentWords.add(word);
            this.oScore += word.length()-3;
        }
    }

    /* 
     * End a given round.
     * This will clear out the human and opponent word lists, so we can begin again.
     * The function will also update each player's total scores, average scores, and
     * reset the current scores for each player to zero.
     * Finally, increment the current round number by 1.
     */
    public void endRound() {
        this.round += 1;
        this.pScoreTotal += this.pScore;
        this.oScoreTotal += this.oScore;
        this.cAverageWords = ((this.cAverageWords * (this.round - 1)) + this.opponentWords.size()) / this.round;
        this.pAverageWords = ((this.pAverageWords * (this.round - 1)) + this.playerWords.size()) / this.round;

    }


    public void newRound() {
        this.opponentWords = new HashSet<String>();
        this.playerWords = new HashSet<String>();
        this.oScore = 0;
        this.pScore = 0;
    }

    /* 
     * Summarize one round of boggle.  Print out:
     * The words each player found this round.
     * Each number of words each player found this round.
     * Each player's score this round.
     */

    /* 
     * Summarize the entire boggle game.  Print out:
     * The total number of rounds played.
     * The total score for either player.
     * The average number of words found by each player per round.
     */

    /* 
     * @return Set<String> The player's word list
     */
    public Set<String> getPlayerWords() {
        return this.playerWords;
    }

    public Set<String> getOpponentWords() {
        return this.opponentWords;
    }

    /*
     * @return int The number of rounds played
     */
    public int getRound() { return this.round; }

    /*
    * @return int The current player score
    */
    public int getScorePlayer() {
        return this.pScore;
    }

    /*
     * @return int The current opponent score
     */
    public int getScoreOther() {
        return this.oScore;
    }

    public int getTotalPlayer() {
        return this.pScoreTotal;
    }

    /*
     * @return int The current opponent score
     */
    public int getTotalOther() {
        return this.oScoreTotal;
    }

}
