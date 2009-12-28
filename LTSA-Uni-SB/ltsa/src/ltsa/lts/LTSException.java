package ltsa.lts;

public class LTSException extends RuntimeException {

	private static final long serialVersionUID = 8374046120407778447L;

	public Object marker;

	public LTSException(String errorMsg) {
		super(errorMsg);
		this.marker = null;
	}

	public LTSException(String errorMsg, Object marker) {
		super(errorMsg);
		this.marker = marker;
	}

}