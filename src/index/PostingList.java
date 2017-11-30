package index;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is just an ArrayList of Postings
 *
 * @author hamza
 */
public class PostingList extends ArrayList<Posting> {
    
    /**
     * Update the ArrayList of Postings for each term in each file
     *
     * @param name     the filename
     * @param location the first location of the term in the file
     */
    public void updatePostings(String name, int location, Term term) {
        Posting posting = get(size() - 1);
        if(posting.getName().equals(name)) {
            posting.addLocation(location);
        }
        
        else {
            add(new Posting(name, location));
            term.incrementDocFrequency();
        }
    }
    
    public PostingList intersect(PostingList backup, List<List<Integer>> locations) {
        PostingList postings = new PostingList();
        for(int i = 1; i < backup.size(); i++) {
            Posting prev = backup.get(i - 1);
            Posting next = backup.get(i);
            
            if(prev.getName().equals(next.getName())) {
                List<Integer> ints = new ArrayList<>(2);
                ints.add(prev.getFirstLocation());
                ints.add(next.getFirstLocation());
                locations.add(ints);
                
                postings.add(next);
            }
        }
        
        return postings;
    }
    
    public PostingList near(PostingList backup, List<List<Integer>> locations, int k) {
        PostingList postings = new PostingList();
        for(int i = 1; i < backup.size(); i++) {
            Posting prev = backup.get(i - 1);
            Posting next = backup.get(i);
            
            if(prev.getName().equals(next.getName())) {
                int x = 0;
                Posting smallest = prev.getLocations().size() < next.getLocations().size() ? prev : next;
                Posting largest = prev.getLocations().size() >= next.getLocations().size() ? prev : next;
                for(Integer small : smallest.getLocations()) {
                    for(Integer large : largest.getLocations()) {
                        if(Math.abs(small - large) <= k){
                            List<Integer> ints = new ArrayList<>(2);
                            ints.add(small);
                            ints.add(large);
                            locations.add(ints);
    
                            postings.add(next);
                        }
                    }
                }
            }
        }
        
        return postings;
    }
    
    public PostingList union(PostingList backup, List<List<Integer>> locations) {
        Posting prev = backup.get(0);
        
        List<Integer> ints = new ArrayList<>(1);
        ints.add(prev.getFirstLocation());
        locations.add(ints);
        
        PostingList postings = new PostingList();
        postings.add(prev);
        
        for(int i = 1; i < backup.size(); i++) {
            Posting next = backup.get(i);
            if(prev.getName().equals(next.getName())) {
                locations.get(locations.size() - 1).add(next.getFirstLocation());
            }
            else {
                ints = new ArrayList<>(1);
                ints.add(next.getFirstLocation());
                locations.add(ints);
                postings.add(next);
            }
            prev = next;
        }
        
        return postings;
    }
    
    public PostingList subtract(PostingList backup, List<List<Integer>> locations) {
        for(int i = backup.size() - 1, j = size() - 1; i >= 0 && j >= 0; i--) {
            String name1 = backup.get(i).getName();
            String name2 = get(j).getName();
            
            if(name1.equals(name2)) {
                backup.remove(i);
                j--;
            }
            else {
                List<Integer> ints = new ArrayList<>(1);
                ints.add(-1);
                locations.add(ints);
            }
        }
        
        return backup;
    }
}
