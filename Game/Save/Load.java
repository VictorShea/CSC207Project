package Game.Save;

import Game.Model.GameController;
import javafx.scene.control.ListView;

import java.io.*;
import java.util.List;

/**
 * The Load class.
 * Load will open up game saves.
 */
public class Load {

    /*
     * Return Save Files.
     */
    public static String[] getSaves() {
        //Access the list of saves.
        File saveFolder = new File("Game/Save/SaveFiles/");
        return saveFolder.list();
    }

    /*
     * Takes user word input.
     *
     * @param String filename.
     *
     * @return GameController Saved model.
     */
    public static GameController loadSave(String filename) {
        // Get the GameController from the file.
        FileInputStream file = null;
        ObjectInputStream in = null;
        try {
            file = new FileInputStream(filename);
            in = new ObjectInputStream(file);
            return (GameController)
                    in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                    file.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


}
