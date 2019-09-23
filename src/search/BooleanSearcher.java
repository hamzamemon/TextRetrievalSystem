package search;

import index.Posting;
import index.PostingList;
import index.PostingLists;
import index.Term;
import index.TermIndex;
import query.BooleanQuery;

import java.io.File;
import java.util.Arrays;

/**
 * This class is do the calculation for AND, OR and NOT
 */
public final class BooleanSearcher {
    
    private static final PostingList ALL_FILES = getFilenames();
    
    /**
     * This method does the actual searching with the terms and the Postings
     *
     * @param query        object to hold the query
     * @param termIndex    Term and its postings
     * @param postingLists Postings of throughout the Documents
     *
     * @return the result of the query
     */
    public static PostingList search(BooleanQuery query, TermIndex termIndex, PostingLists postingLists) {
        String inputA = query.getInputA();
        Term termA = termIndex.get(inputA);
        PostingList listA = postingLists.getList(termA);
        
        String operator = query.getOperator();
        
        String inputB = query.getInputB();
        PostingList listB = new PostingList();
        if(!inputB.isEmpty()) {
            Term termB = termIndex.get(inputB);
            listB = postingLists.getList(termB);
        }
        
        PostingList backup = new PostingList();
        backup.addAll(listA);
        backup.addAll(listB);
        backup.sort(Posting.COMPARATOR);
        
        PostingList list = new PostingList();
        
        if("AND".equals(operator)) {
            if(!listA.isEmpty() && !listB.isEmpty()) {
                list = listA.intersect(backup);
            }
        }
        
        else if("OR".equals(operator)) {
            if(!listA.isEmpty() || !listB.isEmpty()) {
                list = listA.union(backup);
            }
        }
        
        else if("NOT".equals(operator) && ALL_FILES != null) {
            list = termIndex.containsKey(inputB) ? listB.subtract(ALL_FILES) : ALL_FILES;
        }
        
        return list.isEmpty() ? null : list;
    }
    
    /**
     * Converts input files to Postings
     *
     * @return input files
     */
    private static PostingList getFilenames() {
        File dir = new File("res/data/");
        File[] files = dir.listFiles();
        if(files == null) {
            return null;
        }
        
        Arrays.sort(files);
        PostingList postings = new PostingList();
        
        for(int i = 0, filesLength = files.length; i < filesLength; i++) {
            postings.add(new Posting(i));
        }
        
        return postings;
    }
}
