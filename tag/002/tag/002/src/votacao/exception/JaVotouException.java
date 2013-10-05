package votacao.exception;

public class JaVotouException extends Exception {

	private static final long serialVersionUID = 48791558306566844L;

	public JaVotouException() {
	}

	public JaVotouException(String message) {
		super(message);
	}

	public JaVotouException(Throwable cause) {
		super(cause);
	}

	public JaVotouException(String message, Throwable cause) {
		super(message, cause);
	}

}
