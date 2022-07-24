package com.github.hamzamemon.index;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * This class is an ArrayList of Postings that either performs AND, OR or NOT queries
 */
public class PostingList extends ArrayList<Posting> {
    
    private static final long serialVersionUID = 6896647847538170915L;
    
    /**
     * Update the ArrayList of Postings for each term in each file
     *
     * @param filename the filename
     */
    public void updatePostings(String filename) {
        Posting posting = get(size() - 1);
        if (StringUtils.equals(posting.getFilename(), filename)) {
            posting.incrementFrequency();
        }
        else {
            add(new Posting(filename));
        }
    }
    
    /**
     * Performs an AND query by looping through backup
     *
     * @param postings the collection of both PostingLists
     * @return the posting list
     */
    public static PostingList intersect(PostingList postings) {
        PostingList intersectedList = new PostingList();
        for (int i = 1; i < postings.size(); i++) {
            Posting prev = postings.get(i - 1);
            Posting next = postings.get(i);
            
            if (StringUtils.equals(prev.getFilename(), next.getFilename())) {
                intersectedList.add(next);
            }
        }
        
        return intersectedList;
    }
    
    /**
     * Performs an OR query by looping through backup
     *
     * @param postings the collection of both PostingLists
     * @return the posting list
     */
    public static PostingList union(PostingList postings) {
        Posting prev = postings.get(0);
        
        PostingList unionedList = new PostingList();
        unionedList.add(prev);
        
        for (int i = 1; i < postings.size(); i++) {
            Posting next = postings.get(i);
            if (StringUtils.equals(prev.getFilename(), next.getFilename())) {
                unionedList.add(next);
            }
            prev = next;
        }
        
        return unionedList;
    }
    
    /**
     * Performs a NOT query by looping through backup and returning files not found
     *
     * @param backup the collection of both PostingLists (word's Postings and all)
     * @return the posting list
     */
    public PostingList subtract(PostingList backup) {
        for (int i = backup.size() - 1, j = size() - 1; i >= 0 && j >= 0; i--) {
            String filename1 = backup.get(i).getFilename();
            String filename2 = get(j).getFilename();
            
            if (StringUtils.equals(filename1, filename2)) {
                backup.remove(i);
                j--;
            }
        }
        
        return backup;
    }
}
