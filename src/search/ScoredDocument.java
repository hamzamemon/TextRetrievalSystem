package search;

import java.text.DecimalFormat;
import java.util.Comparator;

/**
 * A Document containing the name of the file and a score
 */
public class ScoredDocument {

    public static final Comparator<ScoredDocument> COMPARATOR = (t, t1) -> Double.compare(t1.score, t.score);
    private static final DecimalFormat FORMATTER = new DecimalFormat("#.###");

    private int number;
    private double score;

    /**
     * Creates a ScoredDocument taking in the file name
     *
     * @param number the document number
     */
    public ScoredDocument(int number){
        this.number = number;
    }

    /**
     * Gets the document number
     *
     * @return document number
     */
    public int getNumber(){
        return number;
    }

    /**
     * Gets the document's core
     *
     * @return score
     */
    public double getScore(){
        return score;
    }

    /**
     * Formats the score and sets it
     *
     * @param score score
     */
    public void setScore(double score){
        this.score = Double.parseDouble(FORMATTER.format(score));
    }
    
    @Override
    public String toString() {
        return "ScoredDocument{" +
                "number=" + number +
                ", score=" + score +
                '}';
    }
}
