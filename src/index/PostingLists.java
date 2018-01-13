package index;

import java.util.ArrayList;

/**
 * This class is an array of PostingList where the index is stored in the Term class for said Postings
 */
public class PostingLists extends ArrayList<PostingList> {
    
    /**
     * Uses index stored to Term class to get the PostingList from this class
     *
     * @param term the term to get the PostingList for
     *
     * @return the list
     */
    public PostingList getList(Term term) {
        PostingList postings;
        
        try {
            int index = term.getIndex();
            postings = get(index);
        }
        catch(IndexOutOfBoundsException e) {
            postings = new PostingList();
        }
        
        return postings;
    }
}
