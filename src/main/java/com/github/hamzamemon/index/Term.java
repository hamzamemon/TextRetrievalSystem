package com.github.hamzamemon.index;

import lombok.Data;

import java.io.Serializable;

/**
 * This class stores information about a word, such as the number of documents that have said word
 * and the index to use to get PostingList from PostingLists class
 */
@Data
public class Term implements Serializable {
    
    private static final long serialVersionUID = -8026598333674490372L;
    
    private static int staticIndex;
    private int idf;
    private int index;
    private double loggedIdf;
    
    /**
     * Instantiates a new Term.
     */
    public Term() {
        idf = 1;
        index = staticIndex;
        staticIndex++;
    }
    
    /**
     * Sets logged idf.
     *
     * @param N the size of the DocumentIndex
     */
    public void setLoggedIdf(int N) {
        loggedIdf = Math.log10(N * 1.0 / idf);
    }
    
    /**
     * Increment doc frequency.
     */
    public void incrementDocFrequency() {
        idf++;
    }
}
