package ac.ic.doc.mtstools.model.impl;

public class MTSTransition<Event, State> {
	private Event event;
	private State stateTo;
	private State stateFrom;

	public static <Event, State> MTSTransition<Event, State> createMTSEventState(
			State stateFrom, Event event, State stateTo) {
		return new MTSTransition<Event, State>(stateFrom, event, stateTo);
	}

	public MTSTransition(State stateFrom, Event event, State stateTo) {
		this.event = event;
		this.stateFrom = stateFrom;
		this.stateTo = stateTo;
	}

	/**
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @param event
	 *            the event to set
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof MTSTransition) {
			MTSTransition<Event, State> es = (MTSTransition<Event, State>) obj;
			return this.getEvent().equals(es.getEvent())
					&& this.getStateFrom().equals(es.getStateFrom())
					&& this.getStateTo().equals(es.getStateTo());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getEvent().hashCode() ^ this.getStateFrom().hashCode()
				^ this.getStateTo().hashCode();
	}

	@Override
	public String toString() {
		return "(" + this.getStateFrom() + ", " + this.getEvent() + ", "
				+ this.getStateTo() + ")";
	}

	/**
	 * @return the stateTo
	 */
	public State getStateTo() {
		return stateTo;
	}

	/**
	 * @param stateTo
	 *            the stateTo to set
	 */
	public void setStateTo(State stateTo) {
		this.stateTo = stateTo;
	}

	/**
	 * @return the stateFrom
	 */
	public State getStateFrom() {
		return stateFrom;
	}

	/**
	 * @param stateFrom
	 *            the stateFrom to set
	 */
	public void setStateFrom(State stateFrom) {
		this.stateFrom = stateFrom;
	}
}
