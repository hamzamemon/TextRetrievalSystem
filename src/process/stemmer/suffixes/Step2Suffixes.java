package process.stemmer.suffixes;

public enum Step2Suffixes {
    
    // 7 letters
    ATIONAL("ate"),
    IZATION("ize"),
    FULNESS("ful"),
    OUSNESS("ous"),
    IVENESS("ive"),
    
    // 6 letters
    TIONAL("tion"),
    BILITI("ble"),
    
    // 5 letters
    ENTLI("ent"),
    ATION("ate"),
    ALISM("al"),
    ALITI("al"),
    OUSLI("ous"),
    IVITI("ive"),
    
    // 4 letters
    ENCI("ence"),
    ANCI("ance"),
    //ABLI("able"),
    IZER("ize"),
    ATOR("ate"),
    ALLI("al"),
    LOGI("log"),
    
    // 3 letters
    BLI("ble"),
    ELI("e");
    
    final String suffix;
    
    Step2Suffixes(String suffix) {
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
