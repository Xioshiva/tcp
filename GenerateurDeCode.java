import java.util.Map;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class GenerateurDeCode implements ASTVisitor {

    private Map<String, Object> TDS;
    private Map<String, Boolean> constantOrNot;

    // Pour distinguer de si oui ou non on se trouve dans le bloc des declaration
    private boolean positionBloc = false;

    private ArrayList<String> indexes = new ArrayList<>();

    private String tgtCode = "";

    private int labelIndex = 0;


    public String code(){
        return this.tgtCode;
    }


    // A chaque fois qu'il faut manipuler une variable,
    // Il faut vérifier son index; et si l'index est -1;
    // Alors il faut l'ajouter à la liste et utiliser
    // this.indexes.size() comme nouvel index.
    private int indexVariable(String nom) {
        int index = 0;
        for (String s : this.indexes) {
            if (indexes.get(index).equals(nom))
                return index;
            index += 1;
        }
        return -1;
    }

    public GenerateurDeCode(Map<String, Object> TDS, Map<String, Boolean> constantOrNot) {
        this.TDS = TDS;
        this.constantOrNot = constantOrNot;
    }

    public Object visit(Addition node) {
        node.getGauche().accept(this);
        node.getDroite().accept(this);
        this.tgtCode += "iadd\n";
        return node;
    }

    public Object visit(Assignment node) {
        node.getSource().accept(this);
        this.tgtCode += "istore " + indexVariable(node.getDestination().getNom()) + "\n";

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

    public void ecrireDeclaration(Idf node) {
        String id = node.getNom();
        Class<?> clas = TDS.get(id).getClass();
        if (indexVariable(id) != -1) {
            throw new RuntimeException("Variable déjà déclarée!");
        } else {
            this.tgtCode += ".var " + this.indexes.size() + " is " + id;
            this.indexes.add(id);
        }
        if (clas == Nombre.class) {
            this.tgtCode += " I\n";
        } else if (clas == Vrai.class || clas == Faux.class ) {
            this.tgtCode += " Z\n";
        } else {
            System.out.println(clas);
            throw new RuntimeException("Type inconnu à la ligne: " + node.getLine());
        }
    }

    public Object visit(DeclarConst node) {
        ecrireDeclaration(node.getId());
        node.getExpression().accept(this);
        this.tgtCode += "istore " + indexVariable(node.getId().getNom()) + "\n";
        return node;
    }

    public Object visit(DeclarVar node) {

        ArrayList<Idf> idfs = node.getIds();
        for (Idf i : idfs) {
            ecrireDeclaration(i);
        }
        return node;
    }

    public Object visit(ProgramDeclaration node) {

        this.tgtCode += ".class public " + node.getIdentifier().getNom() + "\n";
        this.tgtCode += ".super java/lang/Object\n";
        this.tgtCode += ".method public static main([Ljava/lang/String;)V\n";
        this.tgtCode += ".limit stack 20000\n";
        this.tgtCode += ".limit locals 100\n";




        node.getDeclaration().accept(this);
        // On n'est plus dans les déclarations, du coup on peut intérdire
        // maintenant les affectations aux constantes
        this.positionBloc = true;
        node.getInstructions().accept(this);
        this.tgtCode += "return\n";
        this.tgtCode += ".end method\n";

        return node;
    }

    public Object visit(Different node) {
        node.getDroite().accept(this);
        node.getGauche().accept(this);
        this.tgtCode += "if_icmpne\n";
        return node;
    }

    public Object visit(Division node) {
        node.getGauche().accept(this);
        node.getDroite().accept(this);
        this.tgtCode += "idiv\n";
        return node;
    }

    public Object visit(Ecrire node) {
        if(node.getValeur() != null){
            this.tgtCode +="getstatic java/lang/System/out Ljava/io/PrintStream;\n";
            this.tgtCode += "ldc " + node.getValeur() +"\n";
            this.tgtCode +="invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n";
        }else{
            node.getExpr().accept(this);
            this.tgtCode +="getstatic java/lang/System/out Ljava/io/PrintStream;\n";
            this.tgtCode +="swap\n";
            this.tgtCode +="invokevirtual java/io/PrintStream/print(I)V\n";
        }
        return node;
    }

    public Object visit(Egal node) {
        node.getDroite().accept(this);
        node.getGauche().accept(this);
        this.tgtCode += "if_icmpeq\n";
        return node;
    }

    public Object visit(Et node) {
        node.getDroite().accept(this);
        node.getGauche().accept(this);
        this.tgtCode += "iand\n";
        return node;
    }

    public Object visit(Faux node) {
        tgtCode += "ldc 0\n";
        return node;
    }


    //On utilise ça seulement dans le cas ou le Idf est "à droite" du égal
    //Que pour les iload
    public Object visit(Idf node) {
        this.tgtCode += "iload " + indexVariable(node.getNom()) + "\n"; 
        return node;
    }

    public Object visit(InfEgal node) {
        node.getDroite().accept(this);
        node.getGauche().accept(this);
        this.tgtCode += "if_icmple\n";
        return node;
    }

    public Object visit(Inferieur node) {
        node.getDroite().accept(this);
        node.getGauche().accept(this);
        this.tgtCode += "if_icmplt\n";
        return node;
    }

    public Object visit(Lire node) {
        return node;
    }

    public Object visit(Moins node) {
        node.getExpression().accept(this);
        this.tgtCode += "ineg\n";
        return node;
    }

    public Object visit(Plus node) {
        node.getExpression().accept(this);
        this.tgtCode += "iadd\n";
        return node;
    }

    public Object visit(Nombre node) {
        this.tgtCode += "ldc " + node.getValeur() + "\n";
        return node;
    }

    public Object visit(Non node) {
        node.getExpression().accept(this);
        
        this.tgtCode += "ldc 1\n";
        //Si la valeur qu'on a dans la pile est égale à 1;
        //Alors on met 0 en haut de la pile;
        int tmp = labelIndex;
        this.tgtCode += "ifeq label_" + labelIndex + "\n";
        labelIndex++;
        this.tgtCode += "ldc 0\n";
        tgtCode += "label_: " + tmp + "\n";
        //Sinon on laisse le 1 qu'on a écrit en haut.
        return node;
    }

    public Object visit(Ou node) {
        node.getGauche().accept(this);
        node.getDroite().accept(this);
        this.tgtCode +="ior\n";
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
        node.getGauche().accept(this);
        node.getDroite().accept(this);
        this.tgtCode += "imult\n";
        return node;
    }

    public Object visit(Soustraction node) {
        node.getGauche().accept(this);
        node.getDroite().accept(this);
        this.tgtCode += "isub\n";
        return node;
    }

    public Object visit(SupEgal node) {
        node.getDroite().accept(this);
        node.getGauche().accept(this);
        this.tgtCode += "if_icmpge\n";
        return node;
    }

    public Object visit(Superieur node) {
        node.getDroite().accept(this);
        node.getGauche().accept(this);
        this.tgtCode += "if_icmpgt\n";
        return node;
    }

    public Object visit(TantQue node) {
        node.getExpr().accept(this);
        node.getInstr().forEach(i -> i.accept(this));
        return node;
    }

    public Object visit(Tilda node) {
        this.tgtCode += "ldc 1\n";
        this.tgtCode += "ineg\n";
        node.getExpression().accept(this);
        this.tgtCode += "ixor\n";
        return node;
    }

    public Object visit(Vrai node) {
        this.tgtCode += "ldc 1\n";
        return node;
    }

    public Object visit(Unary node) {
        return node;
    }

}
