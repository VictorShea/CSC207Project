package Game.Save;

import Game.Model.GameController;
import javafx.scene.control.ListView;

import java.io.*;
import java.util.List;

public class Load {

    static String[] getSaves() {
        //Access the list of saves.
        File saveFolder = new File("./boards/");
        return saveFolder.list();
    }

    static GameController loadSave(String filename) throws IOException {
        // Get the GameController from the file.
        FileInputStream file = null;
        ObjectInputStream in = null;
        try {
            file = new FileInputStream(filename);
            in = new ObjectInputStream(file);
            return (GameController) in.readObject();
        } catch (IOException|ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            in.close();
            file.close();
        }
    }
}
