package com.github.hamzamemon.query;

import org.apache.commons.lang3.StringUtils;
import com.github.hamzamemon.process.Preprocess;

/**
 * Class to determine how to structure query object based on input
 */
public final class QueryParser {
    
    /**
     * Determine operator and object based on input
     *
     * @param queryTerms input (i.e. king AND queen)
     * @return the query object
     */
    public static BooleanQuery parse(String queryTerms) {
        String inputA;
        String operator;
        String inputB = "";
        
        String[] array = queryTerms.split(" ");
        
        if (array.length == 1) {
            inputA = Preprocess.process(array[0]);
            operator = "OR";
        }
        else if (array.length == 2) {
            operator = array[0].toUpperCase();
            if (!StringUtils.equals(operator, "NOT")) {
                return null;
            }
            
            inputB = Preprocess.process(array[1]);
            return new BooleanQuery(operator, inputB);
        }
        else if (array.length == 3) {
            operator = array[1].toUpperCase();
            if (!StringUtils.equals(operator, "AND") && !StringUtils.equals(operator, "OR")) {
                return null;
            }
            inputA = Preprocess.process(array[0]);
            operator = array[1].toUpperCase();
            inputB = Preprocess.process(array[2]);
        }
        else {
            return null;
        }
        
        return new BooleanQuery(inputA, operator, inputB);
    }
    
    /**
     * Constructor for QueryParser
     */
    private QueryParser() {
    }
}
