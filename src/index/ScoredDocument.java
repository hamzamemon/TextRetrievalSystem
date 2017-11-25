package index;

import java.text.DecimalFormat;
import java.util.Comparator;

public class ScoredDocument {
    
    public static final Comparator<ScoredDocument> COMPARATOR = (t, t1) -> Double.compare(t1.score, t.score);
    private static final DecimalFormat FORMATTER = new DecimalFormat("#.###");
    
    private String name;
    private double score;
    
    public ScoredDocument(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public double getScore() {
        return score;
    }
    
    public void setScore(double score) {
        this.score = Double.parseDouble(FORMATTER.format(score));
    }
}
