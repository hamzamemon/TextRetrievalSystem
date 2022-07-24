package com.github.hamzamemon.util;

import java.io.File;

public final class FileUtils {
    
    public static String getFilename(File file) {
        String name = file.getName();
        
        int pos = name.lastIndexOf('.');
        if (pos == -1) {
            return name;
        }
        
        return name.substring(0, pos);
    }
    
    /**
     * Constructor for FileUtils
     */
    private FileUtils() {
    }
}
