/*
Group: Chloe Chung and Karan Munday
File: Interpreter.java
File purpose: This is the interpreter. It takes the file and sends it to the lexical analyzer, parser, 
and interprets the file (i.e. produces the output). 
 */
import java.io.FileNotFoundException; /* throws an exception if no file is entered */

public class Interpreter { /* This is the tester */

            public static void main(String[] args) {
                try
                {
                    Parser pa = new Parser("welcome.scl"); //pass the file being tested to the parser
                    System.out.println("---------------BEGINNING OF INTERPRETING--------------");
                    ProgramInter pro = pa.parseInter(); //this executes the stat*ements in the block
                    pro.execute(); //execute the block statements
                    System.out.println("-----------------FINISHED INTERPRETING----------------");
                    System.out.println();
                    Parser p = new Parser("welcome.scl"); //pass the file being tested to the parser
                    System.out.println("-----------------BEGINNING OF PARSING-----------------");
                    Program prog = p.parse(); //this executes the statements in the block
                    prog.execute(); //execute the block statements
                    System.out.println("-------------------FINISHED PARSING-------------------");
                    System.out.println();
                    LexicalAnalyzer lex = new LexicalAnalyzer("welcome.scl"); //send the file to the lexical analyzer
                    System.out.println("-----------------BEGINNING OF SCANNING-----------------");
                    lex.printLex(); //print the lexemes
                    System.out.println("-------------------FINISHED SCANNING-------------------");
                    //Memory.displayMemory(); // to see results of assignment statement
                }
                catch (ParserException e)
                {
                    e.printStackTrace();
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (LexicalException e)
                {
                    e.printStackTrace();
                }
            }
        
        }