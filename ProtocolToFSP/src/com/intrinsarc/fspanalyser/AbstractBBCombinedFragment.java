package com.intrinsarc.fspanalyser;

import java.util.*;

public abstract class AbstractBBCombinedFragment extends BBMetaModelElement
{
  /** attribute operator::String */
  protected String operator;

  /** association operands::Operand[0..-1] */
  protected List<BBOperand> operands = new ArrayList<BBOperand>();


  /**
   * the constructor
   */
  public AbstractBBCombinedFragment()
  {
  }




  /**
    * set operator
    * @param operator the String to add
    */
  public void setOperator(String operator)
  {
    this.operator = operator;
  }

  /**
    * get the String
    * @return the String
    */
  public String getOperator()
  {
    return operator;
  }

  /**
    * add a BBOperand to operands
    * @param operand the BBOperand to add
    */
  public void addOperand(BBOperand operand)
  {
    this.operands.add(operand);
  }

  /**
    * get the list of BBOperands
    * @return  the BBOperands
    */
  public List<BBOperand> getOperands()
  {
    return operands;
  }

  public String toString(String prefix)
  {
    StringBuffer buf = new StringBuffer();
    buf.append(prefix + "CombinedFragment");

    buf.append(", operator = " + operator);
    buf.append("\n");
    if (operands.size() != 0)
    {
      buf.append(prefix + "    --operands\n");
      for (BBMetaModelElement node : operands)
      {
        buf.append(node.toString(prefix + "        ") + "");
      }
    }

    return buf.toString();
  }

}
