/*
Group: Chloe Chung and Karan Munday
File: Token.java
File purpose: Support for tokenizing
 */
public class Token
{

    private int rowNumber;
    private int columnNumber;
    private String lexeme;
    private TokenType tokType;

    /* setting up a series of preconditions */
    public Token(int rowNumber, int columnNumber, String lexeme,
                 TokenType tokType)
    {
        if (rowNumber <= 0) /* rowNumber has to be positive */
            throw new IllegalArgumentException ("invalid row number argument");
        if (columnNumber <= 0) /* columnNumber has to be positive */
            throw new IllegalArgumentException ("invalid column number argument");
        if (lexeme == null || lexeme.length() == 0) /* lexeme cant be  empty */
            throw new IllegalArgumentException ("invalid lexeme argument");
        if (tokType == null) /* tokType cant be null */
        /* IllegalArgumentException if any of the statements fail */
            throw new IllegalArgumentException ("invalid TokenType argument");
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.lexeme = lexeme;
        this.tokType = tokType;
    }

    public int getRowNumber() /* what row the scanner is operating on */
    {
        return rowNumber;
    }

    public int getColumnNumber() /* what column the scanner is operating on */
    {
        return columnNumber;
    }

    public String getLexeme() /* what lexeme (NOT token) is operating on */
    {
        return lexeme;
    }

    public TokenType getTokType() /* classify the lexeme into a token */
    {
        return tokType;
    }


}