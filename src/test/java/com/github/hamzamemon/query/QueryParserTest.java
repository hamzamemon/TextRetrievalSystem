package com.github.hamzamemon.query;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class QueryParserTest {
    
    @Test
    public void test_parse_Not() {
        BooleanQuery notQuery = QueryParser.parse("NOT x");
        assertNotNull(notQuery);
        assertNull(notQuery.getInputA());
        assertEquals("NOT", notQuery.getOperator());
        assertEquals("x", notQuery.getInputB());
    }
    
    @Test
    public void test_parse_Not_InvalidOperator() {
        BooleanQuery notQuery = QueryParser.parse("NOTnot x");
        assertNull(notQuery);
    }
    
    @Test
    public void test_parse_And() {
        BooleanQuery andQuery = QueryParser.parse("x AND y");
        assertNotNull(andQuery);
        assertEquals("x", andQuery.getInputA());
        assertEquals("AND", andQuery.getOperator());
        assertEquals("y", andQuery.getInputB());
    }
    
    @Test
    public void test_parse_Or_SingleValue() {
        BooleanQuery orQuery = QueryParser.parse("x");
        assertEquals("x", orQuery.getInputA());
        assertEquals("OR", orQuery.getOperator());
        assertEquals("", orQuery.getInputB());
    }
    
    @Test
    public void test_parse_Or_TwoValues() {
        BooleanQuery orQuery = QueryParser.parse("x OR y");
        assertNotNull(orQuery);
        assertEquals("x", orQuery.getInputA());
        assertEquals("OR", orQuery.getOperator());
        assertEquals("y", orQuery.getInputB());
    }
    
    @Test
    public void test_parse_InvalidOperator_TwoValues() {
        BooleanQuery invalidQuery = QueryParser.parse("x Andor y");
        assertNull(invalidQuery);
    }
    
    @Test
    public void test_parse_InvalidQuery() {
        BooleanQuery invalidQuery = QueryParser.parse("x AND NOT y");
        assertNull(invalidQuery);
    }
}
