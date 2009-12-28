package com.hopstepjump.jumble.alloy;

import org.w3c.dom.*;

interface XPathExpressionWatcher
{
  void matchedExpression(Node node, NameRecord name, int nodeCount) throws InterpretAlloyException;
}