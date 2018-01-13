package index;

import java.util.HashMap;

/**
 * This class represents the HashMap of the index for the Documents. It maps a filename (String) to a file (Document).
 */
public class DocumentIndex extends HashMap<String, Document> {
    
    /**
     * Add a document to the HashMap
     *
     * @param name          the filename
     * @param numberOfWords the number of words in the file after processing
     */
    public void addDocument(String name, int numberOfWords) {
        Document document = new Document(name, numberOfWords);
        put(name, document);
    }
}
