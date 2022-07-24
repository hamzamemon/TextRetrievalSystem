package com.github.hamzamemon.index;

import java.util.HashMap;

/**
 * This class represents the HashMap of the index for the Documents. It maps a filename to a Document.
 */
public class DocumentIndex extends HashMap<String, Document> {
    
    private static final long serialVersionUID = 4536139274187876474L;
    
    /**
     * Add a document to the HashMap
     *
     * @param filename      the filename
     * @param numberOfWords the number of words in the file after processing
     */
    public void addDocument(String filename, int numberOfWords) {
        Document document = new Document(filename, numberOfWords);
        put(filename, document);
    }
}
