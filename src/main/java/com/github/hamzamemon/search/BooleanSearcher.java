package com.github.hamzamemon.search;

import com.github.hamzamemon.index.Posting;
import com.github.hamzamemon.index.PostingList;
import com.github.hamzamemon.index.PostingLists;
import com.github.hamzamemon.index.Term;
import com.github.hamzamemon.index.TermIndex;
import com.github.hamzamemon.query.BooleanQuery;
import com.github.hamzamemon.util.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

/**
 * This class does the calculation for AND, OR and NOT
 */
public final class BooleanSearcher {
    
    private static final PostingList ALL_FILES = getFilenames();
    
    /**
     * This method does the actual searching with the terms and the Postings
     *
     * @param query        object to hold the query
     * @param termIndex    Term and its postings
     * @param postingLists Postings of throughout the Documents
     * @return the result of the query
     */
    public static PostingList search(BooleanQuery query, TermIndex termIndex,
                                     PostingLists postingLists) {
        String inputA = query.getInputA();
        PostingList listA = new PostingList();
        if (StringUtils.isNotEmpty(inputA)) {
            Term termA = termIndex.get(inputA);
            listA = postingLists.getList(termA);
        }
        
        String operator = query.getOperator();
        
        String inputB = query.getInputB();
        PostingList listB = new PostingList();
        if (StringUtils.isNotEmpty(inputB)) {
            Term termB = termIndex.get(inputB);
            listB = postingLists.getList(termB);
        }
        
        PostingList backup = new PostingList();
        backup.addAll(listA);
        backup.addAll(listB);
        Collections.sort(backup);
        
        PostingList list = new PostingList();
        
        if (StringUtils.equals(operator, "AND")) {
            if (!listA.isEmpty() && !listB.isEmpty()) {
                list = PostingList.intersect(backup);
            }
        }
        else if (StringUtils.equals(operator, "OR")) {
            if (!listA.isEmpty() || !listB.isEmpty()) {
                list = PostingList.union(backup);
            }
        }
        else if (StringUtils.equals(operator, "NOT") && ALL_FILES != null) {
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
        File dir = new File("src/main/resources/data/");
        File[] files = dir.listFiles();
        if (files == null) {
            return null;
        }
        
        Arrays.sort(files);
        PostingList postings = new PostingList();
        
        for (File file: files) {
            postings.add(new Posting(FileUtils.getFilename(file)));
        }
        
        return postings;
    }
    
    /**
     * Constructor for BooleanSearcher
     */
    private BooleanSearcher() {
    }
}
