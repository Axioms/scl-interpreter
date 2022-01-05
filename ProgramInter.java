/*
Group: Chloe Chung and Karan Munday
File: ProgramInter.java
File purpose: Accepts and prints the statements held in the 
statement block. 
 */
public class ProgramInter
{
    private BlockInter blk;

    public ProgramInter(BlockInter blk) //accepts the statement block
    {
        if (blk == null) //block cannot be empty, that means there are no statements held in it
            throw new IllegalArgumentException ("null block argument");
        this.blk = blk;
    }

    public void execute() 
    {
        blk.execute();
           }
       
       }
