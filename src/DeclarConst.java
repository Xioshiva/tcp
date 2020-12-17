import java.util.*;

public class DeclarConst  extends Instruction{

    private String type;
    private Idf idf;
    private Expression expr;

    public DeclarConst(String type, Idf idf, Expression expr, String fl, int line, int col){
        super(fl, line, col);
        this.type = type;
        this.idf = idf;
        this.expr = expr;
    }

    public String getType(){
        return this.type;
    }

    public Idf getId(){
        return this.idf;
    }

    public Expression getExpression(){
        return this.expr;
    }

    public void lierType(String type){
        this.type = type;
    }

    public void lierIdf(Idf idf){
        this.idf = idf;
    }

    public void lierExpression(Expression expr){
        this.expr = expr;
    }

    @Override
    public String toString(){
        return "Declaration de Constante: ";
    }

    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }
}
