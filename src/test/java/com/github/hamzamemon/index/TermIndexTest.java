package com.github.hamzamemon.index;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TermIndexTest {
    
    @Test
    public void test_getSize() {
        String contents = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        PostingLists postingLists = new PostingLists();
        
        TermIndex termIndex = new TermIndex();
        int size = termIndex.getSize("file", contents, postingLists);
        assertEquals(69, size);
        assertEquals(61, termIndex.size());
        assertEquals(62, postingLists.size());
    }
}
