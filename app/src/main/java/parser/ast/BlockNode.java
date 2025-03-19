package parser.ast;

import java.util.List;
import java.util.Objects;

/**
 * BlockNode
 */
public class BlockNode extends ASTNode {
  public List<ASTNode> instructions;

  public BlockNode(List<ASTNode> statements) {
    this.instructions = statements;
  }

  @Override
  public void print(int indent) {
    printIndent(indent);
    System.out.println("Block node:");
    for (ASTNode node : instructions) {
      node.print(indent + 1);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false; 
    BlockNode blockNode = (BlockNode) obj;
    return Objects.equals(instructions, blockNode.instructions);
  }
}
