package hardcoded.gui2;


import com.intrinsarc.backbone.runtime.api.*;

/** generated by Evolve */
public class Gui3
{
  private Attribute<String> a = new Attribute<String>("A String");
  public void setA(Attribute<String> a) { this.a = a; }
  public Attribute<String> getA() { return a; }

  private test.Gui2Runner x = new test.Gui2Runner();
  private hardcoded.gui2.Gui2 guiA = new hardcoded.gui2.Gui2();
  private hardcoded.gui2.Gui2 guiB = new hardcoded.gui2.Gui2();
  private hardcoded.designgridlayout.DesignGridLayoutPanel dgl = new hardcoded.designgridlayout.DesignGridLayoutPanel();
  private test.Row x1 = new test.Row();
  private test.Grid x2 = new test.Grid();
  private test.Grid gg = new test.Grid();
  private test.Logger outside = new test.Logger();
  private test.Register r = new test.Register();
  private javax.swing.JLabel jj = new javax.swing.JLabel();

  public Gui3()
  {
  }

  public void setSlots()
  {
    x.setTitle(new Attribute<String>("Composition"));
    guiA.setMessageX(a);
    guiB.setMessageX(a);
    outside.setPrefix(new Attribute<String>("Outside"));
    jj.setText(new Attribute<String>("Hurrah").get());
    guiA.setSlots();
    guiB.setSlots();
    dgl.setSlots();
  }

  public com.intrinsarc.backbone.runtime.api.IRun getRun_IRun(Class<?> required)
  {
    return x.getRun_IRun(required);
  }

  public void cacheProvidedInterfaces()
  {
    guiA.cacheProvidedInterfaces();
    guiB.cacheProvidedInterfaces();
    dgl.cacheProvidedInterfaces();
  }

  public void setRequiredInterfaces()
  {
    x2.setContents_JComponent(guiA.getRun_JPanel(javax.swing.JComponent.class), -1);
    x2.setContents_JComponent(jj, -1);
    x.setComponent_JComponent(dgl.getPanel_JPanel(javax.swing.JComponent.class));
    dgl.setRows_IRow(x1.getRow_IRow(test.IRow.class), -1);
    x1.setGrids_IGrid(x2.getGrid_IGrid(test.IGrid.class), -1);
    x1.setGrids_IGrid(gg.getGrid_IGrid(test.IGrid.class), -1);
    gg.setContents_JComponent(guiB.getRun_JPanel(javax.swing.JComponent.class), -1);
    guiA.setRequiredInterfaces();
    guiB.setRequiredInterfaces();
    dgl.setRequiredInterfaces();
  }

  public Gui3 init(boolean children)
  {
    if (children)
    {
      setSlots();
      cacheProvidedInterfaces();
      setRequiredInterfaces();
    }
    guiA.init(false);
    guiB.init(false);
    dgl.init(false);
    r.afterInit();
    return this;
  }
}
