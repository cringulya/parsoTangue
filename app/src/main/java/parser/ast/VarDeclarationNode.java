package parser.ast;

public class VarDeclarationNode extends ASTNode {
  public final String identefier;
  public final ASTNode expression;

  public VarDeclarationNode(String identefier, ASTNode expression) {
    this.identefier = identefier;
    this.expression = expression;
  }

  @Override
  public void print(int indent) {
    printIndent(indent);
    System.out.println("Variable declaration (" + identefier + ")");
    expression.print(indent + 1);
  }
}
