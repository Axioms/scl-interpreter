/*
Group: Chloe Chung and Karan Munday
File: Program.java
File purpose: Accepts and prints the statements held in the 
statement block. 
 */
public class Program
{
    private Block blk;

    public Program(Block blk) //accepts the statement block
    {
        if (blk == null) //block cannot be empty, that means there are no statements held in it
            throw new IllegalArgumentException ("null block argument");
        this.blk = blk;
    }

    public void execute() 
    {
        blk.execute(); //show the statements held in the statement block
    }

}
