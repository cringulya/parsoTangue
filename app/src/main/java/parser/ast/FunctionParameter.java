package parser.ast;

/**
 * FunctionParameter
 */
public class FunctionParameter extends ASTNode {
  public final String identefier;
  public final VarType type;

  public FunctionParameter(String identefier, VarType type) {
    this.identefier = identefier;
    this.type = type;
  }

  @Override
  public void print(int indent) {
    printIndent(indent);
    System.out.println("FunctionParameter (" + type + ' ' + identefier + ")");
  }
}
