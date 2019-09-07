package process.stemmer.suffixes;

import java.util.Locale;

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
    LESSLI("less"),
    
    // 5 letters
    ENTLI("ent"),
    ATION("ate"),
    ALISM("al"),
    ALITI("al"),
    OUSLI("ous"),
    IVITI("ive"),
    FULLI("ful"),
    
    // 4 letters
    ENCI("ence"),
    ANCI("ance"),
    ABLI("able"),
    IZER("ize"),
    ATOR("ate"),
    ALLI("al"),
    OGI("og"),
    
    // 3 letters
    BLI("ble"),
    
    // 2 letters
    LI("");
    
    final String suffix;
    
    Step2Suffixes(String suffix) {
        this.suffix = suffix;
    }
    
    public String getSuffix() {
        return suffix;
    }
    
    @Override
    public String toString() {
        return name().toLowerCase(Locale.ENGLISH);
    }
}
