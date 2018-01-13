package search;

import java.text.DecimalFormat;
import java.util.Comparator;

/**
 * A Document containing the name of the file and a score
 */
public class ScoredDocument {

    public static final Comparator<ScoredDocument> COMPARATOR = (t, t1) -> Double.compare(t1.score, t.score);
    private static final DecimalFormat FORMATTER = new DecimalFormat("#.###");

    private String name;
    private double score;

    /**
     * Creates a ScoredDocument taking in the file name
     *
     * @param name the filename
     */
    public ScoredDocument(String name){
        this.name = name;
    }

    /**
     * Gets the filename
     *
     * @return filename
     */
    public String getName(){
        return name;
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
}
