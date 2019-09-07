package index;

import java.util.HashMap;

/**
 * This class represents the HashMap of the index for the Documents. It maps a filename (String) to a file (Document).
 */
public class DocumentIndex extends HashMap<Integer, Document> {
    
    /**
     * Add a document to the HashMap
     *
     * @param number        the document number
     * @param numberOfWords the number of words in the file after processing
     */
    public void addDocument(int number, int numberOfWords) {
        Document document = new Document(number, numberOfWords);
        put(number, document);
    }
}
