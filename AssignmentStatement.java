/*
Group: Chloe Chung and Karan Munday
File: AssignmentStatement.java
File purpose: Accepts expr from getAssignmentStatement() in Parser.java
It prints expr using the format held in the toString(). 
 */
public class AssignmentStatement implements Statement {

            private String expr;
        
            public AssignmentStatement(String expr)
            {
                if (expr == null)
                    throw new IllegalArgumentException ("null arithmetic expression argument");
                this.expr = expr;
            }
            public String toString() {
                return "This is an assignment statement ---> " + expr;
            }
            @Override
        
            public void execute()
            { 
                System.out.println(expr);
            }
        }
        

