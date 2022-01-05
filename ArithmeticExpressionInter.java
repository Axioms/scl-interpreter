/*
Group: Chloe Chung and Karan Munday
File: ArithmeticExpressionInter.java
File purpose: Accepts expr from getArithmeticExpressionInter() in Parser.java
It prints expr using the format held in the toString(). The method 
public ArithmeticExpressionInter(String expr) takes expr and evaluates the expression
held in the string created by getArithmeticExpressionInter() in Parser.java. This program returns
the value of the expression, rather than printing the operands and operator. 
 */
import java.util.*;
public class ArithmeticExpressionInter implements Statement{
private String expr;


    public ArithmeticExpressionInter(String expr)
    {
        if (expr == null)
            throw new IllegalArgumentException ("null arithmetic expression argument");
        //this.expr = expr;
        int value;
        Scanner sc = new Scanner(expr);
        // get the next number from the scanner
        int firstValue = Integer.parseInt(sc.findInLine("[0-9]*"));
        // get everything which follows and is not a number (might contain white spaces)
        String operator = sc.findInLine("[^0-9]*").trim();
        int secondValue = Integer.parseInt(sc.findInLine("[0-9]*"));
            if(operator.equals("+")) {
                value = firstValue + secondValue;
            } else if(operator.equals("-")) {
                value = firstValue - secondValue;
            } else if(operator.equals("/")) {
                value = firstValue / secondValue;
            } else if(operator.equals("*")) {
                value = (int)(firstValue * secondValue);
            } else if(operator.equals("%")) {
                value = (int) firstValue % secondValue;
            // todo: add additional operators as needed..
            }else{
                throw new RuntimeException("unknown operator: "+operator);
            }
           this.expr = Integer.toString(value);
        
    }
    
    @Override

    public void execute()
    { 
        System.out.println(expr);
        }
        public String toString() {
            return expr;
        }
        }
    
   
    
