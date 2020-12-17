import java.util.Vector;
import java.io.FileReader;
import java_cup.runtime.Symbol;

public class Hepialc {
    public static void main(String[] arg) {
            // parser myP = new parser(new HepialLexer(new FileReader(arg[1])));
            try { FileReader myFile = new FileReader(arg[0]);
                HepialLexer myLex = new HepialLexer(myFile);
                parser myP = new parser(myLex);
                try {myP.parse();}
                catch (Exception e) {
                    System.out.println("parse error");
                }
            }
            catch (Exception e){
                System.out.println("invalid file");
            }
    }
}
