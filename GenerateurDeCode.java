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

/*     public Class<?> getTheClass(Object node) {
        Class<?> className = null;
        if (node.getClass() == Nombre.class) {
            className = Nombre.class;
        } else if (node.getClass() == Vrai.class || node.getClass() == Faux.class || node instanceof Relation) {
            // On traite tout les booleens comme la classe Vrai quand on compare les classes
            // pour la sémentique
            className = Vrai.class;
        } else if (node.getClass() == Idf.class) {
            if (TDS.containsKey(((Idf) node).getNom())) {
                className = TDS.get(((Idf) node).getNom()).getClass();
            } else {
                throw new RuntimeException("Variable non déclarée à la ligne: " + ((ASTNode) node).getLine());
            }
        }
        return className;
    } */

  /*   public boolean binaryOperationIsOkay(Binary node) {

        // System.out.println("Node: " + node.getClass().toString());

        // System.out.println("\t" + node.getGauche().accept(this).toString() + " <-> "
        // + node.getDroite().accept(this).toString());

        // Flag pour les parentheses
        boolean leftOkay = true;
        boolean rightOkay = true;
        boolean okayFlag = false;

        Class<?> classDroite = getTheClass(node.getDroite().accept(this));
        Class<?> classGauche = getTheClass(node.getGauche().accept(this));

        if (node.getDroite() instanceof Parenthese) {
            if (((Parenthese) node.getDroite()).getExpression() instanceof Binary) {
                rightOkay = binaryOperationIsOkay(((Binary) ((Parenthese) node.getDroite()).getExpression()));
                okayFlag = true;
            } else { // C'est à dire qu'on a juste mit un nombre ou un boolean dans la parenthese
                     // comme ça: (3)
                classDroite = getTheClass(((Parenthese) node.getDroite()).getExpression());
                rightOkay = true;
            }
        }

        if (node.getGauche() instanceof Parenthese) {
            if (((Parenthese) node.getGauche()).getExpression() instanceof Binary) {
                leftOkay = binaryOperationIsOkay(((Binary) ((Parenthese) node.getGauche()).getExpression()));
                okayFlag = true;
            } else { // C'est à dire qu'on a juste mit un nombre ou un boolean dans la parenthese
                     // comme ça: (3)
                classGauche = getTheClass(((Parenthese) node.getGauche()).getExpression());
                leftOkay = true;
            }
        }
        if (okayFlag) {
            return leftOkay && rightOkay;
        }
        return classGauche == classDroite;
    } */

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
