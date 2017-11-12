package process;

public class PorterStemmer {

    public static String stem(String term){
        term = doStep1(term);
        return term;
    }

    private static String doStep1(String term) {
        if(term.isEmpty()){
            return term;
        }
        if(term.endsWith("sses")){
            // sses -> ss
            term = term.substring(0, term.length() - 2);
        }
        else if(term.endsWith("ied") || term.endsWith("ies")){
            if(term.length() >= 5){
                // ied or ies -> i
                term = term.substring(0, term.length() - 2);
            }
            else {
                // ied or ies -> ie
                term = term.substring(0, term.length() - 1);
            }
        }

        else if(term.charAt(term.length() - 1) == 's'){
            boolean vowelNotImmediatelyBeforeS = false;
            for(int i = 0; i < term.length() - 2; i++){
                String vowels = getVowels(term);
                if(vowels.indexOf(term.charAt(i)) >= 0){
                    vowelNotImmediatelyBeforeS = true;
                    break;
                }
            }

            if(vowelNotImmediatelyBeforeS){
                term = term.substring(0, term.length() - 1);
            }
        }

        String r1 = getRegion(term);
        if(r1.endsWith("eed") || r1.endsWith("eedly")){
            term = term.substring(0, term.lastIndexOf("ee") + 2);
        }

        return term;
    }

    private static String getRegion(String term){
        int i = 0;
        while(i < term.length() && "aeiouy".indexOf(term.charAt(i)) < 0){
            i++;
        }
        while(i < term.length() && "aeiouy".indexOf(term.charAt(i)) >= 0){
            i++;
        }
        return term.substring(i);
    }

    private static String getVowels(String term){
        // TODO y is  the oppposite of the letter before (anyway -> first y is vowel, second y is consonant)
        return "aeiouy";
    }
}
