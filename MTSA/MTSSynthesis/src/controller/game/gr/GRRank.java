package controller.game.gr;

import org.apache.commons.lang.*;

import controller.game.model.*;

public class GRRank implements Rank {
	public static final int DEFAULT_INITIAL_HEIGHT = 0;
	public static final int DEFAULT_INITIAL_WIDTH = 1;

	private int value;
	private int assume;
	private RankContext context;

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.game.gr.Rank#init(controller.game.gr.GRRankContext)
	 */
	public void init(RankContext context) {
		this.context = context;
	}

	public GRRank(RankContext context) {
		this(context, DEFAULT_INITIAL_HEIGHT, DEFAULT_INITIAL_WIDTH);
		this.context = context;
	}

	public GRRank(RankContext context, int value, int assume) {
		this.context = context;
		if (this.context.inRange(value, assume)) {
			this.value = value;
			this.assume = assume;
		} else {
			throw new RuntimeException("value and/or assume are out of range.");
		}
	}

	// static public void setHeight(int height) { // this function should be
	// available to set the height before
	// // any rank is available
	// Rank.height = height;
	// }
	//	
	// static public void setWidth(int width) { // this function should be
	// available to set the width before
	// // any rank is available
	// Rank.width = width;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.game.gr.Rank#increase()
	 */
	public void increase() {
		if (this.context.getHeight() == value) {
			return;
		} else if (this.context.getWidth() == assume) {
			value++;
			assume = 1;
		} else {
			assume++;
		}
	}

	public void setToInfinity() {
		value = this.context.getHeight();
		assume = 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.game.gr.Rank#reset()
	 */
	public void reset() {
		value = 0;
		assume = 1;
	}

	public void set(int value, int assumption) {
		this.value = value;
		this.assume = assumption;
	}

	public void set(Rank rank) {
		boolean instance = rank instanceof GRRank;
		Validate.isTrue(instance, "Ranks are not comparable. [" + this + ", "
				+ rank + "]");
		GRRank grRank = (GRRank) rank;
		this.set(grRank.getValue(), grRank.getAssume());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.game.gr.Rank#isInfinity()
	 */
	public boolean isInfinity() {
		return value == (this.context.getHeight());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.game.gr.Rank#getValue()
	 */
	public int getValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.game.gr.Rank#getAssume()
	 */
	public int getAssume() {
		return assume;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.game.gr.Rank#compareTo(controller.game.gr.GRRank)
	 */
	@Override
	public int compareTo(Rank other) {
		if (this.equals(other)) {
			return 0;
		}
		boolean instance = other instanceof GRRank;
		Validate.isTrue(instance, "Ranks are not comparable. [" + this + ", "
				+ other + "]");
		GRRank grOther = (GRRank) other;
		if (this.value < grOther.getValue()
				|| (this.value == grOther.getValue() && this.assume < grOther
						.getAssume())) {
			return -1;
		} else if (this.value > grOther.getValue()
				|| (this.value == grOther.getValue() && this.assume > grOther
						.getAssume())) {
			return 1;
		} else { // this.equals(other)
			return 0;
		}
	}

	@Override
	public boolean equals(Object anObject) {
		if (this == anObject) {
			return true;
		}
		if (anObject instanceof GRRank) {
			GRRank grRank = (GRRank) anObject;
			return this.value == grRank.getValue()
					&& this.assume == grRank.getAssume();
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "[assume:" + this.assume + ", value:" + this.value + "]";
	}

	public static GRRank getInfinityFor(RankContext ctx) {
		GRRank rank = new GRRank(ctx);
		rank.setToInfinity();
		return rank;
	}
}
