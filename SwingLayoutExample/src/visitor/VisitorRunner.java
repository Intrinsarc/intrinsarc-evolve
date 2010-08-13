package visitor;

import java.util.*;

import com.intrinsarc.backbone.runtime.api.*;

public class VisitorRunner
{
// start generated code
	private com.intrinsarc.backbone.runtime.api.IRun run_IRunProvided = new IRunRunImpl();
	public com.intrinsarc.backbone.runtime.api.IRun getRun_IRun(Class<?> required) { return run_IRunProvided; }

	private visitor.INode node_INodeRequired;
	public void setNode_INode(visitor.INode node_INodeRequired) { this.node_INodeRequired = node_INodeRequired; }

	private com.intrinsarc.backbone.runtime.api.ICreate create_ICreateRequired;
	public void setCreate_ICreate(com.intrinsarc.backbone.runtime.api.ICreate create_ICreateRequired) { this.create_ICreateRequired = create_ICreateRequired; }
// end generated code


	private class IRunRunImpl implements IRun
	{
		public int run(String[] args)
		{
			System.out.println("\n$$-- starting off with some nodes");
			node_INodeRequired.startVisiting();

			// set up the parameters
			Map<String, Object> values = new HashMap<String, Object>();
			
			// now create some nodes
			System.out.println("\n$$-- creating some more nodes...");
			values.put("name", "FactoryComposite1");
			create_ICreateRequired.create(values);
			node_INodeRequired.startVisiting();

			// now create some nodes
			System.out.println("\n$$-- creating some more nodes...");
			values.put("name", "FactoryComposite2");
			Object memento = create_ICreateRequired.create(values);
			node_INodeRequired.startVisiting();

//			System.out.println("\n$$-- removing the last set of nodes...");
//			create_ICreateRequired.destroy(memento);
//			node_INodeRequired.startVisiting();

			return 0;
		}
	}
}
