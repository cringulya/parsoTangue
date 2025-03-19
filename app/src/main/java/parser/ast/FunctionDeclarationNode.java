package parser.ast;

import java.util.List;

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
}
