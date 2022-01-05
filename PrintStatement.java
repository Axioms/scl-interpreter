/*
Group: Chloe Chung and Karan Munday
File: PrintStatement.java
File purpose: Accepts expr from getPrintStatement() in Parser.java
It prints expr using the format held in the toString(). 
 */
public class PrintStatement implements Statement{

    private String expr;

    public PrintStatement(String expr) //PrintStatement accepts String arguments
    {
        if (expr == null) //expr MUST contain a value
            throw new IllegalArgumentException ("null arithmetic expression argument");
        this.expr = expr;
    }
    public String toString() { //format the output of PrintStatements
        return "This is a print statement ---> " + expr;
    }
    @Override

    public void execute() //this overrides the execute() method in the interface Statement.java
    { 
        System.out.println(expr);
    }
}
