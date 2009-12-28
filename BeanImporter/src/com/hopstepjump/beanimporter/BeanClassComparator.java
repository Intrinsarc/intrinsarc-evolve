package com.hopstepjump.beanimporter;

import java.util.*;

public class BeanClassComparator implements Comparator<BeanClass>
{
	public int compare(BeanClass o1, BeanClass o2)
	{
		if (o1.isBean() && !o2.isBean())
			return -1;
		if (o2.isBean() && !o1.isBean())
			return 1;
		if (o1.getType() == BeanTypeEnum.INTERFACE && o2.getType() != BeanTypeEnum.INTERFACE)
			return -1;
		if (o2.getType() == BeanTypeEnum.INTERFACE && o1.getType() != BeanTypeEnum.INTERFACE)
			return 1;
		return o1.getName().compareTo(o2.getName());
	}
}