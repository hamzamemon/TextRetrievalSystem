package index;

import java.text.DecimalFormat;
import java.util.Comparator;

/**
 * This class saves information about the cosine score of a Document.
 */
public class ScoredDocument {
    
    // sort by descending score
    public static final Comparator<ScoredDocument> COMPARATOR = (t, t1) -> Double.compare(t1.score, t.score);
    private static final DecimalFormat FORMATTER = new DecimalFormat("#.###");
    
    private String name;
    private double score;
    
    /**
     * Instantiates a new Scored document.
     *
     * @param name the document name
     */
    public ScoredDocument(String name) {
        this.name = name;
    }
    
    /**
     * Gets the document name
     *
     * @return the document name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the document's score
     *
     * @return the document's score
     */
    public double getScore() {
        return score;
    }
    
    /**
     * Sets the document's score.
     *
     * @param score the document's score formatted to 3 decimal places
     */
    public void setScore(double score) {
        this.score = Double.parseDouble(FORMATTER.format(score));
    }
    
    @Override
    public String toString() {
        return "ScoredDocument{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
