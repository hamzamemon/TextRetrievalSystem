package index;

import java.io.Serializable;

public class IDF implements Serializable {
    
    private static int staticIndex = 0;
    private int docFrequency;
    private int index = 0;
    
    public IDF() {
        docFrequency = 1;
        index = staticIndex;
        staticIndex++;
    }
    
    public void incrementDocFrequency() {
        docFrequency++;
    }
    
    public int getDocFrequency() {
        return docFrequency;
    }
    
    public int getIndex() {
        return index;
    }
}
