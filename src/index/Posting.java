package index;

import java.io.Serializable;
import java.util.Comparator;

/**
 * This class creates a Posting, which is when a term appears in the file.
 * It contains the filename, the frequency of said term in the file
 * and the first location of the term in the file.
 *
 * @author hamza
 */
public class Posting implements Serializable {

    public static final Comparator<Posting> COMPARATOR = (posting, t1) -> {
        String name = posting.name;
        String name2 = t1.name;

        return name.compareTo(name2);
    };

    private String name;
    private int frequency;
    private int firstLocation;

    /**
     * Instantiates a new Posting.
     *
     * @param name          the filename
     * @param firstLocation the first location of the term in the file
     */
    public Posting(String name, int firstLocation){
        this.name = name;
        this.firstLocation = firstLocation;
        frequency = 1;
    }

    /**
     * Gets the frequency of the term in the file as an Integer
     *
     * @return the frequency
     */
    public int getFrequency(){
        return frequency;
    }

    /**
     * Gets the first location of the term in the file as an Integer.
     *
     * @return the first location
     */
    public int getFirstLocation(){
        return firstLocation;
    }

    public void incrementFrequency(){
        frequency++;
    }

    /**
     * Gets the filename as a String
     *
     * @return the filename
     */
    public String getName(){
        return name;
    }
}