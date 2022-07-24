package com.github.hamzamemon.index;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostingListTest {
    
    @Test
    public void test_updatePostings() {
        PostingList postings = new PostingList();
        postings.add(new Posting("file1"));
        postings.updatePostings("file1");
        
        assertEquals(1, postings.size());
        assertEquals(2, postings.get(0).getFrequency());
        
        postings.updatePostings("file2");
        assertEquals(2, postings.size());
        assertEquals(1, postings.get(1).getFrequency());
    }
    
    @Test
    public void test_intersect() {
        PostingList postingList = new PostingList();
        postingList.add(new Posting("file1"));
        postingList.add(new Posting("file1"));
        postingList.add(new Posting("file1"));
        postingList.add(new Posting("file2"));
        postingList.add(new Posting("file3"));
        postingList.add(new Posting("file3"));
        
        PostingList postings = PostingList.intersect(postingList);
        assertEquals(3, postings.size());
        assertEquals("file1", postings.get(0).getFilename());
        assertEquals("file1", postings.get(1).getFilename());
        assertEquals("file3", postings.get(2).getFilename());
    }
    
    @Test
    public void test_intersect_OneElement() {
        PostingList postingList = new PostingList();
        postingList.add(new Posting("file1"));
        
        PostingList postings = PostingList.intersect(postingList);
        assertEquals(0, postings.size());
    }
    
    @Test
    public void test_union() {
        PostingList postingList = new PostingList();
        postingList.add(new Posting("file1"));
        postingList.add(new Posting("file1"));
        postingList.add(new Posting("file1"));
        postingList.add(new Posting("file2"));
        postingList.add(new Posting("file3"));
        postingList.add(new Posting("file3"));
        
        PostingList postings = PostingList.union(postingList);
        assertEquals(4, postings.size());
        assertEquals("file1", postings.get(0).getFilename());
        assertEquals("file1", postings.get(1).getFilename());
        assertEquals("file1", postings.get(2).getFilename());
        assertEquals("file3", postings.get(3).getFilename());
    }
    
    @Test
    public void test_union_OneElement() {
        PostingList postingList = new PostingList();
        postingList.add(new Posting("file1"));
        
        PostingList postings = PostingList.union(postingList);
        assertEquals(1, postings.size());
        assertEquals("file1", postings.get(0).getFilename());
    }
    
    @Test
    public void test_subtract() {
        PostingList postingList = new PostingList();
        postingList.add(new Posting("file1"));
        postingList.add(new Posting("file4"));
        
        PostingList masterList = new PostingList();
        masterList.add(new Posting("file1"));
        masterList.add(new Posting("file2"));
        masterList.add(new Posting("file3"));
        masterList.add(new Posting("file4"));
        
        PostingList postings = postingList.subtract(masterList);
        assertEquals(2, postings.size());
        assertEquals("file2", postings.get(0).getFilename());
        assertEquals("file3", postings.get(1).getFilename());
    }
}
