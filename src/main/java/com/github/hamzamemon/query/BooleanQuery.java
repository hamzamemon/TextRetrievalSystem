package com.github.hamzamemon.query;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class represents a single query for search (x AND y, x OR y, NOT x)
 */
@Data
@AllArgsConstructor
public class BooleanQuery {
    
    private String inputA;
    private String operator;
    private String inputB;
    
    /**
     * Constructor for NOT
     *
     * @param operator NOT
     * @param inputB   word that shouldn't be included in results
     */
    public BooleanQuery(String operator, String inputB) {
        this(null, operator, inputB);
    }
}
