package process.stemmer;

public class WordMethods {
    
    private StringBuilder term;
    
    public WordMethods(String termS) {
        term = new StringBuilder(termS);
        if(termS.contains("y")) {
            term = setCapitalYs(term);
        }
    }
    
    public static char getLetterType(char letter) {
        switch(letter) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
            case 'y':
                return 'V';
            default:
                return 'C';
        }
    }
    
    public static boolean isVowel(char letter) {
        return getLetterType(letter) == 'V';
    }
    
    public StringBuilder getTerm() {
        return term;
    }
    
    private StringBuilder setCapitalYs(StringBuilder term) {
        if(term.charAt(0) == 'y') {
            term = term.replace(0, 1, "Y");
        }
        for(int i = 1; i < term.length(); i++) {
            if(term.charAt(i) == 'y') {
                if(isVowel(term.charAt(i - 1))) {
                    term = term.replace(i, i + 1, "Y");
                }
            }
        }
        return term;
    }
    
    public boolean endsWithDouble() {
        if(term.length() >= 2 && !isVowel(term.charAt(term.length() - 1))) {
            char last = term.charAt(term.length() - 1);
            char secondLast = term.charAt(term.length() - 2);
            return last == secondLast;
        }
        
        return false;
    }
}
