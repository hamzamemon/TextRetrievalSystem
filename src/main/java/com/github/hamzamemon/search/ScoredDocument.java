package com.github.hamzamemon.search;

import lombok.Data;

import java.text.DecimalFormat;
import java.util.Comparator;

/**
 * A Document containing the name of the file and a score
 */
@Data
public class ScoredDocument {
    
    public static final Comparator<ScoredDocument> COMPARATOR = (t, t1) -> Double.compare(t1.score,
            t.score);
    private static final DecimalFormat FORMATTER = new DecimalFormat("#.###");
    
    private String filename;
    private double score;
    
    /**
     * Creates a ScoredDocument taking in the filename
     *
     * @param filename the filename
     */
    public ScoredDocument(String filename) {
        this.filename = filename;
    }
    
    /**
     * Formats the score and sets it
     *
     * @param score score
     */
    public void setScore(double score) {
        this.score = Double.parseDouble(FORMATTER.format(score));
    }
    
    /**
     * Constructor for ScoredDocument
     */
    private ScoredDocument() {
    }
}
