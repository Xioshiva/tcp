import java.util.Map;

public class AnalyseurSementique implements ASTVisitor {
    
    private Map<String, Object> TDS;
    private Map<String, Boolean> constantOrNot;


    public AnalyseurSementique(Map<String, Object> TDS,Map<String, Boolean> constantOrNot){
        this.TDS = TDS;
        this.constantOrNot = constantOrNot; 
    }


    public Class<?> getTheClass(Object node){
        Class<?> className = null;
        if(node.getClass() == Nombre.class){
            className = Nombre.class;
        }else if(node.getClass() == Vrai.class || node.getClass() == Faux.class){
            className = Vrai.class;
        }else if(node.getClass() == Idf.class ){
            if(TDS.containsKey(((Idf)node).getNom())){
                className = TDS.get(((Idf)node).getNom()).getClass();
            }else{
                throw new RuntimeException("Variable non déclarée à la ligne: " + ((ASTNode)node).getLine());
            }            
        }
        return className;
    }

    public boolean binaryOperationIsOkay(Binary node){
        Class<?> classDroite = getTheClass(node.getDroite().accept(this));
        Class<?> classGauche = getTheClass(node.getGauche().accept(this));

        //System.out.println(classDroite.toString() +  " - " + classGauche.toString());
        return classGauche == classDroite;
    }


    public Object visit(Addition node){

        if(!binaryOperationIsOkay(node)){
            throw new RuntimeException("Opération entre deux types différents à la ligne: " + node.getLine());
        }

        return node;
    }

    public Object visit(Assignment node){
        Object dst = node.getDestination().accept(this);
        Object src = node.getSource().accept(this);

        //Dst est forcéement un Idf, par contre src peut être binaire, ou oper.
        if(src instanceof Binary){
            Class<?> srcClass = getTheClass(((Binary)src).getGauche());
            if(srcClass != null && srcClass != getTheClass(dst)){
                throw new RuntimeException("Affactation illégale à la ligne : " + node.getLine());
            }
        }else if(src.getClass() != getTheClass(dst)) {
            throw new RuntimeException("Affactation illégale à la ligne : " + node.getLine());
        }
        return node;
    }


    //Pas sur que l'analyseur sémentique à besoin d'analyser un bloc
    public Object visit(Block node){
        node.getInstructions().forEach(i -> i.accept(this));
        return node;
    }

    public Object visit(SiAlors node){
        return node;
    }

    public Object visit(SiAlorsSinon node){
        return node;
    }

    public Object visit(DeclarConst node){
        node.getExpression().accept(this);
        return node;
    }

    public Object visit(DeclarVar node){
        node.getIds().forEach(i -> i.accept(this));
        return node;
    }

    public Object visit(ProgramDeclaration node){
        System.out.println("ProgramDeclaration");

        node.getIdentifier().accept(this);
        node.getDeclaration().accept(this);
        node.getInstructions().accept(this);
        return node;
    }

    public Object visit(Different node){
        return node;
    }

    public Object visit(Division node){
        if(!binaryOperationIsOkay(node)){
            throw new RuntimeException("Opération entre deux types différents à la ligne: " + node.getLine());
        }

        return node;
    }

    public Object visit(Ecrire node){
        return node;
    }

    public Object visit(Egal node){
        System.out.println("Egal");
        node.getGauche().accept(this);
        node.getGauche().accept(this);
        
        return node;
    }

    public Object visit(Et node){
        return node;
    }

    public Object visit(Faux node){
        return node;
    }

    public Object visit(Idf node){
        System.out.println("Idf: " + node.toString());
        return node;
    }

    public Object visit(InfEgal node){
        return node;
    }

    public Object visit(Inferieur node){
        return node;
    }

    public Object visit(Lire node){
        return node;
    }

    public Object visit(Moins node){
        return node;
    }

    public Object visit(Nombre node){
        return node;
    }

    public Object visit(Non node){
        return node;
    }

    public Object visit(Ou node){
        return node;
    }

    public Object visit(Parenthese node){
        return node;
    }

    public Object visit(Pour node){
        return node;
    }

    public Object visit(Multiplication node){
        if(!binaryOperationIsOkay(node)){
            throw new RuntimeException("Opération entre deux types différents à la ligne: " + node.getLine());
        }

        return node;
    }

    public Object visit(Soustraction node){
        if(!binaryOperationIsOkay(node)){
            throw new RuntimeException("Opération entre deux types différents à la ligne: " + node.getLine());
        }

        return node;
    }

    public Object visit(SupEgal node){
        return node;
    }

    public Object visit(Superieur node){
        return node;
    }

    public Object visit(TantQue node){
        return node;
    }

    public Object visit(Tilda node){
        return node;
    }

    public Object visit(Vrai node){
        return node;
    }

    public Object visit(Unary node){
        return node;
    }


}
