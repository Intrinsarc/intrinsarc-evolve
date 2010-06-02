package com.hopstepjump.jumble.alloy;

interface ExpressionWatcher
{
  void matchedExpression(String line, String[] parameters, int count);
}