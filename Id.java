/*
Group: Chloe Chung and Karan Munday
File: Id.java
File purpose: Determines the validity of an identifier then returns said identifier.
 */
public class Id 
{

    private String ch;

    /**
     * @param ch - must be a valid identifier
     * @throws IllegalArgumentException if ch is not a valid identifier
     */
    public Id(String ch)
    {
        if (!LexicalAnalyzer.isValidIdentifier(ch))
            throw new IllegalArgumentException ("character is not a valid identifier");
        this.ch = ch;
    }

    public String getChar ()
    {
        return ch;
    }

}