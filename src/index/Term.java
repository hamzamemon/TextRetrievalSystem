package index;

import java.io.Serializable;

public class Term implements Serializable {
    
    private static int staticIndex = 0;
    private String term;
    private int idf;
    private int index = 0;
    private double loggedIdf;
    
    public Term(String term) {
        this.term = term;
        idf = 1;
        index = staticIndex;
        staticIndex++;
    }
    
    public double getLoggedIdf() {
        return loggedIdf;
    }
    
    public void setLoggedIdf(int N) {
        loggedIdf = Math.log10(N * 1.0 / idf);
    }
    
    public int getIndex() {
        return index;
    }
    
    public void incrementDocFrequency() {
        idf++;
    }
}
