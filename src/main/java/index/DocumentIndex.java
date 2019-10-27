package index;

import java.util.HashMap;

/**
 * This class represents the HashMap of the index for the Documents. It maps a file number to a Document.
 */
public class DocumentIndex extends HashMap<Integer, Document> {
    
    private static final long serialVersionUID = 4536139274187876474L;
    
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
