/*
Group: Chloe Chung and Karan Munday
File: TokenType.java
File purpose: Define the token names. Tokens are groups that meaningful lexemes fall into.
 */

/* An enum is a named group of constants. 
This is an enum containing the tokens used by the lexical analyzer */
public enum TokenType 
{   
    /* These are all symbol tokens for general use and arithmitic */
    ID_TOK, ADD_TOK, ASSIGN_TOK, COLON_TOK, CONST_TOK, EOS_TOK,
    SUB_TOK, DIV_TOK, EXP_TOK, MOD_TOK, COMMA_TOK,
    LE_TOK, LT_TOK, MULT_TOK, GE_TOK, GT_TOK, EQ_TOK, NE_TOK,
    LEFT_PAREN_TOK, RIGHT_PAREN_TOK, LEFT_BRACK_TOK, QUOTE_TOK, RIGHT_BRACK_TOK,

    /* These are keywords used by the lexical analyzer */
    ARRAY_TOK, BEGIN_TOK, CONSTANT_TOK, DECLARATIONS_TOK, DEFINE_TOK, DISPLAY_TOK, 
    DO_TOK, ELSE_TOK, ENDFUN_TOK, ENDIF_TOK,  ENDREPEAT_TOK, 
    ENDWHILE_TOK, ENUM_TOK, EXIT_TOK, FORWARD_TOK, FUNCTION_TOK, GLOBAL_TOK, 
    IF_TOK, IMPLEMENTATIONS_TOK, IMPORT_TOK, INTEGER_TOK, IS_TOK, MAIN_TOK, 
    PARAMETERS_TOK, POINTER_TOK, REFERENCES_TOK, REPEAT_TOK, 
    RETURN_TOK, SET_TOK, SPECIFICATIONS_TOK, STRUCT_TOK, SYMBOL_TOK, 
    THEN_TOK, TYPE_TOK, UNTIL_TOK, WHILE_TOK
}