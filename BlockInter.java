/*
Group: Chloe Chung and Karan Munday
File: BlockInter.java
File purpose: Statements for interpreter processing are held in the Block. 
ProgramInter.java is what prints the statements held in the block.
 */
import java.util.ArrayList;
import java.util.List;
//import java.util.stream.Collectors;

public class BlockInter
{
    private List<Statement> stmts;

    public BlockInter ()
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
        
        //for (int i = 0; i < 1; i++) {
            //stmts.get(i).execute(); 
            /*The above is viable for printing, but by using .get 
            I formatted the output */
            System.out.println(stmts.get(0));
            System.out.println(stmts.get(1));
            System.out.println(stmts.get(2));
            System.out.println(stmts.get(3));
            System.out.print(stmts.get(4));
            System.out.println(stmts.get(5));
            System.out.print(stmts.get(6));
            System.out.println(stmts.get(7));
            System.out.print(stmts.get(8));
            System.out.print(stmts.get(9));
            System.out.println(stmts.get(10));
            System.out.print(stmts.get(11));
            System.out.println(stmts.get(12));
            System.out.print(stmts.get(13));
            System.out.println(stmts.get(14));
        }
    
        
        
    }
    

