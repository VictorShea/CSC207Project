// Java program implementing Singleton class
// with using getInstance() method


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

// Class 1
// Helper class
class Converter {

    private static Converter single_instance = null;

    // Declaring a variable of type Boolean
    public boolean ifSound;
    static Clip clip;

    private Converter()
    {
        ifSound = true;
    }

    public static Converter getInstance()
    {
        if (single_instance == null)
            single_instance = new Converter();

        return single_instance;
    }

    public static void playSound(String letter){
        String filename = "CSC207Project/Alphabet/" + letter + ".wav";
        try {
            File musicPath = new File(filename);
            if (musicPath.exists()) {
                if (clip != null){
                    clip.stop();
                }
                AudioInputStream audio = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start();

            } else {
                System.out.println("File not found, sorry.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }



}
