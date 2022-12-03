package Model;

public class Controller {
    public int size;
    public int timer;
    public String gameMode;
    public int rounds;
    public int curRounds;
    public String playerName;
    public Controller(int size, int timer, String gameMode, int rounds){
        throw new UnsupportedOperationException();
    }

    //Called every timer seconds
    public boolean timer(){
        return true;
    }

    //input word (unchecked)
    public boolean inputWord(String word){
        throw new UnsupportedOperationException();
    }
}
