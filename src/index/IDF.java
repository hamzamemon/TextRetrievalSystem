package index;

import java.io.Serializable;

/**
 * This class act an IDF for each term. It contains the document frequency and the index corresponding to the index of
 * the ArrayList of ArrayList
 */
public class IDF implements Serializable {
    
    private static int staticIndex = 0;
    private int docFrequency;
    private int termIndex = 0;
    
    /**
     * Instantiates a new Idf.
     */
    public IDF() {
        docFrequency = 1;
        termIndex = staticIndex;
        staticIndex++;
    }
    
    /**
     * Increment doc frequency.
     */
    public void incrementDocFrequency() {
        docFrequency++;
    }
    
    /**
     * Gets the document frequency.
     *
     * @return the document frequency
     */
    public int getDocFrequency() {
        return docFrequency;
    }
    
    /**
     * Gets the index
     *
     * @return the index
     */
    public int getIndex() {
        return termIndex;
    }
}
