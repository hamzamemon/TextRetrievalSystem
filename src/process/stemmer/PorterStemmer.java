package process.stemmer;

import process.stemmer.suffixes.Step2Suffixes;
import process.stemmer.suffixes.Step3Suffixes;
import process.stemmer.suffixes.Step4Suffixes;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Performs the Snowball stemming algorithm (Porter2)
 */
public final class PorterStemmer {
    
    private static final Set<String> EXCEPTIONAL_FORMS_AFTER_STEP_1A = new HashSet<>(Arrays.asList("inning", "outing",
            "canning", "herring", "earring", "proceed", "exceed", "succeed"));
    private static final Step2Suffixes[] STEP_2_SUFFIXES = Step2Suffixes.values();
    private static final Step3Suffixes[] STEP_3_SUFFIXES = Step3Suffixes.values();
    private static final Step4Suffixes[] STEP_4_SUFFIXES = Step4Suffixes.values();
    private static final Pattern VOWEL = Pattern.compile("[aeiouy]");
    private static final Pattern VOWEL_CONSONANT = Pattern.compile("[aeiouy][^aeiouy]");
    private static final Pattern CVC = Pattern.compile(".*[^aeiouy][aeiouy][^aeiouywxY]$");
    private static Map<String, String> stems = new HashMap<>();
    private static HashMap<String, String> exceptionalForms;
    
    private PorterStemmer() {
    }
    
    /**
     * Allocates the exceptionalForms map with exceptions to the algorithm
     */
    public static void createMap() {
        String[] keys = {"skis", "skies", "dying", "lying", "tying", "idly", "gently", "ugly", "early", "only",
                "singly", "sky", "news", "howe", "atlas", "cosmos", "bias", "andes", "generate", "generates",
                "generated", "generating", "general", "generally", "generic", "generically", "generous",
                "generously", "commune", "communing", "communism", "communities", "community"};
        String[] values = {"ski", "sky", "die", "lie", "tie", "idl", "gentl", "ugli", "earli", "onli", "singl",
                "sky", "news", "howe", "atlas", "cosmos", "bias", "andes", "generat", "generat", "generat",
                "generat", "general", "general", "generic", "generic", "generous", "generous", "commune", "commune",
                "communism", "communiti", "communiti"};
        
        exceptionalForms = new HashMap<>(keys.length);
        for(int i = 0, length = keys.length; i < length; i++) {
            exceptionalForms.put(keys[i], values[i]);
        }
    }
    
    /**
     * Main method to stem
     *
     * @param termS the word to stem
     *
     * @return the stemmed version
     */
    public static String stem(String termS) {
        if(termS.length() <= 2) {
            return termS;
        }
        
        String stem;
        
        if(stems.containsKey(termS)) {
            stem = stems.get(termS);
        }
        else {
            StringBuilder term = new StringBuilder(termS);
            if(term.charAt(0) == '\'') {
                term = term.deleteCharAt(0);
            }
            String termToString = term.toString();
            if(exceptionalForms.containsKey(termToString)) {
                stem = exceptionalForms.get(termToString);
            }
            else {
                WordMethods.setCapitalYs(term);
                doStep0(term);
                if(term.length() != 0) {
                    doStep1a(term);
                    if(!EXCEPTIONAL_FORMS_AFTER_STEP_1A.contains(term.toString())) {
                        doStep1bc(term);
                        doStep2To4(term);
                        doStep5(term);
                    }
                }
                
                termToString = term.toString();
                stem = termToString.replace("Y", "y");
            }
            
            stems.put(termS, stem);
        }
        
        return stem;
    }
    
    private static void doStep0(StringBuilder term) {
        String termS = term.toString();
        
        if(termS.endsWith("'s'")) {
            term = term.delete(term.length() - 3, term.length());
        }
        else if(termS.endsWith("'s")) {
            term = term.delete(term.length() - 2, term.length());
        }
        else if(termS.charAt(termS.length() - 1) == '\'') {
            term = term.deleteCharAt(term.length() - 1);
        }
    }
    
    private static void doStep1a(StringBuilder term) {
        String termS = term.toString();
        
        if(termS.endsWith("sses")) {
            // *sses -> *ss
            term = term.delete(term.length() - 2, term.length());
        }
        else if(termS.endsWith("ies") || termS.endsWith("ied")) {
            if(termS.length() >= 5) {
                term = term.delete(term.length() - 2, term.length());
            }
            else {
                term = term.deleteCharAt(term.length() - 1);
            }
        }
        else if(termS.endsWith("ss") || termS.endsWith("us")) {
            // *ss -> *ss
        }
        else if(termS.charAt(termS.length() - 1) == 's') {
            // *s -> *
            for(int i = 0, length = termS.length(); i < length - 2; i++) {
                if("aeiouy".indexOf(termS.charAt(i)) >= 0) {
                    term = term.deleteCharAt(term.length() - 1);
                    break;
                }
            }
        }
    }
    
    private static void doStep1bc(StringBuilder term) {
        String termS = term.toString();
        
        if(termS.endsWith("eed") || termS.endsWith("eedly")) {
            int indexOfSuffix = termS.lastIndexOf("eed");
            String prefix = termS.substring(0, indexOfSuffix);
            if(getMeasure(prefix) > 0) {
                // *eed, *eedly -> *ee
                term = term.delete(indexOfSuffix + 2, term.length());
            }
        }
        else {
            String[] suffixes = {"ed", "edly", "ing", "ingly"};
            for(String suffix : suffixes) {
                if(termS.endsWith(suffix)) {
                    String prefix = termS.substring(0, termS.length() - suffix.length());
                    Matcher matcher = VOWEL.matcher(prefix);
                    if(matcher.find()) {
                        term = term.delete(termS.length() - suffix.length(), term.length());
                        step1BPart2(term);
                    }
                }
            }
        }
        
        
        if(term.charAt(term.length() - 1) == 'y' || term.charAt(term.length() - 1) == 'Y') {
            if(term.length() >= 3 && "aeiouy".indexOf(term.charAt(term.length() - 2)) < 0) {
                term = term.replace(term.length() - 1, term.length(), "i");
            }
        }
    }
    
    private static void step1BPart2(StringBuilder term) {
        String termS = term.toString();
        
        if(termS.endsWith("at") || termS.endsWith("bl") || termS.endsWith("iz")) {
            term = term.append('e');
        }
        else if(WordMethods.endsWithDouble(term)) {
            term = term.deleteCharAt(term.length() - 1);
        }
        else if(getMeasure(termS) == 1 && endsWithShortSyllable(termS)) {
            term = term.append('e');
        }
    }
    
    private static void doStep2To4(StringBuilder term) {
        // Step 2
        String termS = term.toString();
        for(Step2Suffixes step2Suffix : STEP_2_SUFFIXES) {
            String suffix = step2Suffix.toString();
            if(termS.endsWith(suffix)) {
                if("li".equals(suffix)) {
                    // ternary operator to handle special case where "li" is a word
                    char liEnding = term.length() >= 3 ? termS.charAt(termS.length() - 3) : 'c';
                    if(liEnding == 'c' || liEnding == 'd' || liEnding == 'e' || liEnding == 'g' || liEnding == 'h' ||
                            liEnding == 'k' || liEnding == 'm' || liEnding == 'n' || liEnding == 'r' || liEnding == 't') {
                        removeSuffix(term, suffix, step2Suffix.getSuffix());
                    }
                }
                else if("ogi".equals(suffix)) {
                    char ogiEnding = termS.charAt(termS.length() - 4);
                    if(ogiEnding == 'l') {
                        removeSuffix(term, suffix, step2Suffix.getSuffix());
                    }
                }
                else {
                    removeSuffix(term, suffix, step2Suffix.getSuffix());
                }
                break;
            }
        }
        
        // Step 3
        termS = term.toString();
        if(termS.endsWith("ative")) {
            String prefix = termS.substring(0, termS.length() - 5);
            if(getMeasure(prefix) >= 2) {
                term = term.replace(term.length() - 5, term.length(), "");
                termS = term.toString();
            }
        }
        for(Step3Suffixes step3Suffix : STEP_3_SUFFIXES) {
            String suffix = step3Suffix.toString();
            if(termS.endsWith(suffix)) {
                removeSuffix(term, suffix, step3Suffix.getSuffix());
                break;
            }
        }
        
        // Step 4
        termS = term.toString();
        
        for(Step4Suffixes step4Suffix : STEP_4_SUFFIXES) {
            String suffix = step4Suffix.toString();
            if(termS.endsWith(suffix)) {
                String prefix = termS.substring(0, termS.length() - suffix.length());
                if(getMeasure(prefix) >= 2) {
                    int lastIndex = prefix.length();
                    if("ion".equals(suffix)) {
                        if(prefix.charAt(prefix.length() - 1) == 's' || prefix.charAt(prefix.length() - 1) == 't') {
                            term = term.replace(lastIndex, term.length(), step4Suffix.getSuffix());
                            break;
                        }
                    }
                    else {
                        term = term.replace(lastIndex, term.length(), step4Suffix.getSuffix());
                        break;
                    }
                }
                break;
            }
        }
    }
    
    private static void doStep5(StringBuilder term) {
        String termS = term.toString();
        
        if(termS.charAt(termS.length() - 1) == 'e') {
            String withoutE = termS.substring(0, termS.length() - 1);
            int m = getMeasure(withoutE);
            if(m >= 2) {
                term = term.deleteCharAt(term.length() - 1);
            }
            else if(m == 1) {
                if(!endsWithShortSyllable(withoutE)) {
                    term = term.deleteCharAt(term.length() - 1);
                }
            }
        }
        
        else if(getMeasure(termS) >= 2 && termS.endsWith("ll")) {
            term = term.deleteCharAt(term.length() - 1);
        }
    }
    
    private static boolean endsWithShortSyllable(String termS) {
        if(termS.length() == 2) {
            Matcher matcher = VOWEL_CONSONANT.matcher(termS);
            return matcher.find();
        }
        Matcher matcher = CVC.matcher(termS);
        return matcher.find();
    }
    
    private static void removeSuffix(StringBuilder term, String suffix, String replacement) {
        String prefix = term.substring(0, term.length() - suffix.length());
        if(getMeasure(prefix) > 0) {
            int lastIndex = prefix.length();
            term = term.replace(lastIndex, term.length(), replacement);
        }
    }
    
    private static String getLetterTypes(String word) {
        StringBuilder letterTypes = new StringBuilder(word.length());
        for(int i = 0, length = word.length(); i < length; i++) {
            char letter = word.charAt(i);
            char letterType = WordMethods.getLetterType(letter);
            if(letterTypes.length() == 0 || letterTypes.charAt(letterTypes.length() - 1) != letterType) {
                letterTypes.append(letterType);
            }
        }
        
        return letterTypes.toString();
    }
    
    private static int getMeasure(String word) {
        String letterTypes = getLetterTypes(word);
        if(letterTypes.length() < 2) {
            return 0;
        }
        if(letterTypes.charAt(0) == 'C') {
            return letterTypes.length() - 1 >> 1;
        }
        return letterTypes.length() >> 1;
    }
}
