/*
Group: Chloe Chung and Karan Munday
File: Block.java
File purpose: Statements are held in the Block. The
interpreter will handle statements from the block. Program.java is what prints 
the statements held in the block.
 */
import java.util.ArrayList;
import java.util.List;
//import java.util.stream.Collectors;

public class Block
{
    private List<Statement> stmts;

    public Block ()
    {

        stmts = new ArrayList<Statement>();
    }

    public void add(Statement stmt)
    {
        if (stmt == null)
            throw new IllegalArgumentException ("null statement argument");
        stmts.add(stmt);
    }

    public void execute()
    {
        System.out.println("This is the main function ---> function main is");
        for (int i = 0; i < stmts.size(); i++) {
            //stmts.get(i).execute();
            System.out.println(stmts.get(i));
        }
        System.out.println("This is the end of the function ---> endfun");
        System.out.println("This exits the program ---> exit");
        /* this is basically a toString() for a List. 
        String result = stmts.stream()
        .map(n -> String.valueOf(n))
        .collect(Collectors.joining("-", "{", "}"));
        System.out.println(result);
        */
    }
    }

