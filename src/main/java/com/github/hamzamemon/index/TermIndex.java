package com.github.hamzamemon.index;

import com.github.hamzamemon.process.Preprocess;
import com.github.hamzamemon.util.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * This class represents the HashMap of the index for the terms
 */
public class TermIndex extends TreeMap<String, Term> {
    
    private static final long serialVersionUID = -4668144947469533622L;
    
    public void addDocuments(DocumentIndex documentIndex, PostingLists postingLists) {
        File dir = new File("src/main/resources/data/");
        File[] files = dir.listFiles();
        Arrays.sort(files);
        
        for (File file: files) {
            String contents = null;
            try (Scanner scanner = new Scanner(file).useDelimiter("\\Z")) {
                contents = scanner.next();
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            
            String filename = FileUtils.getFilename(file);
            int size = getSize(filename, contents, postingLists);
            documentIndex.addDocument(filename, size);
        }
    }
    
    /**
     * This method returns the number of words in the file that pass the 'Preprocessing' class. It
     * loops through every word in 'contents' and 'Preprocess'es each of them
     *
     * @param filename     the filename
     * @param contents     the contents of the file
     * @param postingLists the PostingLists
     * @return the number of words in the file after processing 'contents'
     */
    public int getSize(String filename, String contents, PostingLists postingLists) {
        int size = 0;
        
        StringTokenizer tokenizer = new StringTokenizer(contents);
        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken();
            String processedWord = Preprocess.process(word);
            
            if (!processedWord.isEmpty()) {
                size++;
                
                Term term = containsKey(processedWord) ? get(processedWord) : new Term();
                PostingList postings = postingLists.getList(term);
                
                // if first word in the file
                if (postings.isEmpty()) {
                    postings.add(new Posting(filename));
                    
                    postingLists.add(postings);
                    put(processedWord, term);
                }
                
                // another occurrence of the word in the file
                else {
                    postings.updatePostings(filename);
                    term.incrementDocFrequency();
                }
            }
        }
        
        return size;
    }
}
