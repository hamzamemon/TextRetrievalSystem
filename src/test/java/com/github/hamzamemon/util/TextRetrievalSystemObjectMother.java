package com.github.hamzamemon.util;

import com.github.hamzamemon.index.Posting;
import com.github.hamzamemon.index.PostingList;
import com.github.hamzamemon.index.PostingLists;
import com.github.hamzamemon.index.Term;
import com.github.hamzamemon.index.TermIndex;

public final class TextRetrievalSystemObjectMother {
    
    public static TermIndex createTermIndex() {
        TermIndex termIndex = new TermIndex();
        termIndex.put("test", new Term());
        termIndex.put("testing", new Term());
        return termIndex;
    }
    
    public static PostingLists createPostingLists() {
        PostingList postingList = new PostingList();
        postingList.add(new Posting("file1"));
        postingList.add(new Posting("file2"));
        
        PostingList postingList2 = new PostingList();
        postingList2.add(new Posting("file1"));
        
        PostingLists postingLists = new PostingLists();
        postingLists.add(postingList);
        postingLists.add(postingList2);
        
        return postingLists;
    }
}
