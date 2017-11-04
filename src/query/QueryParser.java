package query;

import process.Preprocess;

public final class QueryParser {

    public static BooleanQuery parse(String queryTerms){
        String inputA;
        String operator;
        String inputB = "";

        String[] array = queryTerms.split(" ");

        if(array.length == 2){
            operator = array[0].toUpperCase();
            inputB = Preprocess.process(array[1]);
            return new BooleanQuery(operator, inputB);
        }
        if(array.length == 1){
            inputA = Preprocess.process(array[0]);
            operator = "OR";
        }
        else{
            inputA = Preprocess.process(array[0]);
            operator = array[1].toUpperCase();
            inputB = Preprocess.process(array[2]);
        }
        return new BooleanQuery(inputA, operator, inputB);
    }
}
