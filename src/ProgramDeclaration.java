/*
 * Represent a function declaration instruction node inside the AST.
*/

public class ProgramDeclaration extends Instruction {
    /**
     * The declared variable identifier
     */
    protected Idf identifier;
    /**
     * The declaration section of the function (list of DeclarationVariable)
     */
    protected Block declarations;
    /**
     * The body of the function
     */
    protected Block instructions;

    /**
     * Constructor
     */
    public ProgramDeclaration(Idf identifier, String fl, int line, int col){
        super(fl, line, col);
        this.identifier = identifier;
    }

    /**
     * Get the declared variable identifier
     */
    public Idf getIdentifier() {
        return this.identifier;
    }
    /**
     * Get the declaration section of the function
     */
    public Block getDeclaration() {
        return this.declarations;
    }
    /**
     * Get the body of the function
     */
    public Block getInstructions() {
        return this.instructions;
    }

    /**
     * Set the declared variable identifier
     */
    public void setIdentifier(Idf identifier) {
        this.identifier = identifier;
    }
    /**
     * Set the declarations section of the function
     */
    public void setDeclarations(Block declarations) {
        this.declarations = declarations;
    }
    /**
     * Set the body of the function
     */
    public void setInstructions(Block instructions) {
        this.instructions = instructions;
    }

    /**
     * Accepts a AST visitor
     */
    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }
}
