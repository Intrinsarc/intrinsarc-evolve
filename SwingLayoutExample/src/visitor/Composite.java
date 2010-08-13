package visitor;

import com.intrinsarc.backbone.runtime.api.*;


public class Composite
{
// start generated code
	private Attribute<String> name;
	public Attribute<String> getName() { return name; }
	public void setName(Attribute<String> name) { this.name = name;}

	private java.util.List<visitor.INode> nodes_INodeRequired = new java.util.ArrayList<visitor.INode>();
	public void setNodes_INode(visitor.INode nodes_INodeRequired, int index) { PortHelper.fill(this.nodes_INodeRequired, nodes_INodeRequired, index); }
	private visitor.IComposite me_ICompositeProvided = new ICompositeMeImpl();
	public visitor.IComposite getMe_IComposite(Class<?> required) { return me_ICompositeProvided; }
// end generated code
	private IVisitor visitor;

	private class ICompositeMeImpl implements visitor.IComposite
	{
		public String getName()
		{
			return name.get();
		}

		public void startVisiting()
		{
			if (visitor != null)
			{
				visitor.visit();
				for (INode node : nodes_INodeRequired)
					node.startVisiting();
			}
		}
	}


	public void setV_IVisitor(visitor.IVisitor set)
	{
		visitor = set;
	}


	public visitor.IComposite getV_IComposite(Class<?> required)
	{
		return me_ICompositeProvided;
	}
}
