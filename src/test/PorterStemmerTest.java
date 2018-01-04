package test;

import org.junit.Test;
import process.stemmer.PorterStemmer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class PorterStemmerTest {
    
    @Test
    public void testPorterAlgorithm() {
        int count = 0;
        
        try(Scanner testInput = new Scanner(new File("testInput.txt"))) {
            Scanner testOutput = new Scanner(new File("testOutput.txt"));
            
            while(testInput.hasNext()) {
                String input = testInput.next();
                String output = testOutput.next();
                
                String stemmed = PorterStemmer.stem(input);
                if(!stemmed.equals(output)) {
                    count++;
                }
            }
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        
        assertEquals(count, 0);
    }
}
