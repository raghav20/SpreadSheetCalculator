import java.util.Stack;

public class MathExpression{
    Double result;
    private String str;
    MathExpression(String str){
        this.str = str;
        result = evaluateExpression(str);
    }
    public double evaluateExpression(String str){
        double result=0.0;
        Stack<String> stack = new Stack<String>();
        String[] temp = str.split(" ");
        for (int k = temp.length - 1; k >= 0; k--) {
            stack.add(temp[k]);
        }
        while (!stack.isEmpty()) {
            String s1 = stack.pop();
            if (stack.isEmpty()) {
                result = Integer.parseInt(s1);
            } else {
                String s2 = stack.pop();
                String s3 = stack.pop();
                int value1 = Integer.parseInt(s1);
                int value2 = Integer.parseInt(s2);
                if (s3.equals("+")) {
                    stack.add("" + (value1 + value2));
                }
                if (s3.equals("-")) {
                    stack.add("" + (value1 - value2));
                }
                if (s3.equals("*")) {
                    stack.add("" + (value1 * value2));
                }
                if (s3.equals("/")) {
                    stack.add("" + (value1 / value2));
                }
            }
        }
        return result;
    }
}
