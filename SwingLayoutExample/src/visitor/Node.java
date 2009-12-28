package visitor;

import com.hopstepjump.backbone.runtime.api.*;


public class Node
{
// start generated code
	private Attribute<String> name;
	public Attribute<String> getName() { return name; }
	public void setName(Attribute<String> name) { this.name = name;}
	private visitor.INode me_INodeProvided = new INodeMeImpl();
	public visitor.INode getMe_INode(Class<?> required) { return me_INodeProvided; }
// end generated code
	private IVisitor visitor;
	
	private class INodeMeImpl implements visitor.INode
	{
		public String getName()
		{
			return name.get();
		}

		public void startVisiting()
		{
			if (visitor != null)
				visitor.visit();
		}
	}

	public void setV_IVisitor(visitor.IVisitor set)
	{
		visitor = set;
	}

	public visitor.INode getV_INode(Class<?> required)
	{
		return me_INodeProvided;
	}
}
