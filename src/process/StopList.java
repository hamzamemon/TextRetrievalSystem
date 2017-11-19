package process;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

/**
 * This class is a HashSet of the words contained in stoplist.txt. These words should not be included in our index
 *
 * @author hamza
 */
public class StopList extends HashSet<String> {
    
    /**
     * Instantiates a new Stop list.
     */
    public StopList() {
        try(Scanner scanner = new Scanner(new File("src/process/stoplist.txt"))) {
            while(scanner.hasNext()) {
                String next = removeSingleQuotesAndLowerCase(scanner.next());
                add(next);
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("stoplist.txt cannot be opened");
        }
    }
    
    /**
     * This method removes single quotes from the stop words and makes them lower case
     *
     * @param contents the contents of the file
     *
     * @return the contents lower case and with single quotes removed
     */
    private String removeSingleQuotesAndLowerCase(String contents) {
        return contents.replace("'", "").toLowerCase();
    }
}
