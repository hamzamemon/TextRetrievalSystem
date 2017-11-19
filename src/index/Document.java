package index;

import java.io.Serializable;

/**
 * This class creates a Document that acts as the text file. It contains the name of the file and the number of words in
 * the file after processing it.
 *
 * @author hamza
 */
public class Document implements Serializable {
    
    private String name;
    private int numberOfWords;
    
    /**
     * Instantiates a new Document.
     *
     * @param name          the filename
     * @param numberOfWords the number of words after processing
     */
    public Document(String name, int numberOfWords) {
        this.name = name;
        this.numberOfWords = numberOfWords;
    }
    
    /**
     * Gets the document's name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the number of words in the document after preprocessing
     *
     * @return the number of words
     */
    public int getNumberOfWords() {
        return numberOfWords;
    }
}
