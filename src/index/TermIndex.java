package index;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

import process.Preprocess;
import process.stemmer.PorterStemmer;

/**
 * This class represents the HashMap of the index for the terms
 */
public class TermIndex extends TreeMap<String, Term> {
    
    private static final long serialVersionUID = -4668144947469533622L;
    
    /**
     * Instantiates a new Term index.
     *
     * @param documentIndex the HashMap of the index for the Documents
     * @param postingLists  the PostingLists
     */
    public TermIndex(DocumentIndex documentIndex, PostingLists postingLists){
        File dir = new File("res/data/");
        File[] files = dir.listFiles();
        Arrays.sort(files);
        PorterStemmer.createMap();

        for(int i = 0, length = files.length; i < length; i++){
            File file = files[i];
            String contents = null;
            try(Scanner scanner = new Scanner(file).useDelimiter("\\Z")){
                contents = scanner.next();
            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            }

//            String name = file.getName();
  //          name = name.substring(0, name.length() - 4);
            int size = getSize(i, contents, postingLists);
            documentIndex.addDocument(i, size);
        }
    }

    /**
     * This method returns the number of words in the file that pass the 'Preprocessing' class. It loops through every
     * word in 'contents' and 'Preprocess'es each of them
     *
     * @param number       the document number
     * @param contents     the contents of the file
     * @param postingLists the PostingLists
     *
     * @return the number of words in the file after processing 'contents'
     */
    private int getSize(int number, String contents, PostingLists postingLists){
        int size = 0;

        StringTokenizer tokenizer = new StringTokenizer(contents);
        while(tokenizer.hasMoreTokens()){
            String word = tokenizer.nextToken();
            String processedWord = Preprocess.process(word);

            if(!processedWord.isEmpty()){
                size++;

                Term term = containsKey(processedWord) ? get(processedWord) : new Term();
                PostingList postingList = postingLists.getList(term);

                // if first word in the file
                if(postingList.isEmpty()){
                    postingList.add(new Posting(number));

                    postingLists.add(postingList);
                    put(processedWord, term);
                }

                // another occurrence of the word in the file
                else{
                    postingList.updatePostings(number);
                    term.incrementDocFrequency();
                }
            }
        }

        return size;
    }
}
