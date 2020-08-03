package query;

/**
 * This class represents a single query for search (x AND y, x OR y, NOT x)
 */
public class BooleanQuery {

  private String inputA;
  private String operator;
  private String inputB;

  /**
   * Constructor for NOT
   *
   * @param operator NOT
   * @param inputB   word that shouldn't be included in results
   */
  public BooleanQuery(String operator, String inputB) {
    inputA = null;
    this.operator = operator;
    this.inputB = inputB;
  }

  /**
   * Constructor for AND and OR
   *
   * @param inputA   first word
   * @param operator AND or OR
   * @param inputB   second word
   */
  public BooleanQuery(String inputA, String operator, String inputB) {
    this.inputA = inputA;
    this.operator = operator;
    this.inputB = inputB;
  }

  /**
   * Gets the first word
   *
   * @return the first word
   */
  public String getInputA() {
    return inputA;
  }

  /**
   * Gets the operator (AND, OR, NOT)
   *
   * @return the operator
   */
  public String getOperator() {
    return operator;
  }

  /**
   * Gets the second word
   *
   * @return the second word
   */
  public String getInputB() {
    return inputB;
  }

  /**
   * Output object as String
   *
   * @return the object as a String
   */
  @Override
  public String toString() {
    return "BooleanQuery{" + "inputA='" + inputA + '\'' + ", operator='" + operator + '\''
            + ", inputB='" + inputB + '\'' + '}';
  }
}
