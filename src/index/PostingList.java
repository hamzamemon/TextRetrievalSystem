package index;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is just an ArrayList of Postings that either performs AND, OR or NOT queries
 */
public class PostingList extends ArrayList<Posting> {
    
    private static final long serialVersionUID = 6896647847538170915L;
    
    /**
     * Update the ArrayList of Postings for each term in each file
     *
     * @param number the document number
     */
    public void updatePostings(int number) {
        Posting posting = get(size() - 1);
        if(posting.getNumber() == number) {
            posting.incrementFrequency();
        }
        
        else {
            add(new Posting(number));
        }
    }
    
    /**
     * Performs an AND query by looping through backup
     *
     * @param backup    the collection of both PostingLists
     *
     * @return the posting list
     */
    public PostingList intersect(PostingList backup) {
        PostingList postings = new PostingList();
        for(int i = 1; i < backup.size(); i++) {
            Posting prev = backup.get(i - 1);
            Posting next = backup.get(i);
            
            if(prev.getNumber() == next.getNumber()) {
                postings.add(next);
            }
        }
        
        return postings;
    }
    
    /**
     * Performs an OR query by looping through backup
     *
     * @param backup    the collection of both PostingLists
     *
     * @return the posting list
     */
    public PostingList union(PostingList backup) {
        Posting prev = backup.get(0);
        
        PostingList postings = new PostingList();
        postings.add(prev);
        
        for(int i = 1; i < backup.size(); i++) {
            Posting next = backup.get(i);
            if(prev.getNumber() != next.getNumber()) {
                postings.add(next);
            }
            prev = next;
        }
        
        return postings;
    }
    
    /**
     * Performs a NOT query by looping through backup and returning files not found
     *
     * @param backup    the collection of both PostingLists (word's Postings and all)
     *
     * @return the posting list
     */
    public PostingList subtract(PostingList backup) {
        for(int i = backup.size() - 1, j = size() - 1; i >= 0 && j >= 0; i--) {
            int number1 = backup.get(i).getNumber();
            int number2 = get(j).getNumber();
            
            if(number1 == number2) {
                backup.remove(i);
                j--;
            }
        }
        
        return backup;
    }
}
