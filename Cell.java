import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cell extends Observable implements Observer {

	private double value = 0.0;
	private String exp = null;
	private List<Cell> subjects = new ArrayList<Cell>();

    private int sub=0;
    public boolean status = false;
    //observer interface implementation
    //add Observer to the current cell
	public void addSubject(Cell subject) {
        subject.addObserver(this);
		this.subjects.add(subject);
	}

    public List<Cell> getSubjects(){
        return this.subjects;
    }

    //set value of cell
	public void setValue(double val) {
		value = val;
        setChanged();
        status =true;
		notifyObservers();
	}

	public void setValue()throws CyclicDepandencyException{
		if (exp != null) {
            status =true;
			setValue(eval());
		}
	}
    @Override
	public void update(Observable o, Object arg)
    {
        try {
            setValue();
        } catch (Exception ex) {
            Logger.getLogger(Cell.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

	public double getValue() {

        return value;
    }

	public void setExpression(String exp) {
		this.exp = exp;
	}

	public String getExpression() {
		return exp;
	}

	public String toString() { return "" + value; }

    //evaluate expression in reverse polish notation.
	private double eval()throws CyclicDepandencyException{
        double result = 0.0;
        Stack<String> stack = new Stack<String>();
        String[] tokens  = exp.split(" ");
        if(tokens.length == 1)return result+= evaluate(tokens[0]);
        String operators = "+-*/";
        for(String t:tokens){
            if(!operators.contains(t))stack.push(t);
            else{
                double a = evaluate(stack.pop());
                double b = evaluate(stack.pop());
                int index = operators.indexOf(t);
                switch(index){
                    case 0:
                        stack.push(String.valueOf(a+b));
                        break;
                    case 1:
                        stack.push(String.valueOf(b-a));
                        break;
                    case 2:
                        stack.push(String.valueOf(a*b));
                        break;
                    case 3:
                        stack.push(String.valueOf(a/b));
                        break;
                }
            }
        }
        result = Double.valueOf(stack.pop());

        return result;
	}

    public double evaluate(String str)throws CyclicDepandencyException{
        if(isNumber(str.charAt(0))){
            return Double.parseDouble(str);
        }else{
               double value = subjects.get(sub).getValue();
               sub++;
               return value;
        }
    }

    public static boolean isNumber(char c){
        int value = (int) c;
        return value >= 48 && value <= 57;
    }


}
