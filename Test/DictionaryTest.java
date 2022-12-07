package Test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import Game.View.DefinitionProcess;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DictionaryTest {

    @Test
    public void DictionaryTest() throws IOException, ClassNotFoundException {
        DefinitionProcess.initializeDictionary();
        HashMap<String, List<String>> out = (DefinitionProcess.get_defintion("aardvark"));
        assertEquals (out.toString(), "{Noun=[nocturnal burrowing mammal of the grasslands of Africa that feeds on termites; sole extant representative of the order Tubulidentata]}");

    }


}
