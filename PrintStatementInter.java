/*
Group: Chloe Chung and Karan Munday
File: PrintStatementInter.java
File purpose: Accepts expr from getPrintStatementInter() in Parser.java
It prints the contents of expr using the format held in the toString(). 
 */
public class PrintStatementInter implements Statement{

    private String expr;

    public PrintStatementInter(String expr) //PrintStatement accepts String arguments
    {
        if (expr == null) //expr MUST contain a value
            throw new IllegalArgumentException ("null arithmetic expression argument");
        this.expr = expr;
    }
    public String toString() { //format the output of PrintStatements
        return expr; 
    } 
    @Override

    public void execute() //this overrides the execute() method in the interface Statement.java
    { 
        System.out.println(expr);
    }
}
