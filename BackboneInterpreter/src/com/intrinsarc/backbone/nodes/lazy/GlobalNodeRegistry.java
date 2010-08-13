package com.intrinsarc.backbone.nodes.lazy;

public class GlobalNodeRegistry
{
  public static NodeRegistry registry;
  
  static
  {
  	reset();
  }
  
  public static void reset()
  {
  	registry = new NodeRegistry();
  	registry.initialize();  	
  }
}
