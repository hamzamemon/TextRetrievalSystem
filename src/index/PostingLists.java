package index;

import java.util.ArrayList;

public class PostingLists extends ArrayList<PostingList> {
    
    public PostingList getList(Term term) {
        PostingList postings;
        
        try {
            int indexA = term.getIndex();
            postings = get(indexA);
        }
        catch(NullPointerException | IndexOutOfBoundsException e) {
            postings = new PostingList();
        }
        
        return postings;
    }
}
