package Game.Save;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import Game.Model.GameController;
import java.time.Clock;

public class Save {

    public static boolean saveGameController(GameController controller ) {
        File saveFolder = new File("Game/Save/SaveFiles/");
        String[] saves = saveFolder.list();

        if(saves != null) {
            for (String save : saves) {
                if (save.startsWith(Integer.toString(controller.getID()))) {
                    File saveFile = new File(save);
                    saveFile.delete();
                }
            }
        }

        try {
            String fileName = "Game/Save/SaveFiles/" + controller.getID() + "_" + java.time.Clock.systemUTC().instant() + ".ser";
            fileName = fileName.replace(":", ",");
            File saveFile = new File(fileName);
            FileOutputStream output = new FileOutputStream(saveFile);
            ObjectOutputStream out = new ObjectOutputStream(output);
            out.writeObject(controller);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
