package process.stemmer;

public class PorterStemmer {
    
    
    public static String stem(String term) {
        if(term.length() > 2) {
            Word word = new Word(term);
            term = doStep1(word);
        }
        return term;
    }
    
    private static String doStep1(Word word) {
        String term = word.getTerm();
        
        if(term.isEmpty()) {
            return term;
        }
        // StringBuilder?
        if(term.endsWith("sses")) {
            // sses -> ss
            term = term.substring(0, term.length() - 2);
        }
        else if(term.endsWith("ied") || term.endsWith("ies")) {
            if(term.length() >= 5) {
                // ied or ies -> i
                term = term.substring(0, term.length() - 2);
            }
            else {
                // ied or ies -> ie
                term = term.substring(0, term.length() - 1);
            }
        }
        else if(term.charAt(term.length() - 1) == 's' && !(term.endsWith("us") || term.endsWith("ss"))) {
            boolean vowelNotImmediatelyBeforeS = false;
            for(int i = 0; i < term.length() - 2; i++) {
                String vowels = getVowels(term);
                if(vowels.indexOf(term.charAt(i)) >= 0) {
                    vowelNotImmediatelyBeforeS = true;
                    break;
                }
            }
            
            if(vowelNotImmediatelyBeforeS) {
                term = term.substring(0, term.length() - 1);
            }
        }
        
        String region1 = word.getRegion1();
        if(region1.endsWith("eed") || region1.endsWith("eedly")) {
            term = term.substring(0, term.lastIndexOf("ee") + 2);
        }
        
        return term;
    }
    
    private static String getVowels(String term) {
        // TODO y is  the oppposite of the letter before (anyway -> first y is vowel, second y is consonant)
        return "aeiouy";
    }
}
