package com.github.hamzamemon.index;

import lombok.Data;

import java.io.Serializable;

/**
 * This class creates a Document that acts as the text file. It contains the filename, the
 * length and the number of words in the file after processing it.
 */
@Data
public class Document implements Serializable {
    
    private static final long serialVersionUID = -2554333512339589168L;
    
    private String filename;
    private int numberOfWords;
    private double length;
    
    /**
     * Instantiates a new Document.
     *
     * @param filename      the filename
     * @param numberOfWords the number of words after processing
     */
    public Document(String filename, int numberOfWords) {
        this.filename = filename;
        this.numberOfWords = numberOfWords;
    }
    
    /**
     * Adds weight^2 to the length
     *
     * @param amount weight^2
     */
    public void addLength(double amount) {
        length += amount;
    }
}
