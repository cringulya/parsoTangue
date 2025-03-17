package parser.ast;

public abstract class ASTNode {
  public abstract void print(int indent);

  protected void printIndent(int indent) {
    for (int i = 0; i < indent; i++)
      System.out.print("  ");
  }
}
