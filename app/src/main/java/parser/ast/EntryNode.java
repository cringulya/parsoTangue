package parser.ast;

import java.util.List;

public class EntryNode extends ASTNode {
  public final List<ASTNode> declarations;

  public EntryNode(List<ASTNode> declarations) {
    this.declarations = declarations;
  }

  @Override
  public void print(int indent) {
    printIndent(indent);
    System.out.println("Entry:");
    for (ASTNode node : declarations) {
      node.print(indent + 1);
    }
  }
}
