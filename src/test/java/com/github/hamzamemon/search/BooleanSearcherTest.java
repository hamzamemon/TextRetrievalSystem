package com.github.hamzamemon.search;

import com.github.hamzamemon.index.PostingList;
import com.github.hamzamemon.index.PostingLists;
import com.github.hamzamemon.index.TermIndex;
import com.github.hamzamemon.query.BooleanQuery;
import com.github.hamzamemon.query.QueryParser;
import com.github.hamzamemon.util.TextRetrievalSystemObjectMother;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BooleanSearcherTest {
    
    private TermIndex termIndex;
    private PostingLists postingLists;
    
    @BeforeAll
    public void setUp() {
        termIndex = TextRetrievalSystemObjectMother.createTermIndex();
        postingLists = TextRetrievalSystemObjectMother.createPostingLists();
    }
    
    @Test
    public void test_search_Not() {
        BooleanQuery notQuery = QueryParser.parse("NOT test");
        
        PostingList actualPostingList = BooleanSearcher.search(notQuery, termIndex, postingLists);
        assertNotEquals("file1", actualPostingList.get(0).getFilename());
    }
    
    @Test
    public void test_search_And() {
        BooleanQuery andQuery = QueryParser.parse("test AND test");
        
        PostingList actualPostingList = BooleanSearcher.search(andQuery, termIndex, postingLists);
        assertEquals(2, actualPostingList.size());
        assertEquals("file1", actualPostingList.get(0).getFilename());
        assertEquals("file2", actualPostingList.get(1).getFilename());
    }
    
    @Test
    public void test_search_Or() {
        BooleanQuery orQuery = QueryParser.parse("test OR testing");
        
        PostingList actualPostingList = BooleanSearcher.search(orQuery, termIndex, postingLists);
        assertEquals(3, actualPostingList.size());
        assertEquals("file1", actualPostingList.get(0).getFilename());
        assertEquals("file1", actualPostingList.get(1).getFilename());
        assertEquals("file2", actualPostingList.get(2).getFilename());
    }
    
    @Test
    public void test_search_InvalidSearch() {
        BooleanQuery notQuery = QueryParser.parse("NOT sghsoghsoghs");
        
        PostingList actualPostingList = BooleanSearcher.search(notQuery, termIndex, postingLists);
        assertNotEquals("file1", actualPostingList.get(0).getFilename());
    }
}
