/*
Group: Chloe Chung and Karan Munday
File: ReturnStatement.java
File purpose: This is a form of print statement exclusively for assignment statements and arithmetic
expressions. It returns the value of the assignment statement or it returns the value of an 
arithmetic expression by performing the operation specified in the expression
 */
public class ReturnStatement implements Statement{

    private String expr;

    public ReturnStatement(String expr) //PrintStatement accepts String arguments
    {
        if (expr == null) //expr MUST contain a value
            throw new IllegalArgumentException ("null arithmetic expression argument");
        this.expr = expr;
    }
    public String toString() { //format the output of PrintStatements
        return "This is a return statement ---> " + expr;
    }
    @Override

    public void execute() //this overrides the execute() method in the interface Statement.java
    { 
        System.out.println(expr);
    }
}