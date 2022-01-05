import java.util.*;
/*
Group: Chloe Chung and Karan Munday
File: AssignmentStatementInter.java
File purpose: Accepts expr from getAssignmentStatementInter() in Parser.java
It prints expr using the format held in the toString(). The method 
public AssignmentStatementInter(String expr) takes expr and returns the value
associated with its variable defined in Parser.java.
 */
 
public class AssignmentStatementInter implements Statement{

    private String expr;

    public AssignmentStatementInter(String expr)
    {
        if (expr == null)
            throw new IllegalArgumentException ("null arithmetic expression argument");
        this.expr = expr;
    }
    public String toString() {
        return expr;
    }
    @Override

    public void execute()
    { 
        Scanner sc = new Scanner(expr);

        // get the next number from the scanner
        int firstValue = Integer.parseInt(sc.findInLine("[0-9]*"));
        System.out.println(firstValue);

    }
    }
