package search;

import index.*;
import query.BooleanQuery;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class BooleanSearcher {
    
    private static final PostingList ALL_FILES = getFilenames();
    private static List<List<Integer>> locations;
    
    public static PostingList search(BooleanQuery query, TermIndex termIndex, PostingLists postingLists) {
        if(query == null) {
            return null;
        }
        String inputA = query.getInputA();
        Term termA = termIndex.get(inputA);
        PostingList listA = postingLists.getList(termA);
        
        String operator = query.getOperator();
        
        String inputB = query.getInputB();
        Term termB = termIndex.get(inputB);
        PostingList listB = postingLists.getList(termB);
        
        locations = new ArrayList<>(Math.abs(listB.size() - listA.size()));
        
        PostingList backup = new PostingList();
        backup.addAll(listA);
        backup.addAll(listB);
        backup.sort(Posting.COMPARATOR);
        
        PostingList list = new PostingList();
        
        if("AND".equals(operator) || "NEAR".equals(operator)) {
            if(!listA.isEmpty() && !listB.isEmpty()) {
                list = listA.intersect(backup, locations);
            }
        }
        
        else if("OR".equals(operator)) {
            if(!listA.isEmpty() || !listB.isEmpty()) {
                list = listA.union(backup, locations);
            }
        }
        
        else if("NOT".equals(operator)) {
            list = termIndex.containsKey(inputB) ? listB.subtract(ALL_FILES, locations) : ALL_FILES;
        }
        if("NEAR".equals(operator)) {
            locations.clear();
            list = list.near(backup, locations, query.getK());
        }
        
        return list.isEmpty() ? null : list;
    }
    
    public static List<List<Integer>> getLocations() {
        return locations;
    }
    
    public static PostingList getFilenames() {
        File dir = new File("res/data/");
        File[] files = dir.listFiles();
        Arrays.sort(files);
        PostingList postings = new PostingList();
        
        for(File file : files) {
            postings.add(new Posting(file.getName(), -1));
        }
        
        return postings;
    }
}
