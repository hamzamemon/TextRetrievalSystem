package search;

import index.IDF;
import index.Posting;
import index.PostingList;
import index.PostingLists;
import index.TermIndex;
import query.BooleanQuery;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class BooleanSearcher {

    private static final PostingList ALL_FILES = getFilenames();
    private static List<List<Integer>> locations;

    public static PostingList search(BooleanQuery query, TermIndex termIndex, PostingLists postingLists){
        String inputA = query.getInputA();
        PostingList listA = getList(inputA, termIndex, postingLists);

        String operator = query.getOperator();

        String inputB = query.getInputB();
        PostingList listB = getList(inputB, termIndex, postingLists);

        locations = new ArrayList<>(Math.abs(listB.size() - listA.size()));

        PostingList backup = new PostingList();
        backup.addAll(listA);
        backup.addAll(listB);
        backup.sort(Posting.COMPARATOR);

        PostingList list = new PostingList();

        if("AND".equals(operator)){
            if(termIndex.containsKey(inputA) && termIndex.containsKey(inputB)){
                list = listA.intersect(backup, locations);
            }
        }

        else if("OR".equals(operator)){
            if(termIndex.containsKey(inputA) || termIndex.containsKey(inputB)){
                list = listA.union(backup, locations);
            }
        }

        else if("NOT".equals(operator)){
            list = termIndex.containsKey(inputB) ? listB.subtract(ALL_FILES, locations) : ALL_FILES;
        }

        return list.isEmpty() ? null : list;
    }

    private static PostingList getList(String input, TermIndex termIndex, PostingLists postingLists){
        PostingList postings;

        try{
            IDF idfA = termIndex.get(input);
            int indexA = idfA.getIndex();
            postings = postingLists.get(indexA);
        }
        catch(NullPointerException e){
            postings = new PostingList();
        }

        return postings;
    }


    public static List<List<Integer>> getLocations(){
        return locations;
    }

    public static PostingList getFilenames(){
        File dir = new File("res/data/");
        File[] files = dir.listFiles();
        Arrays.sort(files);
        PostingList postings = new PostingList();

        for(File file : files){
            postings.add(new Posting(file.getName(), -1));
        }

        return postings;
    }
}