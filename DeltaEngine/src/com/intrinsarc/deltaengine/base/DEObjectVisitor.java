package com.intrinsarc.deltaengine.base;

public interface DEObjectVisitor
{
  public void visitStratum(DEStratum stratum);
  public void visitComponent(DEComponent component);
  public void visitInterface(DEInterface iface);
}
