package search;

import index.DocumentIndex;
import index.Invert;
import index.Posting;
import index.PostingList;
import index.PostingLists;
import index.TermIndex;
import query.BooleanQuery;
import query.QueryParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

/**
 * This class reads in the indices that were created in the "Invert" class and allows the user to search for terms with
 * Booleans within them
 */
public class Driver {
    
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     *
     * @throws IOException            an I/O exception has occurred
     * @throws ClassNotFoundException a class could not found in the folder
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        long start = System.nanoTime();
        
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Invert.TERMS));
        TermIndex termIndex = (TermIndex) ois.readObject();
        
        ObjectInputStream ois2 = new ObjectInputStream(new FileInputStream(Invert.DOCS));
        DocumentIndex documentIndex = (DocumentIndex) ois2.readObject();
        
        ObjectInputStream ois3 = new ObjectInputStream(new FileInputStream(Invert.LIST));
        PostingLists postingLists = (PostingLists) ois3.readObject();
        
        long end = System.nanoTime();
        long diff = end - start;
        System.out.println("diff = " + diff / 1_000_000_000.0);
        
        ois.close();
        ois2.close();
        ois3.close();
        
        String queryTerms = getInput();
        
        while(!"EXIT".equals(queryTerms)) {
            start = System.nanoTime();
            
            BooleanQuery query = QueryParser.parse(queryTerms);
            PostingList postings = BooleanSearcher.search(query, termIndex, postingLists);
            
            end = System.nanoTime();
            diff = end - start;
            System.out.println("diff = " + diff / 1_000_000_000.0);
            
            if(postings == null) {
                System.out.println('\'' + queryTerms + "' isn't in any of the files.\n");
            }
            
            else {
                writeInfo(query, queryTerms, postings);
            }
            
            
            queryTerms = getInput();
        }
    }
    
    private static String getInput() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a term to search for (EXIT to quit): ");
        return scanner.nextLine();
    }
    
    /**
     * This method writes the Postings into a table in a file that contain the word
     *
     * @param queryTerms the word to search for
     * @param postings   the list of Postings that is the final result of the query
     */
    private static void writeInfo(BooleanQuery query, String queryTerms, PostingList postings)
            throws IOException {
        queryTerms = queryTerms.replace(" ", "_");
        boolean empty = query.getInputB().isEmpty();
        
        PrintWriter pw = new PrintWriter(queryTerms + ".txt");
        pw.println("The word that was entered was '" + queryTerms + "'.");
        pw.println("There are a total of " + postings.size() + " documents that contain the word");
        pw.println("Here are the documents that contain '" + queryTerms + "'.\n");
        
        for(int i = 0, length = postings.size(); i < length; i++) {
            Posting posting = postings.get(i);
            
            int number = posting.getNumber();
            pw.println("Doc number: " + number);
            if(empty) {
                pw.println("Term frequency: " + posting.getFrequency());
            }
    
            pw.println("---------------\n");
        }
        
        pw.close();
        
        System.out.println("Created " + queryTerms + ".txt");
    }
}
