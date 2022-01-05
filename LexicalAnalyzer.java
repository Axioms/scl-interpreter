/*
Group: Chloe Chung and Karan Munday
File: LexicalAnalyzer.java
File purpose: Perform lexical analysis on a specified file
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LexicalAnalyzer
{

    private List<Token> tokens;
    
    /* While the file contains info, initialize tokens in an arraylist, initiaize scanner for the file,
    and start at line number 0. Then while the file has another lexeme, process it and increment the line number
    to keep moving through the file */
    public LexicalAnalyzer(String fileName) throws FileNotFoundException, LexicalException
    {
        assert (fileName != null);
        tokens = new ArrayList<Token>();
        Scanner sourceCode = new Scanner (new File (fileName));
        int lineNumber = 0;
        while (sourceCode.hasNext())
        {
            String line = sourceCode.nextLine();
            processLine (line, lineNumber);
            lineNumber++;
        }
        /* EOS just means we've reached the end of the file and we're done scanning */
        tokens.add(new Token (lineNumber, 1, "EOS", TokenType.EOS_TOK)); 
        sourceCode.close(); 
    }
    /* Support for processing with the scanner, keeps tabs on pulling the lexeme, finding the token,
    and keeping track of index and line number*/
    private void processLine(String line, int lineNumber) throws LexicalException
    {
        assert (line != null && lineNumber >= 1);
        int index = 0;
        index = skipWhiteSpace (line, index);
        while (index < line.length())
        {
            String lexeme = getLexeme (line, lineNumber, index);
            TokenType tokType = getTokenType (lexeme, lineNumber, index);
            tokens.add(new Token (lineNumber + 1, index + 1, lexeme, tokType));
            index += lexeme.length();
            index = skipWhiteSpace (line, index);
        }
    }

    private TokenType getTokenType(String lexeme, int lineNumber, int columnNumber) throws LexicalException
    {
        assert (lexeme != null && lineNumber >= 1 && columnNumber >= 1);
        TokenType tokType = null;
        if (Character.isLetter(lexeme.charAt(0)))
        { 
            /* IMPORTANT: IF THE LEXEME IS VALID BUT NOT A DEFINED KEYWORD OR SYMBOL,
            IT'S AN ID_TOK */
            if (lexeme.length() == 1) 
                if (isValidIdentifier (lexeme))
                    tokType = TokenType.ID_TOK;
                else
                    throw new LexicalException ("invalid lexeme at row number " +
                            (lineNumber + 1) + " and column " + (columnNumber + 1));
                            
               /* THESE ARE ALL RESERVED KEYWORDS */
                else if (lexeme.equals("array"))
                    tokType = TokenType.ARRAY_TOK;
                else if (lexeme.equals("begin"))
                    tokType = TokenType.BEGIN_TOK;
                else if (lexeme.equals("constant"))
                    tokType = TokenType.CONSTANT_TOK;
                else if (lexeme.equals("declarations"))
                    tokType = TokenType.DECLARATIONS_TOK;
                else if (lexeme.equals("define"))
                    tokType = TokenType.DEFINE_TOK;
                else if (lexeme.equals("display"))
                    tokType = TokenType.DISPLAY_TOK;
                else if (lexeme.equals("do"))
                    tokType = TokenType.DO_TOK;
                else if (lexeme.equals("else"))
                    tokType = TokenType.ELSE_TOK;
                else if (lexeme.equals("endfun"))
                    tokType = TokenType.ENDFUN_TOK;
                else if (lexeme.equals("exit"))
                    tokType = TokenType.EXIT_TOK;
                else if (lexeme.equals("endif"))
                    tokType = TokenType.ENDIF_TOK;
                else if (lexeme.equals("endrepeat"))
                    tokType = TokenType.ENDREPEAT_TOK;
                else if (lexeme.equals("endwhile"))
                    tokType = TokenType.ENDWHILE_TOK;
                else if (lexeme.equals("enum"))
                    tokType = TokenType.ENUM_TOK;
                else if (lexeme.equals("forward"))
                    tokType = TokenType.FORWARD_TOK;
                else if (lexeme.equals("function"))
                    tokType = TokenType.FUNCTION_TOK;
                else if (lexeme.equals("global"))
                    tokType = TokenType.GLOBAL_TOK;
                else if (lexeme.equals("if"))
                    tokType = TokenType.IF_TOK;
                else if (lexeme.equals("implementations"))
                    tokType = TokenType.IMPLEMENTATIONS_TOK;
                else if (lexeme.equals("import"))
                    tokType = TokenType.IMPORT_TOK;
                else if (lexeme.equals("integer"))
                    tokType = TokenType.INTEGER_TOK;
                else if (lexeme.equals("is"))
                    tokType = TokenType.IS_TOK;
                else if (lexeme.equals("main"))
                    tokType = TokenType.MAIN_TOK;
                else if (lexeme.equals("parameters"))
                    tokType = TokenType.PARAMETERS_TOK;
                else if (lexeme.equals("pointer"))
                    tokType = TokenType.POINTER_TOK;
                else if (lexeme.equals("references"))
                    tokType = TokenType.REFERENCES_TOK;
                else if (lexeme.equals("repeat"))
                    tokType = TokenType.REPEAT_TOK;
                else if (lexeme.equals("return"))
                    tokType = TokenType.RETURN_TOK;
                else if (lexeme.equals("set"))
                    tokType = TokenType.SET_TOK;
                else if (lexeme.equals("specifications"))
                    tokType = TokenType.SPECIFICATIONS_TOK;
                else if (lexeme.equals("struct"))
                    tokType = TokenType.STRUCT_TOK;
                else if (lexeme.equals("symbol"))
                    tokType = TokenType.SYMBOL_TOK;
               else if (lexeme.equals("then"))
                    tokType = TokenType.THEN_TOK;
                else if (lexeme.equals("type"))
                    tokType = TokenType.TYPE_TOK;
                else if (lexeme.equals("until"))
                    tokType = TokenType.UNTIL_TOK;
                else if (lexeme.equals("while"))
                    tokType = TokenType.WHILE_TOK;
                else if (isValidIdentifier (lexeme))
                        tokType = TokenType.ID_TOK;
            else
                throw new LexicalException ("invalid lexeme at row number " +
                        (lineNumber + 1) + " and column " + (columnNumber + 1));
        }
        else if (Character.isDigit (lexeme.charAt(0)))
        {
            if (allDigits (lexeme))
                tokType = TokenType.CONST_TOK; //defines a constant i.e. series of digits
            else
                throw new LexicalException ("invalid lexeme at row number " +
                        (lineNumber + 1) + " and column " + (columnNumber + 1));
        } //symbols for arithmetic and general symbols
        else if (lexeme.equals("+"))
        tokType = TokenType.ADD_TOK;
        else if (lexeme.equals("-"))
        tokType = TokenType.SUB_TOK;
        else if (lexeme.equals("*"))
        tokType = TokenType.MULT_TOK;
        else if (lexeme.equals("/"))
        tokType = TokenType.DIV_TOK;
        else if (lexeme.equals("%"))
        tokType = TokenType.MOD_TOK;
        else if (lexeme.equals("="))
        tokType = TokenType.ASSIGN_TOK;
        else if (lexeme.equals(":"))
        tokType = TokenType.COLON_TOK;
        else if (lexeme.equals("("))
        tokType = TokenType.LEFT_PAREN_TOK;
        else if (lexeme.equals(")"))
            tokType = TokenType.RIGHT_PAREN_TOK;
        else if (lexeme.equals("["))
            tokType = TokenType.LEFT_BRACK_TOK;
        else if (lexeme.equals("]"))
            tokType = TokenType.RIGHT_BRACK_TOK;
        else if (lexeme.equals(">="))
            tokType = TokenType.GE_TOK;
        else if (lexeme.equals(">"))
            tokType = TokenType.GT_TOK;
        else if (lexeme.equals("<="))
            tokType = TokenType.LE_TOK;
        else if (lexeme.equals("<"))
            tokType = TokenType.LT_TOK;
        else if (lexeme.equals("=="))
            tokType = TokenType.EQ_TOK;
        else if (lexeme.equals("~="))
            tokType = TokenType.NE_TOK;
        else if (lexeme.equals(","))
            tokType = TokenType.COMMA_TOK;
        else if (lexeme.equals("\""))
            tokType = TokenType.QUOTE_TOK;
        else
            throw new LexicalException ("invalid lexeme at row number " +
                    (lineNumber + 1) + " and column " + (columnNumber + 1));
        return tokType;
    }
    /* This is support for determining CONST_TOK. If something is all digits, it qualifies as a constant */
    private boolean allDigits(String s)
    {
        assert (s != null);
        int i = 0;
        while (i < s.length() && Character.isDigit(s.charAt(i)))
            i++;
        return i == s.length();
    }
    /*IMPORTANT: This method iterates through the file line by line and grabs characters
    and/or lexemes by index. It then appends a substring of line (i.e. whatever line it's working on)
    ranging from the beginning to "i" which is the current to a StringBuilder*/
    private String getLexeme(String line, int lineNumber, int index)
    {
        StringBuilder str = new StringBuilder();
        int i = index;  
        assert (line != null && lineNumber >= 1 && index >= 0);
        while (i < line.length() && !Character.isWhitespace(line.charAt(i))) {
            i++; 
        }
        str.append(line.substring(index, i));
        return str.toString();
    } 
    /*finds the indices of whitespace, important for delimiting lexemes*/
    private int skipWhiteSpace(String line, int index)
    {
        assert (line != null && index >= 0);
        while (index < line.length() && Character.isWhitespace(line.charAt(index)))
            index++;
        return index;
    }
    /* this is error handling */
    public Token getNextToken() throws LexicalException
    {
        if (tokens.isEmpty())
            throw new LexicalException ("no more tokens");
        return tokens.remove(0);
    }
    /* this is more error handling */
    public Token getLookaheadToken() throws LexicalException
    {
        if (tokens.isEmpty())
            throw new LexicalException ("no more tokens");
        return tokens.get(0);
    }
    /* this determines if the character/lexeme at a given index is valid. 
    The regex "^[a-zA-Z0-9_.-]*$" states that from the beginning to the end of the line,
    characters and lexemes containing uppercase and lowercase A-Z, digits 0-9, 
    and the characters underscore (_), period (.), and hyphen (-), are viable. 
    */
    public static boolean isValidIdentifier (String ch)
    {
        //Pattern pattern = Pattern.compile("([A-Z]|[a-z])", Pattern.CASE_INSENSITIVE);
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_.-]*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ch);
        return matcher.find();
    
    }
 
    
    /* Prints all tokens and lexemes in the form 
    The current token is: <TOK_(insert)> ---> Current lexeme is: <insert corresponding lexeme> */
    public void printLex(){ 
        for(int i = 0; i < tokens.size(); i++){
            TokenType tokType = tokens.get(i).getTokType();
            String lexeme = tokens.get(i).getLexeme();
            System.out.println("The current token is: " + tokType + " ---> Current lexeme is: " + lexeme);
        }
    }
}