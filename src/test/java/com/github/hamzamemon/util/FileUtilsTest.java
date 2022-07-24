package com.github.hamzamemon.util;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileUtilsTest {
    
    @Test
    public void test_getFilename() {
        assertEquals("test", FileUtils.getFilename(new File("test.txt")));
        assertEquals("test.txt", FileUtils.getFilename(new File("test.txt.bak")));
        assertEquals("test", FileUtils.getFilename(new File("test.jpeg")));
    }
}
