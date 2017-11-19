package index;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

/**
 * This class creates the indices (HashMaps) and the Posting List and writes them to a file
 *
 * @author hamza
 */
public class Invert {
    
    public static final String TERMS = "terms.ser";
    public static final String DOCS = "docs.ser";
    public static final String LIST = "list.ser";
    
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     *
     * @throws IOException an I/O exception has occurred
     */
    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        
        PostingLists postingLists = new PostingLists();
        DocumentIndex documentIndex = new DocumentIndex();
        TermIndex termIndex = new TermIndex(documentIndex, postingLists);
        
        writeObjects(termIndex, documentIndex, postingLists);
        
        System.out.println("termIndex.size() = " + termIndex.size());
        
        long end = System.nanoTime();
        long diff = end - start;
        System.out.println("diff = " + diff / 1_000_000_000.0);
    }
    
    /**
     * Writes objects.
     *
     * @param termIndex     the HashMap of the index for the terms
     * @param documentIndex the HashMap of the index for the documents
     * @param postingLists  the ArrayList of ArrayList of Postings
     *
     * @throws IOException an I/O exception has occurred
     */
    private static void writeObjects(TermIndex termIndex, DocumentIndex documentIndex, PostingLists postingLists)
            throws IOException {
        writeObject(termIndex, TERMS);
        writeObject(documentIndex, DOCS);
        writeObject(postingLists, LIST);
    }
    
    private static void writeObject(Object object, String filename) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(filename, "rw");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(randomAccessFile.getFD()));
        oos.writeObject(object);
    }
}
