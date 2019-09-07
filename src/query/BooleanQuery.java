package query;

public class BooleanQuery {
    
    private String inputA;
    private String operator;
    private String inputB;
    
    public BooleanQuery(String operator, String inputB) {
        inputA = null;
        this.operator = operator;
        this.inputB = inputB;
    }
    
    public BooleanQuery(String inputA, String operator, String inputB) {
        this.inputA = inputA;
        this.operator = operator;
        this.inputB = inputB;
    }
    
    public String getInputA() {
        return inputA;
    }
    
    public String getOperator() {
        return operator;
    }
    
    public String getInputB() {
        return inputB;
    }
    
    @Override
    public String toString() {
        return "BooleanQuery{" +
                "inputA='" + inputA + '\'' +
                ", operator='" + operator + '\'' +
                ", inputB='" + inputB + '\'' +
                '}';
    }
}
