package parser.ast;

public class VarDeclarationNode extends ASTNode {
  public final String identefier;
  public final VarType type;
  public final ASTNode expression;

  public VarDeclarationNode(String identefier, VarType type, ASTNode expression) {
    this.identefier = identefier;
    this.type = type;
    this.expression = expression;
  }

  @Override
  public void print(int indent) {
    printIndent(indent);
    System.out.println("Variable declaration (" + identefier + ")");
    expression.print(indent + 1);
  }
}
