# TextRetrievalSystem

This Text Retrieval system indexes files located in `/res/data/` and allows you to search through
them using a single query.  This performs the [Porter2 Stemming
Algorithm](https://github.com/xJavaTheHutt/PorterStemmer) on each word in the files and in the input
to group like words (such as generous and generosity).

### Set-Up
1. Put .txt files into `res/data/` that you would like to search through
2. Add or remove words from `src/main/java/process/stoplist.txt` to have them ignored.  Stop words do not contribute to the cosine normalization.
3. Run `src/main/java/index/Invert.java` to index the files
4. Run one of these files
   * Run `src/main/java/search/Driver.java` to run a normal query
   * Run `src/main/java/search/VSMTester.java` to perform [cosine normalization](https://en.wikipedia.org/wiki/Cosine_similarity) and be returned the top 1000 documents
5. Your query's results should be saved in the top level directory

### Queries
1. NOT: `NOT x` returns all documents that do not contain `x`
2. AND: `x AND y` returns all documents that contain `x` and `y`
3. OR: `x OR y` returns all documents that contain `x`, `y` or both
