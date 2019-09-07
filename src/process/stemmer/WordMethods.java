package process.stemmer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public final class WordMethods {

    private static final Pattern DOUBLES = Pattern.compile(".*(bb|dd|ff|gg|mm|nn|pp|rr|tt)$");
    private static Map<String, StringBuilder> mapCapitalYs = new HashMap<>();

    private WordMethods(){
    }

    public static char getLetterType(char letter){
        switch(letter){
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

    public static boolean isVowel(char letter){
        return getLetterType(letter) == 'V';
    }

    public static boolean endsWithDouble(String termS){
        return DOUBLES.matcher(termS).matches();
    }

    public static void setCapitalYs(StringBuilder term){
        String termS = term.toString();
        if(mapCapitalYs.containsKey(termS)){
            term = mapCapitalYs.get(termS);
            return;
        }

        if(termS.contains("y")){
            if(term.charAt(0) == 'y'){
                term = term.replace(0, 1, "Y");
            }
            for(int i = 1, length = term.length(); i < length; i++){
                if(term.charAt(i) == 'y'){
                    if(isVowel(term.charAt(i - 1))){
                        term = term.replace(i, i + 1, "Y");
                    }
                }
            }
        }
        
        mapCapitalYs.put(termS, term);
    }
}
