package process.stemmer;

public class Word {
    
    private StringBuilder term;
    private String region1;
    private String region2;
    private String shortSyllable;
    private boolean isShort;
    private boolean endsWithDouble;
    
    public Word(String termS) {
        setTerm(new StringBuilder(termS));
    }
    
    private String getRegion(StringBuilder section) {
        int i = 0;
        while(i < section.length() && "aeiouy".indexOf(section.charAt(i)) == -1) {
            i++;
        }
        while(i < section.length() && "aeiouy".indexOf(section.charAt(i)) >= 0) {
            i++;
        }
        return section.substring(i);
    }
    
    public StringBuilder getTerm() {
        return term;
    }
    
    public void setTerm(StringBuilder term) {
        String termS = term.toString();
        
        if(termS.contains("y")) {
            term = setCapitalYs(term);
        }
        this.term = term;
        if(termS.endsWith("bb") || termS.endsWith("dd") || termS.endsWith("ff") || termS.endsWith("gg")
                || termS.endsWith("mm") || termS.endsWith("nn") || termS.endsWith("pp") || termS.endsWith("rr") || termS.endsWith("tt")) {
            endsWithDouble = true;
        }
        
        region1 = getRegion(term);
        region2 = getRegion(new StringBuilder(region1));
        
        if((term.length() >= 2) && ("aeiouy".indexOf(term.charAt(0)) >= 0) && ("aeiouy").indexOf(term.charAt(1)) == -1) {
            shortSyllable = term.toString();
        }
        else {
            for(int i = 1; i < term.length() - 1; i++) {
                if("aeiouy".indexOf(term.charAt(i)) >= 0) {
                    if(("aeiouy".indexOf(term.charAt(i + 1)) == -1) && (term.charAt(i + 1) != 'w' && term.charAt(i + 1) != 'x' && term.charAt(i + 1) != 'Y')) {
                        if("aeiouy".indexOf(term.charAt(i - 1)) == -1) {
                            shortSyllable = term.substring(i);
                            break;
                        }
                    }
                }
            }
        }
        
        isShort = shortSyllable != null && term.toString().endsWith(shortSyllable) && region1.isEmpty();
    }
    
    
    private StringBuilder setCapitalYs(StringBuilder term) {
        if(term.charAt(0) == 'y') {
            term = term.replace(0, 1, "Y");
        }
        for(int i = 1; i < term.length(); i++) {
            if(term.charAt(i) == 'y') {
                if("aeiouy".indexOf(term.charAt(i - 1)) >= 0) {
                    term = term.replace(i, i + 1, "Y");
                }
            }
        }
        return term;
    }
    
    public boolean isShort() {
        return isShort;
    }
    
    public boolean endsWithDouble() {
        
        return endsWithDouble;
    }
    
    public String getShortSyllable() {
        return shortSyllable;
    }
    
    public String getRegion1() {
        return region1;
    }
    
    public String getRegion2() {
        return region2;
    }
}
