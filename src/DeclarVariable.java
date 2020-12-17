import java.util.*;

public class DeclarVariable  extends Instruction{

    private String type;
    private ArrayList<Idf> idfs;

    public DeclarVariable(String type, ArrayList<Idf> idfs, String fl, int line, int col){
        super(fl, line, col);
        this.type = type;
        this.idfs = idfs;
    }

    public String getType(){
        return this.type;
    }

    public ArrayList<Idf> getIds(){
        return this.idfs;
    }

    public void lierType(String type){
        this.type = type;
    }

    public void lierIds(ArrayList<Idf> idfs){
        this.idfs = idfs;
    }

    @Override
    public String toString(){
        return "Declaration de Variable: ";
    }

    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }
}
