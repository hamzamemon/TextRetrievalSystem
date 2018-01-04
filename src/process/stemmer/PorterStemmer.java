package process.stemmer;

import process.stemmer.suffixes.Step2Suffixes;
import process.stemmer.suffixes.Step3Suffixes;
import process.stemmer.suffixes.Step4Suffixes;

import java.util.HashMap;

public class PorterStemmer {
    
    private static HashMap<String, String> stems = new HashMap<>();
    
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
            doStep1(term);
            doStep2To4(term);
            doStep5(term);
            
            stem = term.toString().replace("Y", "y");
            stems.put(termS, stem);
        }
        
        return stem;
    }
    
    private static void doStep1(StringBuilder term) {
        String termS = term.toString();
        if(termS.contains("y")) {
            WordMethods.setCapitalYs(term);
        }
        
        if(termS.endsWith("sses")) {
            // *sses -> *ss
            term = term.delete(term.length() - 2, term.length());
        }
        else if(termS.endsWith("ies")) {
            // *ies -> *i
            term = term.delete(term.length() - 2, term.length());
        }
        else if(termS.endsWith("ss")) {
            // *ss -> *ss
        }
        else if(!termS.isEmpty() && termS.charAt(termS.length() - 1) == 's') {
            // *s -> *
            term = term.deleteCharAt(term.length() - 1);
        }
        
        termS = term.toString();
        
        if(termS.endsWith("eed")) {
            String prefix = termS.substring(0, termS.length() - 3);
            if(getMeasure(prefix) > 0) {
                term = term.deleteCharAt(term.length() - 1);
            }
        }
        else if(termS.endsWith("ed")) {
            String prefix = termS.substring(0, termS.length() - 2);
            if(getLetterTypes(prefix).contains("V")) {
                term = step1BPart2(term, 2);
            }
        }
        else if(termS.endsWith("ing")) {
            String prefix = termS.substring(0, termS.length() - 3);
            if(getLetterTypes(prefix).contains("V")) {
                term = step1BPart2(term, 3);
            }
        }
        
        if(term.charAt(term.length() - 1) == 'y' || term.charAt(term.length() - 1) == 'Y') {
            String withoutY = term.substring(0, term.length() - 1);
            if(getLetterTypes(withoutY).contains("V")) {
                term = term.replace(term.length() - 1, term.length(), "i");
            }
        }
    }
    
    private static StringBuilder step1BPart2(StringBuilder term, int numCharsToDelete) {
        term = term.delete(term.length() - numCharsToDelete, term.length());
        String termS = term.toString();
        
        if(termS.endsWith("at") || termS.endsWith("bl") || termS.endsWith("iz")) {
            term = term.append('e');
        }
        else if(WordMethods.endsWithDouble(term)) {
            if(termS.charAt(termS.length() - 1) != 'l' && termS.charAt(termS.length() - 1) != 's' && termS.charAt(termS.length() - 1) != 'z') {
                term = term.deleteCharAt(term.length() - 1);
            }
        }
        else if(getMeasure(termS) == 1 && isCVC(termS)) {
            term = term.append('e');
        }
        
        return term;
    }
    
    private static void doStep2To4(StringBuilder term) {
        // Step 2
        Step2Suffixes[] step2Suffixes = Step2Suffixes.values();
        
        String termS = term.toString();
        for(Step2Suffixes step2Suffix : step2Suffixes) {
            String suffix = step2Suffix.toString();
            if(termS.endsWith(suffix)) {
                removeSuffix(term, suffix, step2Suffix.getSuffix());
                break;
            }
        }
        
        // Step 3
        Step3Suffixes[] step3Suffixes = Step3Suffixes.values();
        
        termS = term.toString();
        for(Step3Suffixes step3Suffix : step3Suffixes) {
            String suffix = step3Suffix.toString();
            if(termS.endsWith(suffix)) {
                removeSuffix(term, suffix, step3Suffix.getSuffix());
                break;
            }
        }
        
        // Step 4
        Step4Suffixes[] step4Suffixes = Step4Suffixes.values();
        termS = term.toString();
        
        for(Step4Suffixes step4Suffix : step4Suffixes) {
            String suffix = step4Suffix.toString();
            if(termS.endsWith(suffix)) {
                String prefix = termS.substring(0, termS.length() - suffix.length());
                int lastIndex = prefix.length();
                if(getMeasure(prefix) > 1) {
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
        
        if(!termS.isEmpty() && termS.charAt(termS.length() - 1) == 'e') {
            String withoutE = termS.substring(0, termS.length() - 1);
            int m = getMeasure(withoutE);
            if(m > 1) {
                term = term.deleteCharAt(term.length() - 1);
            }
            else if(m == 1) {
                if(!isCVC(withoutE)) {
                    term = term.deleteCharAt(term.length() - 1);
                }
            }
        }
        
        termS = term.toString();
        
        if(getMeasure(termS) > 1 && termS.endsWith("ll")) {
            term = term.deleteCharAt(term.length() - 1);
        }
    }
    
    private static boolean isCVC(String termS) {
        if(termS.length() < 3) {
            return false;
        }
        char last = termS.charAt(termS.length() - 1);
        if(last == 'w' || last == 'x' || last == 'Y') {
            return false;
        }
        char secondLast = termS.charAt(termS.length() - 2);
        char thirdLast = termS.charAt(termS.length() - 3);
        
        return !WordMethods.isVowel(thirdLast) && WordMethods.isVowel(secondLast) && !WordMethods.isVowel(last);
    }
    
    private static void removeSuffix(StringBuilder term, String suffix, String replacement) {
        String prefix = term.substring(0, term.length() - suffix.length());
        int lastIndex = prefix.length();
        if(getMeasure(prefix) > 0) {
            term = term.replace(lastIndex, term.length(), replacement);
        }
    }
    
    private static String getLetterTypes(String word) {
        StringBuilder letterTypes = new StringBuilder(word.length());
        for(int i = 0; i < word.length(); i++) {
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
            return (letterTypes.length() - 1) / 2;
        }
        return letterTypes.length() / 2;
    }
}
