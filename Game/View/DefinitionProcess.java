package Game.View;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class DefinitionProcess {
    static FileInputStream file;
    static ObjectInputStream in;
    static HashMap<String, Long> defLoc;

    static RandomAccessFile raf;
    static LinkedHashMap<String, HashMap<String, List<String>>> cachedDefinition;
    public static HashMap<String, List<String>> get_defintion(String word) {
        if(cachedDefinition.containsKey(word)){
            return cachedDefinition.get(word);
        }
        String wordDef;
        try{
            raf.seek(defLoc.get(word));
            wordDef = raf.readLine();
        } catch (IOException e) {
            return null;
        }
        HashMap<String, List<String>> out = new HashMap<String, List<String>>();
        for (String def: wordDef.split("/a/")) {
            if (Objects.equals(def, word)){
                continue;
            }
            List<String> split_def = List.of(def.split("/b/"));
            out.put(split_def.get(0), split_def.subList(1, split_def.size()));
        }
        cachedDefinition.put(word, out);
        if(cachedDefinition.size() > 100){
            cachedDefinition.remove(cachedDefinition.entrySet().iterator().next().getKey());
        }
        return out;
    }
    public static void initializeDictionary() throws IOException, ClassNotFoundException {
        cachedDefinition = new LinkedHashMap<>();
        file = new FileInputStream("DefLoc.ser");
        in = new ObjectInputStream(file);
        defLoc= (HashMap<String, Long>) in.readObject();
        file.close();
        in.close();
        raf = new RandomAccessFile("Definition.txt", "r");

        BufferedReader r = new BufferedReader( new FileReader("wordlist.txt"));
    }
}
