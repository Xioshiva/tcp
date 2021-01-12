import java.util.Map;

public class GenerateurDeCode implements ASTVisitor {

    private Map<String, Object> TDS;
    private Map<String, Boolean> constantOrNot;

    // Pour distinguer de si oui ou non on se trouve dans le bloc des declaration
    private boolean positionBloc = false;
    

    public GenerateurDeCode(Map<String, Object> TDS, Map<String, Boolean> constantOrNot) {
        this.TDS = TDS;
        this.constantOrNot = constantOrNot;
    }

    public Object visit(Addition node) {

        return node;
    }

    public Object visit(Assignment node) {
        return node;
    }

    public Object visit(Block node) {
        node.getInstructions().forEach(i -> i.accept(this));
        return node;
    }

    public Object visit(SiAlors node) {
        node.getExpr().accept(this);
        node.getInstr().forEach(i -> i.accept(this));
        return node;
    }

    public Object visit(SiAlorsSinon node) {
        node.getExpr().accept(this);
        node.getInstr().forEach(i -> i.accept(this));
        node.getInstr2().forEach(i -> i.accept(this));
        return node;
    }

    public Object visit(DeclarConst node) {
 
        return node;
    }

    public Object visit(DeclarVar node) {
        node.getIds().forEach(i -> i.accept(this));
        return node;
    }

    public Object visit(ProgramDeclaration node) {

        node.getDeclaration().accept(this);
        node.getIdentifier().accept(this);

        // On n'est plus dans les déclarations, du coup on peut intérdire
        // maintenant les affectations aux constantes
        this.positionBloc = true;
        node.getInstructions().accept(this);
        return node;
    }

    public Object visit(Different node) {
  
        return node;
    }

    public Object visit(Division node) {
        return node;
    }

    public Object visit(Ecrire node) {
        return node;
    }

    public Object visit(Egal node) {
        return node;
    }

    public Object visit(Et node) {
    
        return node;
    }

    public Object visit(Faux node) {
        return node;
    }

    public Object visit(Idf node) {
        return node;
    }

    public Object visit(InfEgal node) {
        return node;
    }

    public Object visit(Inferieur node) {
        return node;
    }

    public Object visit(Lire node) {
        return node;
    }

    public Object visit(Moins node) {
        return node;
    }

    public Object visit(Plus node) {
        return node;
    }

    public Object visit(Nombre node) {
        return node;
    }

    public Object visit(Non node) {
        return node;
    }

    public Object visit(Ou node) {
        return node;
    }

    public Object visit(Parenthese node) {
        node.getExpression().accept(this);
        return node.getExpression();
    }

    public Object visit(Pour node) {
        node.getInstr().forEach(i -> i.accept(this));
        return node;
    }

    public Object visit(Multiplication node) {
        return node;
    }

    public Object visit(Soustraction node) {
        return node;
    }

    public Object visit(SupEgal node) {
        return node;
    }

    public Object visit(Superieur node) {
        return node;
    }

    public Object visit(TantQue node) {
        node.getExpr().accept(this);
        node.getInstr().forEach(i -> i.accept(this));
        return node;
    }

    public Object visit(Tilda node) {
        node.getExpression().accept(this);
        return node;
    }

    public Object visit(Vrai node) {
        return node;
    }

    public Object visit(Unary node) {

        return node;
    }

}
