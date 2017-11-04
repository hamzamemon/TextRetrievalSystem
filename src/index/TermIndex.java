package index;

import process.Preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * This class represents the HashMap of the index for the terms
 */
public class TermIndex extends TreeMap<String, IDF> {

    /**
     * Instantiates a new Term index.
     *
     * @param documentIndex the HashMap of the index for the Documents
     *
     * @throws FileNotFoundException a class could not found in the folder
     */
    public TermIndex(DocumentIndex documentIndex, PostingLists postingLists) throws FileNotFoundException{
        File dir = new File("res/data/");
        File[] files = dir.listFiles();
        Arrays.sort(files);

        for(File file : files){
            Scanner scanner = new Scanner(file).useDelimiter("\\Z");
            String contents = scanner.next();
            scanner.close();

            String name = file.getName();
            int size = getSize(name, contents, postingLists);
            documentIndex.addDocument(name, size);
        }
    }

    /**
     * This method returns the number of words in the file that pass the 'Preprocessing' class.
     * It loops through every word in 'contents' and 'Preprocess'es each of them
     *
     * @param name     the filename
     * @param contents the contents of the file
     *
     * @return the number of words in the file after processing 'contents'
     */
    private int getSize(String name, String contents, PostingLists postingLists){
        int location = 0;
        int size = 0;

        StringTokenizer tokenizer = new StringTokenizer(contents);
        while(tokenizer.hasMoreTokens()){
            location++;

            String word = tokenizer.nextToken();
            String processedWord = Preprocess.process(word);

            if(!processedWord.isEmpty()){
                IDF df = containsKey(processedWord) ? get(processedWord) : new IDF();
                int index = df.getIndex();

                size++;
                PostingList postingList = null;

                try{
                    postingList = postingLists.get(index);
                }
                catch(IndexOutOfBoundsException e){
                }

                if(postingList == null){
                    postingList = new PostingList();
                    postingList.add(new Posting(name, location));

                    postingLists.add(postingList);
                    put(processedWord, df);
                }

                else{
                    postingList.updatePostings(name, location, df);
                }
            }
        }

        return size;
    }
}