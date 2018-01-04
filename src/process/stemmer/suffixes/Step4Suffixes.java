package process.stemmer.suffixes;

public enum Step4Suffixes {
    
    // 5 letters
    EMENT(""),
    
    // 4 letters
    ANCE(""),
    ENCE(""),
    ABLE(""),
    IBLE(""),
    MENT(""),
    
    // 3 letters
    ANT(""),
    ENT(""),
    ISM(""),
    ATE(""),
    ITI(""),
    OUS(""),
    IVE(""),
    IZE(""),
    ION(""),
    
    // 2 letters
    AL(""),
    ER(""),
    IC(""),
    OU("");
    
    final String suffix;
    
    Step4Suffixes(String suffix) {
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
