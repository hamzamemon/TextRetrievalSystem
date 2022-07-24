package com.github.hamzamemon.index;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a List of PostingList where the index is stored in the Term class for said Postings
 */
@Data
public class PostingLists extends ArrayList<PostingList> {
    
    private static final long serialVersionUID = -5361405310464617287L;
    
    private List<PostingList> postingLists;
    
    /**
     * Uses index stored to Term class to get the PostingList from this class
     *
     * @param term the term to get the PostingList for
     * @return the list
     */
    public PostingList getList(Term term) {
        PostingList postings;
        
        try {
            int index = term.getIndex();
            postings = get(index);
        }
        catch (NullPointerException | IndexOutOfBoundsException e) {
            postings = new PostingList();
        }
        
        return postings;
    }
}
