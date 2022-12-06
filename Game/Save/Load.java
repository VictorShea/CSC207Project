package Game.Save;

import Game.Model.GameController;
import javafx.scene.control.ListView;

import java.io.*;
import java.util.List;

public class Load {

    public static String[] getSaves() {
        //Access the list of saves.
        File saveFolder = new File("Game/Save/SaveFiles/");
        return saveFolder.list();
    }

    public static GameController loadSave(String filename) {
        // Get the GameController from the file.
        FileInputStream file = null;
        ObjectInputStream in = null;
        try {
            file = new FileInputStream(filename);
            in = new ObjectInputStream(file);
            return (GameController)
                    in.readObject();
        } catch (IOException|ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try{in.close();
            file.close();}
            catch(IOException e){throw new RuntimeException(e);}
        }
    }


}
