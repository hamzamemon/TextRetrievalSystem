package process.stemmer;

public class Word {
    
    private String term;
    private String region1;
    private String region2;
    
    public Word(String term) {
        this.term = term;
        region1 = getRegion(term);
        region2 = getRegion(region1);
    }
    
    private String getRegion(String section) {
        int i = 0;
        while(i < section.length() && "aeiouy".indexOf(section.charAt(i)) < 0) {
            i++;
        }
        while(i < section.length() && "aeiouy".indexOf(section.charAt(i)) >= 0) {
            i++;
        }
        return section.substring(i);
    }
    
    public String getTerm() {
        return term;
    }
    
    public String getRegion1() {
        return region1;
    }
    
    public String getRegion2() {
        return region2;
    }
}
