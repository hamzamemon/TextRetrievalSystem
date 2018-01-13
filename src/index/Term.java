package index;

import java.io.Serializable;

/**
 * This class stores information about a word, such as the word itself, the number of documents that have said word
 * and the index to use to get PostingList from PostingLists class
 */
public class Term implements Serializable {
    
    private static int staticIndex = 0;
    private String term;
    private int idf;
    private int index;
    private double loggedIdf;
    
    /**
     * Instantiates a new Term.
     *
     * @param term the term itself
     */
    public Term(String term) {
        this.term = term;
        idf = 1;
        index = staticIndex;
        staticIndex++;
    }
    
    /**
     * Gets logged idf.
     *
     * @return the logged idf
     */
    public double getLoggedIdf() {
        return loggedIdf;
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
     * Gets index to be used in PostingLists class
     *
     * @return the index
     */
    public int getIndex() {
        return index;
    }
    
    /**
     * Increment doc frequency.
     */
    public void incrementDocFrequency() {
        idf++;
    }
}
