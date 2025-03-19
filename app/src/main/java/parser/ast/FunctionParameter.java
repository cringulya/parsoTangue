package parser.ast;

import java.util.Objects;

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

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    FunctionParameter other = (FunctionParameter) obj;
    return Objects.equals(identefier, other.identefier)
        && Objects.equals(type, other.type);
  }
}
