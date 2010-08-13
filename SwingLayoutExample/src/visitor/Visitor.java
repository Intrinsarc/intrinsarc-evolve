package visitor;

import com.intrinsarc.backbone.runtime.api.*;

public class Visitor
{
// start generated code

	private java.util.List<visitor.INode> nodes_INodeRequired = new java.util.ArrayList<visitor.INode>();
	public void setNodes_INode(visitor.INode nodes_INodeRequired, int index) { PortHelper.fill(this.nodes_INodeRequired, nodes_INodeRequired, index); }
	private java.util.List<IVisitorNodesImpl>  nodes_IVisitorProvided = new java.util.ArrayList<IVisitorNodesImpl>();
	public visitor.IVisitor getNodes_IVisitor(Class<?> required, int index) { int ind = PortHelper.fill(nodes_IVisitorProvided, null, index); if (nodes_IVisitorProvided.get(ind) == null) nodes_IVisitorProvided.add(ind, new IVisitorNodesImpl()); return nodes_IVisitorProvided.get(ind); }

	private java.util.List<visitor.IComposite> composites_ICompositeRequired = new java.util.ArrayList<visitor.IComposite>();
	public void setComposites_IComposite(visitor.IComposite composites_ICompositeRequired, int index) { PortHelper.fill(this.composites_ICompositeRequired, composites_ICompositeRequired, index); }
	private java.util.List<IVisitorCompositesImpl>  composites_IVisitorProvided = new java.util.ArrayList<IVisitorCompositesImpl>();
	public visitor.IVisitor getComposites_IVisitor(Class<?> required, int index) { int ind = PortHelper.fill(composites_IVisitorProvided, null, index); if (composites_IVisitorProvided.get(ind) == null) composites_IVisitorProvided.add(ind, new IVisitorCompositesImpl()); return composites_IVisitorProvided.get(ind); }
// end generated code
	private int nodeCount;
	private int compositeCount;


	private class IVisitorNodesImpl implements visitor.IVisitor
	{
		private int index = nodeCount++;
		
		public void visit()
		{
			System.out.println("$$ visiting Node " + nodes_INodeRequired.get(index).getName());
		}
	}

	private class IVisitorCompositesImpl implements visitor.IVisitor
	{
		private int index = compositeCount++;
		
		public void visit()
		{
			System.out.println("$$ visiting Composite " + composites_ICompositeRequired.get(index).getName());
		}
	}
}
