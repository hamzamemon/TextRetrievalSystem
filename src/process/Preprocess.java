package process;


import java.util.Set;
import java.util.regex.Pattern;

/**
 * This class handles preprocessing for each individual term in every file
 *
 * @author hamza
 */
public final class Preprocess {

    private static final Pattern COMPILE = Pattern.compile("[^a-zA-z]");
    private static final Set<String> STOP_LIST = new StopList();

    /**
     * Process string. by calling each method in the right order
     *
     * @param term the term
     *
     * @return the term after processing
     */
    public static String process(String term){
        if(STOP_LIST.contains(term)){
            term = "";
        }

        else{
            term = removePunctuationAndLowerCase(term);
            term = removeStopWords(term);
        }

        return term;
    }

    /**
     * This method returns a String with anything that is not lower case letters
     *
     * @param term the term
     *
     * @return the term with stuff removed and lower case
     */
    private static String removePunctuationAndLowerCase(String term){
        return COMPILE.matcher(term).replaceAll("").toLowerCase();
    }

    /**
     * This method removes stop words that were generated from the HashSet
     *
     * @param term the term
     *
     * @return the term with stop words removed
     */
    private static String removeStopWords(String term){
        return STOP_LIST.contains(term) ? "" : term;
    }
}