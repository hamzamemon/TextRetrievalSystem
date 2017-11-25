package process.stemmer;

import process.stemmer.suffixes.Step2Suffixes;
import process.stemmer.suffixes.Step3Suffixes;
import process.stemmer.suffixes.Step4Suffixes;

import java.util.HashMap;

public class PorterStemmer {
    
    private static HashMap<String, String> stems = new HashMap<>();
    
    /*
   termIndex.size() = 77020
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
            Word word = new Word(termS);
            word = doStep1(word);
            word = doStep2To5(word);
            
            StringBuilder term = word.getTerm();
            stem = term.toString().replace("Y", "y");
            stems.put(termS, stem);
        }
        
        return stem;
    }
    
    private static Word doStep2To5(Word word) {
        // Step 2
        Step2Suffixes[] step2Suffixes = Step2Suffixes.values();
        
        StringBuilder term = word.getTerm();
        String region1 = word.getRegion1();
        for(Step2Suffixes step2Suffix : step2Suffixes) {
            String suffix = step2Suffix.toString();
            if(region1.endsWith(suffix)) {
                term = term.replace(term.lastIndexOf(suffix), term.length(), step2Suffix.getSuffix());
                break;
            }
        }
        word.setTerm(term);
        
        // Step 3
        String region2 = word.getRegion2();
        if(region2.endsWith("ative")) {
            term = term.delete(term.length() - 5, term.length());
        }
        word.setTerm(term);
        
        Step3Suffixes[] step3Suffixes = Step3Suffixes.values();
        
        region1 = word.getRegion1();
        for(Step3Suffixes step3Suffix : step3Suffixes) {
            String suffix = step3Suffix.toString();
            if(region1.endsWith(suffix)) {
                term = term.replace(term.lastIndexOf(suffix), term.length(), step3Suffix.getSuffix());
                break;
            }
        }
        
        word.setTerm(term);
        
        // Step 4
        Step4Suffixes[] step4Suffixes = Step4Suffixes.values();
        
        region2 = word.getRegion2();
        for(Step4Suffixes step4Suffix : step4Suffixes) {
            String suffix = step4Suffix.toString();
            if(region2.endsWith(suffix)) {
                term = term.replace(term.lastIndexOf(suffix), term.length(), step4Suffix.getSuffix());
                break;
            }
        }
        
        word.setTerm(term);
        
        // Step 5
        region1 = word.getRegion1();
        region2 = word.getRegion2();
        
        if(region2.endsWith("e") || (region1.endsWith("e") && !region1.endsWith(word.getShortSyllable() + 'e')) || region2.endsWith("ll")) {
            term = term.deleteCharAt(term.length() - 1);
        }
        
        word.setTerm(term);
        return word;
    }
    
    private static Word doStep1(Word word) {
        StringBuilder term = word.getTerm();
        String termS = term.toString();
        
        if(term.length() == 0) {
            return word;
        }
        if(termS.endsWith("sses")) {
            // sses -> ss
            term = term.delete(term.length() - 2, term.length());
        }
        else if(termS.endsWith("ied") || termS.endsWith("ies")) {
            if(term.length() >= 5) {
                // ied or ies -> i
                term = term.delete(term.length() - 2, term.length());
            }
            else {
                // ied or ies -> ie
                term = term.deleteCharAt(term.length() - 1);
            }
        }
        else if(term.charAt(term.length() - 1) == 's' && !(termS.endsWith("us") || termS.endsWith("ss"))) {
            if(hasVowelBeforeLastKLetter(term, 2)) {
                term = term.deleteCharAt(term.length() - 1);
            }
        }
        
        word.setTerm(term);
        termS = term.toString();
        
        String region1 = word.getRegion1();
        if(region1.endsWith("eed") || region1.endsWith("eedly")) {
            term = term.delete(term.lastIndexOf("ee") + 2, term.length());
        }
        else if(termS.endsWith("ed") || termS.endsWith("edly")) {
            int lastIndex = term.lastIndexOf("ed");
            if(hasVowelBeforeLastKLetter(term, term.length() - lastIndex)) {
                term = step1BPart2(word, lastIndex);
            }
        }
        else if(termS.endsWith("ing") || termS.endsWith("ingly")) {
            int lastIndex = term.lastIndexOf("ing");
            if(hasVowelBeforeLastKLetter(term, term.length() - lastIndex)) {
                term = step1BPart2(word, lastIndex);
            }
        }
        
        word.setTerm(term);
        
        if(term.charAt(term.length() - 1) == 'y' || term.charAt(term.length() - 1) == 'Y') {
            if(term.length() > 2 && "aeiouy".indexOf(term.charAt(term.length() - 2)) == -1) {
                term = term.replace(term.length() - 1, term.length(), "i");
            }
        }
        
        word.setTerm(term);
        return word;
    }
    
    private static StringBuilder step1BPart2(Word word, int lastIndex) {
        StringBuilder term = word.getTerm();
        
        term = term.delete(lastIndex, term.length());
        String termS = term.toString();
        
        word.setTerm(term);
        
        if(term.length() == 0) {
            return term;
        }
        if(termS.endsWith("at") || termS.endsWith("bl") || termS.endsWith("iz")) {
            term = term.append('e');
        }
        else if(word.endsWithDouble()) {
            term = term.deleteCharAt(term.length() - 1);
        }
        else if(word.isShort()) {
            term = term.append('e');
        }
        
        return term;
    }
    
    private static boolean hasVowelBeforeLastKLetter(StringBuilder term, int k) {
        boolean containsVowel = false;
        for(int i = 0; i < term.length() - k; i++) {
            if("aeiouy".indexOf(term.charAt(i)) >= 0) {
                containsVowel = true;
                break;
            }
        }
        
        return containsVowel;
    }
}
