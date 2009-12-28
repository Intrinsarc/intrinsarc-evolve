package com.hopstepjump.deltaengine.errorchecking;

import com.hopstepjump.deltaengine.base.*;

public class ErrorChecker
{
  private ErrorRegister errors;

  public ErrorChecker(ErrorRegister errors)
  {
    this.errors = errors;
  }

  public void performCheck(final DEStratum perspective, DEObject element, final boolean diagramCheck)
  {
    element.visit(
      new DEObjectVisitorAdapter()
      {
        @Override
        public void visitInterface(DEInterface iface)
        {
      		new ElementErrorChecker(perspective, iface, errors).performCheck(diagramCheck);
      		new InterfaceErrorChecker(perspective, iface, errors).performCheck(diagramCheck);
        }

        @Override
        public void visitComponent(DEComponent component)
        {
          new ElementErrorChecker(perspective, component, errors).performCheck(diagramCheck);
          new ComponentErrorChecker(perspective, component, errors).performCheck(diagramCheck);
        }

        @Override
        public void visitStratum(DEStratum stratum)
        {
          new StratumErrorChecker(stratum, errors).performCheck(diagramCheck);
        }          
      });
  }
}
