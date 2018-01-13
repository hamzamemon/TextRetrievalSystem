package index;

import process.Preprocess;
import process.stemmer.PorterStemmer;

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
    
    /**
     * Instantiates a new Term index.
     *
     * @param documentIndex the HashMap of the index for the Documents
     * @param postingLists  the PostingLists
     */
    public TermIndex(DocumentIndex documentIndex, PostingLists postingLists) {
        File dir = new File("res/data/");
        File[] files = dir.listFiles();
        Arrays.sort(files);
        PorterStemmer.createMap();
        
        for(File file : files) {
            String contents = null;
            try(Scanner scanner = new Scanner(file).useDelimiter("\\Z")) {
                contents = scanner.next();
            }
            catch(FileNotFoundException e) {
                e.printStackTrace();
            }
            
            String name = file.getName();
            name = name.substring(0, name.length() - 4);
            int size = getSize(name, contents, postingLists);
            documentIndex.addDocument(name, size);
        }
    }
    
    /**
     * This method returns the number of words in the file that pass the 'Preprocessing' class. It loops through every
     * word in 'contents' and 'Preprocess'es each of them
     *
     * @param name         the filename
     * @param contents     the contents of the file
     * @param postingLists the PostingLists
     *
     * @return the number of words in the file after processing 'contents'
     */
    private int getSize(String name, String contents, PostingLists postingLists) {
        int location = 0;
        int size = 0;
        
        StringTokenizer tokenizer = new StringTokenizer(contents);
        while(tokenizer.hasMoreTokens()) {
            location++;
            
            String word = tokenizer.nextToken();
            String processedWord = Preprocess.process(word);
            
            if(!processedWord.isEmpty()) {
                size++;
                
                Term term = containsKey(processedWord) ? get(processedWord) : new Term(word);
                PostingList postingList = postingLists.getList(term);
                
                // if first word in the file
                if(postingList.isEmpty()) {
                    postingList.add(new Posting(name, location));
                    
                    postingLists.add(postingList);
                    put(processedWord, term);
                }
                
                // another occurrence of the word in the file
                else {
                    postingList.updatePostings(name, location);
                    term.incrementDocFrequency();
                }
            }
        }
        
        return size;
    }
}
