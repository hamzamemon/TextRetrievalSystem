package process.stemmer;

public final class WordMethods {
    
    private WordMethods() {
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
    
    public static boolean endsWithDouble(StringBuilder term) {
        if(term.length() >= 2 && !isVowel(term.charAt(term.length() - 1))) {
            char last = term.charAt(term.length() - 1);
            char secondLast = term.charAt(term.length() - 2);
            return last == secondLast;
        }
        
        return false;
    }
    
    public static void setCapitalYs(StringBuilder term) {
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
    }
}
