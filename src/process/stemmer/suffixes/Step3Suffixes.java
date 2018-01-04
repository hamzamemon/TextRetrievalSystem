package process.stemmer.suffixes;

public enum Step3Suffixes {
    
    // 5 letters
    ATIVE(""),
    ALIZE("al"),
    ICATE("ic"),
    ICITI("ic"),
    
    // 4 letters
    ICAL("ic"),
    NESS(""),
    
    // 3 letters
    FUL("");
    
    final String suffix;
    
    Step3Suffixes(String suffix) {
        this.suffix = suffix;
    }
    
    public String getSuffix() {
        return suffix;
    }
    
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
