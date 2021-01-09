import java.util.Vector;
import java.io.FileReader;
import java_cup.runtime.Symbol;
import java.util.HashMap;

public class Hepialc {
    public static void main(String[] arg) {
        // parser myP = new parser(new HepialLexer(new FileReader(arg[1])));
        try { FileReader myFile = new FileReader(arg[0]);
                //Lexicale
                HepialLexer myLex = new HepialLexer(myFile);
                parser myP = new parser(myLex);
                //Syntaxique
                ProgramDeclaration program = (ProgramDeclaration)myP.parse().value;
                HashMap<String, Object> TDS = program.getTDS();
                HashMap<String, Boolean> constantOrNot = program.getConstantOrNot();
                //Sémentique
                AnalyseurSementique as = new AnalyseurSementique(TDS, constantOrNot);
                program.accept(as);
                System.out.println("Looking good!");
                //Gen de code
            
                try {
                }
                catch (Exception e) {

                    System.out.println("parse error");
                }
            }
            catch (Exception e){
                e.printStackTrace();
                System.out.println("invalid file");
            }
    }
}
