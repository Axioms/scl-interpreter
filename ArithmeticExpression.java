/*
Group: Chloe Chung and Karan Munday
File: ArithmeticExpression.java
File purpose: Accepts expr from getArithmeticExpression() in Parser.java
It prints expr using the format held in the toString(). 
 */
public class ArithmeticExpression implements Statement{

        private String expr;
    
        public ArithmeticExpression(String expr)
        {
            if (expr == null)
                throw new IllegalArgumentException ("null arithmetic expression argument");
            this.expr = expr;
        }
        public String toString() {
            return "This is an arithmetic expression ---> " + expr;
        }
        @Override
    
        public void execute()
        { 
            System.out.println(expr);
        }

    }
