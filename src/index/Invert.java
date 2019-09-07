package index;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

/**
 * This class creates the indices (HashMaps) and the Posting List and writes them to a file
 */
public class Invert {
    
    public static final String TERMS = "terms.ser";
    public static final String DOCS = "docs.ser";
    public static final String LIST = "list.ser";
    
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        long start = System.nanoTime();
        
        PostingLists postingLists = new PostingLists();
        DocumentIndex documentIndex = new DocumentIndex();
        TermIndex termIndex = new TermIndex(documentIndex, postingLists);
        
        // setDocLengths(termIndex, documentIndex, postingLists);
        // writeObjects(termIndex, documentIndex, postingLists);
        
        long end = System.nanoTime();
        long diff = end - start;
        
        System.out.println("termIndex.size() = " + termIndex.size());
        System.out.println("diff = " + diff / 1_000_000_000.0);
    }
    
    /**
     * Sets the document lengths for all documents
     *
     * @param termIndex     the HashMap of the index for the terms
     * @param documentIndex the HashMap of the index for the documents
     * @param postingLists  the ArrayList of ArrayList of Postings
     */
    private static void setDocLengths(TermIndex termIndex, DocumentIndex documentIndex, PostingLists postingLists) {
        int N = documentIndex.size();
        
        for(Term term : termIndex.values()) {
            term.setLoggedIdf(N);
            PostingList list = postingLists.getList(term);
            
            for(Posting posting : list) {
                double loggedTf = posting.getLoggedTf();
                double weight = loggedTf * term.getLoggedIdf();
                posting.setWeight(weight);
                
                Document document = documentIndex.get(posting.getNumber());
                document.addLength(weight * weight);
            }
        }
        
        for(Document document : documentIndex.values()) {
            document.setLength(Math.sqrt(document.getLength()));
        }
    }
    
    /**
     * Writes objects.
     *
     * @param termIndex     the HashMap of the index for the terms
     * @param documentIndex the HashMap of the index for the documents
     * @param postingLists  the ArrayList of ArrayList of Postings
     */
    private static void writeObjects(TermIndex termIndex, DocumentIndex documentIndex,
                                     PostingLists postingLists) {
        writeObject(termIndex, TERMS);
        writeObject(documentIndex, DOCS);
        writeObject(postingLists, LIST);
    }
    
    /**
     * Writes one object
     *
     * @param object   the object to write
     * @param filename the file to write the object to
     */
    private static void writeObject(Object object, String filename) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(filename, "rw");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(randomAccessFile.getFD()));
            oos.writeObject(object);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
