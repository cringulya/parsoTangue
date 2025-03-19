package parser.ast;

import java.util.List;
import java.util.Objects;

public class FunctionDeclarationNode extends ASTNode {
  public final String identefier;
  public final List<ASTNode> parameters;
  public final ASTNode body;

  public FunctionDeclarationNode(String identefier, List<ASTNode> parameters, ASTNode body) {
    this.identefier = identefier;
    this.parameters = parameters;
    this.body = body;
  }

  @Override
  public void print(int indent) {
    printIndent(indent);
    System.out.println("Function declaration (" + identefier + ")");
    printIndent(indent + 1);
    System.out.println("Parameters:");
    for (ASTNode param : parameters) {
      param.print(indent + 2);
    }
    printIndent(indent + 1);
    System.out.println("Body:");
    body.print(indent + 2);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    FunctionDeclarationNode other = (FunctionDeclarationNode) obj;
    return Objects.equals(identefier, other.identefier)
        && Objects.equals(parameters, other.parameters)
        && Objects.equals(body, other.body);
  }
}
