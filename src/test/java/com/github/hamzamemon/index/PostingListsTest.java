package com.github.hamzamemon.index;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PostingListsTest {
    
    @Test
    public void test_getList() {
        PostingLists postingLists = new PostingLists();
        
        PostingList postingList = new PostingList();
        postingList.add(new Posting("file1"));
        postingLists.add(postingList);
        
        PostingList postings = postingLists.getList(new Term());
        assertNotNull(postings);
        assertEquals(1, postings.size());
        assertEquals("file1", postings.get(0).getFilename());
    }
    
    @Test
    public void test_getList_Null() {
        PostingLists postingLists = new PostingLists();
        
        PostingList postings = postingLists.getList(null);
        assertNotNull(postings);
        assertEquals(0, postings.size());
    }
    
    @Test
    public void test_getList_IndexOutOfBounds() {
        PostingLists postingLists = new PostingLists();
        
        PostingList postings = postingLists.getList(new Term());
        assertNotNull(postings);
        assertEquals(0, postings.size());
    }
}
