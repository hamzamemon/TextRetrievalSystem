package index;

import java.io.Serializable;

/**
 * This class creates a Document that acts as the text file. It contains the name of the file, the
 * length and the number of words in the file after processing it.
 */
public class Document implements Serializable {

  private static final long serialVersionUID = -2554333512339589168L;
  private int number;
  private int numberOfWords;
  private double length;

  /**
   * Instantiates a new Document.
   *
   * @param number        the document number
   * @param numberOfWords the number of words after processing
   */
  public Document(int number, int numberOfWords) {
    this.number = number;
    this.numberOfWords = numberOfWords;
  }

  /**
   * Gets the document's length
   *
   * @return length
   */
  public double getLength() {
    return length;
  }

  /**
   * Sets the document's length
   *
   * @param length the length
   */
  public void setLength(double length) {
    this.length = length;
  }

  /**
   * Adds weight^2 to the length
   *
   * @param amount weight^2
   */
  public void addLength(double amount) {
    length += amount;
  }

  /**
   * Gets the document number
   *
   * @return the document number
   */
  public int getNumber() {
    return number;
  }

  /**
   * Gets the number of words in the document after pre-processing
   *
   * @return the number of words
   */
  public int getNumberOfWords() {
    return numberOfWords;
  }

  /**
   * Output object as String
   *
   * @return the object as a String
   */
  @Override
  public String toString() {
    return "Document{" + "number=" + number + ", numberOfWords=" + numberOfWords + ", length="
            + length + '}';
  }
}
