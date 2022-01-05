import java.util.*;
/*
Group: Chloe Chung and Karan Munday
File name: Parser.java
File purpose:
1. Parser(): Invokes the lexical analyzer with the file as the argument
2. parse() determines what format the file being evaluated must follow. Here it is:
   function main is
   <statements>
   endfun 
     exit
3. private Block getBlock() is the getter method for Block.java. It adds statements to the 
block for the interpreter. 
4. getStatement() declares a stmt and calls Token tok = lex.getLookaheadToken(); to determine what token
are valid starts to statements AND sets stmt equal to the getter method that corresponds with what 
token is at the beginning of the statement (for ex. if it starts with DISPLAY_TOK then stmt is a 
PrintStatement so it calls getPrintStatement)
4. The getter methods check if each lexeme or token abides by the grammar specifications.
5. private boolean isValidStartOfStatement(Token tok): this specifies what tokens a statement can start with
(id, if, while, for, display). It's a boolean because it evaluates to T/F and throws an exception 
if the statement starts with something that isn't a token it specified.
6. match() basically uses lookahead to make sure tokens match token types.
*/
import java.io.FileNotFoundException;

public class Parser
{
    private LexicalAnalyzer lex; // declare the lexer (named lex)

    public Parser(String fileName) throws FileNotFoundException, LexicalException
    {
        lex = new LexicalAnalyzer (fileName); //call the lexical analyzer to scan the file and handle tokens
    }
    /*
    Method name: parse() 
    What it does: Assumes files are of the form 
    function main is
    <statements>
    endfun 
    exit
    */
    public Program parse () throws ParserException, LexicalException
    {
        Token tok = lex.getNextToken();
        match (tok, TokenType.FUNCTION_TOK);
        tok = lex.getNextToken();
        match (tok, TokenType.MAIN_TOK);
        tok = lex.getNextToken();
        match (tok, TokenType.IS_TOK);
        Block blk = getBlock();
        tok = lex.getNextToken();
        match (tok, TokenType.ENDFUN_TOK);
        tok = lex.getNextToken();
        match (tok, TokenType.EXIT_TOK);
        tok = lex.getNextToken();
        if (tok.getTokType() != TokenType.EOS_TOK)
            throw new ParserException ("garbage at end of file");
        return new Program (blk);
    }
    public ProgramInter parseInter () throws ParserException, LexicalException
    {
        Token tok = lex.getNextToken();
        match (tok, TokenType.FUNCTION_TOK);
        tok = lex.getNextToken();
        match (tok, TokenType.MAIN_TOK);
        tok = lex.getNextToken();
        match (tok, TokenType.IS_TOK);
        BlockInter blk = getBlockInter();
        tok = lex.getNextToken();
        match (tok, TokenType.ENDFUN_TOK);
        tok = lex.getNextToken();
        match (tok, TokenType.EXIT_TOK);
        tok = lex.getNextToken();
        if (tok.getTokType() != TokenType.EOS_TOK)
            throw new ParserException ("garbage at end of file");
        return new ProgramInter (blk);
    }
    /*
    Method name: getBlock()
    What it does: It adds statements that start with valid keyword tokens to the block.
    So it declares a block named blk, then it gets the next token, it then determines if the
    token is defined in isValidStartOfStatement, then while it keeps finding legal statements, 
    it adds those statements to the block and keeps calling getLookAheadToken to see the next
    token.
    */
    private Block getBlock() throws ParserException, LexicalException
    {
        Block blk = new Block();
        Token tok = lex.getLookaheadToken();
        while (isValidStartOfStatement (tok))
        {
            Statement stmt = getStatement();
            blk.add (stmt);
            tok = lex.getLookaheadToken();
        }
        return blk;
    }

    private BlockInter getBlockInter() throws ParserException, LexicalException
    {
        BlockInter blk = new BlockInter();
        Token tok = lex.getLookaheadToken();
        while (isValidStartOfStatement (tok))
        {
            Statement stmt = getStatementInter();
            blk.add (stmt);
            tok = lex.getLookaheadToken();
        }
        return blk;
    }
    private Statement getStatementInter() throws ParserException, LexicalException
    {
        Statement stmt; // declare a stmt of type Statement
        Token tok = lex.getLookaheadToken(); // lexer searches for next token
        if (tok.getTokType() == TokenType.DISPLAY_TOK) // if the keyword is "display"
            stmt = getPrintStatementInter();  // stmt is a print statement, so set it equal to getPrintStatement().
        else if (tok.getTokType() == TokenType.DEFINE_TOK)
            stmt = getAssignmentStatementInter();
        else if (tok.getTokType() == TokenType.SET_TOK)
            stmt = getArithmeticExpressionInter();
         else if (tok.getTokType() == TokenType.RETURN_TOK)
            stmt = getReturnStatementInter();
        else  // If the lexeme found isn't specified in the if/else block throw a parser exception.
        throw new ParserException ("invalid statement at row " +
                    tok.getRowNumber()  + " and column " + tok.getColumnNumber());
        
        return stmt;
    }
    
    /*
    Method name: getPrintStatement()
    What it does: This defines the form of a print statement and stores it
    in a String variable named expr which is then passed to PrintStatement. 
    Essentially it says that print statements with the keyword "display" 
    followed by a quote "\"", followed by a string of characters, that is 
    then enclosed in another quote "\"". 
    */
    private Statement getPrintStatement() throws ParserException, LexicalException
    {
        String expr = "";
        Token tok = lex.getNextToken();
        match (tok, TokenType.DISPLAY_TOK);
        expr+= "display ";
        tok = lex.getNextToken ();
        match (tok, TokenType.QUOTE_TOK);
        expr += "\"";
        tok = lex.getNextToken();
        match (tok, TokenType.ID_TOK);
        expr += tok.getLexeme();
        tok = lex.getNextToken ();
        match (tok, TokenType.QUOTE_TOK);
        expr += "\"";
        return new PrintStatement (expr);
    } 
    private Statement getPrintStatementInter() throws ParserException, LexicalException
    {
        String expr = "";
        Token tok = lex.getNextToken();
        match (tok, TokenType.DISPLAY_TOK);
        tok = lex.getNextToken ();
        match (tok, TokenType.QUOTE_TOK);
        tok = lex.getNextToken();
        match (tok, TokenType.ID_TOK);
        expr += tok.getLexeme();
        tok = lex.getNextToken ();
        match (tok, TokenType.QUOTE_TOK);
        return new PrintStatementInter (expr);
    } 
    
    private Statement getReturnStatementInter() throws ParserException, LexicalException
    {
        String expr = "\b";
        Token tok = lex.getNextToken();
        match (tok, TokenType.RETURN_TOK);
        tok = lex.getNextToken();
        match (tok, TokenType.ID_TOK);
        return new ReturnStatementInter (expr);
    } 
    private Statement getReturnStatement() throws ParserException, LexicalException
    {
        String expr = "";
        Token tok = lex.getNextToken();
        match (tok, TokenType.RETURN_TOK);
        expr+= "return ";
        tok = lex.getNextToken();
        match (tok, TokenType.ID_TOK);
        expr += tok.getLexeme();
        return new ReturnStatement (expr);
    }
    /*
    Method name: getStatement() 
    What it does: This determines what getter methods are associated with their respective 
    keywords (every statement begins with a keyword, for example all print statements start
    with the keyword "display"). 
     */
    private Statement getStatement() throws ParserException, LexicalException
    {
        Statement stmt; // declare a stmt of type Statement
        Token tok = lex.getLookaheadToken(); // lexer searches for next token
        if (tok.getTokType() == TokenType.DISPLAY_TOK) // if the keyword is "display"
            stmt = getPrintStatement();  // stmt is a print statement, so set it equal to getPrintStatement().
        else if (tok.getTokType() == TokenType.DEFINE_TOK)
            stmt = getAssignmentStatement();
        else if (tok.getTokType() == TokenType.SET_TOK)
            stmt = getArithmeticExpression();
            else if (tok.getTokType() == TokenType.RETURN_TOK)
            stmt = getReturnStatement();
        else  // If the lexeme found isn't specified in the if/else block throw a parser exception.
        throw new ParserException ("invalid statement at row " +
                    tok.getRowNumber()  + " and column " + tok.getColumnNumber());
        
        return stmt;
    }
    /*
    Method name: getArithmeticExpression()
    What it does: It evaluates the correctness of arithmetic expressions and determines if they
    are in accordance with the grammar. The syntax for arithmetic statements in SCL are:
    set <var> = <const/var> <operator> <const/var>
    First it declares an empty string expr. Then it checks to see if the statement in the file
    starts with the keyword set. If it does, then it adds "set" to expr then checks for the next 
    token which must be ASSIGN_TOK (=). It then adds "=" to expr. After, it checks for the next 
    constant or variable then adds it. Next there is an if/else block for operators. It checks to
    see what operator is between the two vars/consts and adds it to expr. Then the next var/const is added.
    */
    private Statement getArithmeticExpression() throws ParserException, LexicalException
    {
        String expr = "";   //declare an empty string
        Token tok = lex.getNextToken(); // get the next token
        match (tok, TokenType.SET_TOK); //ALL arithmetic expressions start with keyword set
        expr += tok.getLexeme() + " "; // add lexeme to expr
        tok = lex.getNextToken();
        match (tok, TokenType.ID_TOK); //asks for the variable after set
        expr += tok.getLexeme();
        tok = lex.getNextToken ();
        match (tok, TokenType.ASSIGN_TOK); //asks for = sign. So far it is set <var> =
        expr += " " + tok.getLexeme() + " ";
        tok = lex.getNextToken(); // after = we can either have a constant or another variable
        if (tok.getTokType() == TokenType.CONST_TOK) 
        {
        match (tok, TokenType.CONST_TOK); //if it's a constant add it to expr
        expr += tok.getLexeme();
        } else {
        match (tok, TokenType.ID_TOK); //if it's a variable add it to expr
        expr += tok.getLexeme(); //the if/else block below matches this lexeme to a defined operator
        } 
        tok = lex.getNextToken();
        if (tok.getTokType() == TokenType.ADD_TOK)
        {
            match (tok, TokenType.ADD_TOK);
            expr += " " + tok.getLexeme();
        }
        else if (tok.getTokType() == TokenType.SUB_TOK)
        {
            match (tok, TokenType.SUB_TOK);
            expr += " " + tok.getLexeme();
        }
        else if (tok.getTokType() == TokenType.MULT_TOK)
        {
            match (tok, TokenType.MULT_TOK);
            expr += " " + tok.getLexeme() + " ";
        }
        else if (tok.getTokType() == TokenType.DIV_TOK)
        {
            match (tok, TokenType.DIV_TOK);
            expr += " " + tok.getLexeme();
        }
        else if (tok.getTokType() == TokenType.EXP_TOK)
        {
            match (tok, TokenType.EXP_TOK);
            expr += " " + tok.getLexeme();
        }
        else if (tok.getTokType() == TokenType.MOD_TOK)
        {
            match (tok, TokenType.MOD_TOK);
            expr += " " + tok.getLexeme() + " ";
        }
        else {
            throw new ParserException (" operator expected at row " +
                    tok.getRowNumber() +" and column "  + tok.getColumnNumber());
        }
        tok = lex.getNextToken(); //after the operator, we can have either a constant or a variable
        if (tok.getTokType() == TokenType.CONST_TOK)
        {
        match (tok, TokenType.CONST_TOK);
        expr += tok.getLexeme();
        } else {
        match (tok, TokenType.ID_TOK);
        expr += " " + tok.getLexeme();
        }
        return new ArithmeticExpression(expr); //send expr to ArithmeticExpression AND block
    }

    private Statement getArithmeticExpressionInter() throws ParserException, LexicalException
    {
        String expr = "";
        Token tok = lex.getNextToken(); // get the next token
        match (tok, TokenType.SET_TOK); //ALL arithmetic expressions start with keyword set
        tok = lex.getNextToken();
        match (tok, TokenType.ID_TOK); //asks for the variable after set
        tok = lex.getNextToken ();
        match (tok, TokenType.ASSIGN_TOK); //asks for = sign. So far it is set <var> =
        tok = lex.getNextToken(); // after = we can either have a constant or another variable
        if (tok.getTokType() == TokenType.CONST_TOK) 
        {
        match (tok, TokenType.CONST_TOK); //if it's a constant add it to expr
        expr += tok.getLexeme();
        } else {
        match (tok, TokenType.ID_TOK); //if it's a variable add it to expr
        expr += tok.getLexeme(); //the if/else block below matches this lexeme to a defined operator
        } 
        tok = lex.getNextToken();
        if (tok.getTokType() == TokenType.ADD_TOK)
        {
            match (tok, TokenType.ADD_TOK);
            expr += " " + tok.getLexeme() + " ";
        }
        else if (tok.getTokType() == TokenType.SUB_TOK)
        {
            match (tok, TokenType.SUB_TOK);
            expr += " " + tok.getLexeme() + " ";
        }
        else if (tok.getTokType() == TokenType.MULT_TOK)
        {
            match (tok, TokenType.MULT_TOK);
            expr += " " + tok.getLexeme() + " ";
        }
        else if (tok.getTokType() == TokenType.DIV_TOK)
        {
            match (tok, TokenType.DIV_TOK);
            expr += " " + tok.getLexeme() + " ";
        }
        else if (tok.getTokType() == TokenType.EXP_TOK)
        {
            match (tok, TokenType.EXP_TOK);
            expr += " " + tok.getLexeme();
        }
        else if (tok.getTokType() == TokenType.MOD_TOK)
        {
            match (tok, TokenType.MOD_TOK);
            expr += " " + tok.getLexeme() + " ";
        }
        else {
            throw new ParserException (" operator expected at row " +
                    tok.getRowNumber() +" and column "  + tok.getColumnNumber());
        }
        tok = lex.getNextToken(); //after the operator, we can have either a constant or a variable
        if (tok.getTokType() == TokenType.CONST_TOK)
        {
        match (tok, TokenType.CONST_TOK);
        expr += tok.getLexeme();
        } else {
        match (tok, TokenType.ID_TOK);
        expr += " " + tok.getLexeme();
        } //BEGINNING EXPERIMENT
        /*Scanner sc = new Scanner(expr);
        // get the next number from the scanner
        int firstValue = Integer.parseInt(sc.findInLine("[0-9]*"));
        int value;
        // get everything which follows and is not a number (might contain white spaces)
        String operator = sc.findInLine("[^0-9]*").trim();
        int secondValue = Integer.parseInt(sc.findInLine("[0-9]*"));
            if(operator.equals("+")) {
                value = firstValue + secondValue;
                expr = Integer.toString(value);
            } else if(operator.equals("-")) {
                value = firstValue - secondValue;
                System.out.println(value);
                expr+=value;
            } else if(operator.equals("/")) {
                value = firstValue / secondValue;
                expr = Integer.toString(value);
            } else if(operator.equals("*")) {
                value = (int)(firstValue * secondValue);
                expr = Integer.toString(value);
            } else if(operator.equals("%")) {
                value = (int) firstValue % secondValue;
                expr = Integer.toString(value);
            // todo: add additional operators as needed..
            }else{
                throw new RuntimeException("unknown operator: "+operator);
            
         //send expr to ArithmeticExpression AND block
         */
    return new ArithmeticExpressionInter(expr);
}

    /*
    Method name: getId() 
    What it does: This is useful for assignment or expressions of the form
    <Id> = <expr> <op> <expr> because it finds the LHS token. 
     */
    private Id getId() throws LexicalException, ParserException
    {
        Token tok = lex.getNextToken();
        match (tok, TokenType.ID_TOK);
        return new Id (tok.getLexeme());
    } 
 
     /*
    Method name: getAssignmentStatement() 
    What it does: It checks for syntactic correctness of statements of the form:
    define <var> = <const>
    An empty string expr is declared. The it checks to see if the statement starts with define
    and adds define to expr. The next token must be an ASSIGN_TOK (=) which is checked for then
    added to expr. Then the constant is added to expr. 
     */
    private Statement getAssignmentStatement() throws ParserException, LexicalException
    {
        String expr = "";
        Token tok = lex.getNextToken();
        match (tok, TokenType.DEFINE_TOK);
        expr+= "define ";
        tok = lex.getNextToken();
        match (tok, TokenType.ID_TOK);
        expr+= tok.getLexeme();
        tok = lex.getNextToken ();
        match (tok, TokenType.ASSIGN_TOK);
        expr += " = ";
        tok = lex.getNextToken();
        match (tok, TokenType.CONST_TOK);
        expr += tok.getLexeme();
        return new AssignmentStatement (expr);
    }

    private Statement getAssignmentStatementInter() throws ParserException, LexicalException
    {
        String expr = "";
        Token tok = lex.getNextToken();
        match (tok, TokenType.DEFINE_TOK);
        tok = lex.getNextToken();
        match (tok, TokenType.ID_TOK);
        tok = lex.getNextToken ();
        match (tok, TokenType.ASSIGN_TOK);
        tok = lex.getNextToken();
        match (tok, TokenType.CONST_TOK);
        expr += tok.getLexeme();
        return new AssignmentStatementInter (expr);
    }
    

    /*
    Method name: isValidStartOfStatement() 
    What it does: It's a boolean that accepts tokens and determines if 
    statements can begin with these keywords. For example, if a statement starts
    with just a symbol (say QUOTE_TOK) that is not a legal statement so 
    isValidStartOfStatement() will return false. 
     */
    private boolean isValidStartOfStatement(Token tok)
    {
        assert (tok != null);
        return tok.getTokType() == TokenType.DISPLAY_TOK ||
        tok.getTokType() == TokenType.DEFINE_TOK ||
        tok.getTokType() == TokenType.SET_TOK ||
        tok.getTokType() == TokenType.FUNCTION_TOK ||
        tok.getTokType() == TokenType.RETURN_TOK ||
        tok.getTokType() == TokenType.ID_TOK;
    }

    /*
    Method name: match()
    What it does: it matches the lexeme to tokens. If it doesn't match it throws
    a parser exception. 
     */
    private void match(Token tok, TokenType tokType) throws ParserException
    {
        assert (tok != null && tokType != null);
        if (tok.getTokType() != tokType)
            throw new ParserException (tokType.name() + " expected at row " +
                    tok.getRowNumber() +" and column "  + tok.getColumnNumber());
    }
}