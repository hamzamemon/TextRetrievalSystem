package search;

import index.Document;
import index.DocumentIndex;
import index.Invert;
import index.Posting;
import index.PostingList;
import index.PostingLists;
import index.Term;
import index.TermIndex;
import process.Preprocess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * This class reads in the indices that were created in the "Invert" class and allows the user to search for terms with
 * Booleans within them
 */
public class VSMTester {
    
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     *
     * @throws IOException            an I/O exception has occurred
     * @throws ClassNotFoundException a class could not found in the folder
     */
    public static void main(String... args) throws IOException, ClassNotFoundException {
        long start = System.nanoTime();
        
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Invert.TERMS));
        TermIndex termIndex = (TermIndex) ois.readObject();
        
        ObjectInputStream ois2 = new ObjectInputStream(new FileInputStream(Invert.DOCS));
        DocumentIndex documentIndex = (DocumentIndex) ois2.readObject();
        
        ObjectInputStream ois3 = new ObjectInputStream(new FileInputStream(Invert.LIST));
        PostingLists postingLists = (PostingLists) ois3.readObject();
        
        long end = System.nanoTime();
        long dif = end - start;
        System.out.println("dif = " + dif / 1_000_000_000.0);
        
        ois.close();
        ois2.close();
        ois3.close();
        
        String queryTerms = getInput();
        
        while(!"EXIT".equals(queryTerms)) {
            String processedWord = Preprocess.process(queryTerms);
            if(termIndex.containsKey(processedWord)) {
                Term term = termIndex.get(processedWord);
                PostingList postings = postingLists.getList(term);
                
                PriorityQueue<ScoredDocument> queue = new PriorityQueue<>(1000,
                        ScoredDocument.COMPARATOR);
                scoreDocs(postings, documentIndex, queue);
                writeInfo(queryTerms, queue);
            }
            else {
                System.out.println('\'' + queryTerms + "' isn't in any of the files.\n");
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
     * Scores the Documents containing the term
     *
     * @param postings      the postings for the term
     * @param documentIndex the HashMap of filename mapping to Document
     * @param queue         the ScoredDocuments ranked by score
     */
    private static void scoreDocs(PostingList postings, DocumentIndex documentIndex,
                                  PriorityQueue<ScoredDocument> queue) {
        for(Posting posting : postings) {
            Document document = documentIndex.get(posting.getNumber());
            ScoredDocument scoredDocument = new ScoredDocument(document.getNumber());
            
            double score = posting.getWeight() / document.getLength();
            scoredDocument.setScore(score);
            
            if(score > 0) {
                queue.offer(scoredDocument);
            }
        }
    }
    
    /**
     * This method writes the Postings into a table in a file that contain the word
     *
     * @param queryTerms the word to search for
     * @param queue      the ScoredDocuments ranked by score
     */
    private static void writeInfo(String queryTerms, PriorityQueue<ScoredDocument> queue) throws FileNotFoundException {
        try(PrintWriter pw = new PrintWriter(queryTerms + ".txt")) {
            while(!queue.isEmpty()) {
                ScoredDocument scoredDocument = queue.poll();
                
                pw.println(scoredDocument.getNumber() + ' ' + scoredDocument.getScore());
            }
        }
        
        System.out.println("Created " + queryTerms + ".txt");
    }
}
