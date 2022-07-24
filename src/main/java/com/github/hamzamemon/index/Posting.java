package com.github.hamzamemon.index;

import lombok.Data;

import java.io.Serializable;

/**
 * This class creates a Posting, which is when a term appears in the file. It contains the filename,
 * the frequency of said term in the file, the weight and the first location of the term in the file.
 */
@Data
public class Posting implements Serializable, Comparable<Posting> {
    
    private static final long serialVersionUID = 3277327571128511637L;
    
    private String filename;
    private int frequency;
    private double weight;
    
    /**
     * Instantiates a new Posting.
     *
     * @param filename the filename
     */
    public Posting(String filename) {
        this.filename = filename;
        frequency = 1;
    }
    
    /**
     * Gets the logged term frequency
     *
     * @return logged term frequency
     */
    public double getLoggedTf() {
        return Math.log10(frequency) + 1;
    }
    
    /**
     * Increments frequency is the particular word is found another time in the file
     */
    public void incrementFrequency() {
        frequency++;
    }
    
    /**
     * Use for sorting ascending
     *
     * @param secondPosting the object to be compared.
     * @return 0 for equal, -1 for comes before "secondPosting" and 1 if after "secondPosting"
     */
    @Override
    public int compareTo(Posting secondPosting) {
        return filename.compareTo(secondPosting.filename);
    }
}
