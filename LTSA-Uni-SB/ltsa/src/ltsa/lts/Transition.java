package ltsa.lts;

public class Transition {
	int from;
	int to;
	Symbol event;

	Transition(int from, Symbol event, int to) {
		this.from = from;
		this.to = to;
		this.event = event;
	}

	@Override
	public String toString() {
		return from + " " + event + " " + to;
	}
}
