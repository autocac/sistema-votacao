package votacao.exception;

public class SessaoInvalidaException extends Exception {

	private static final long serialVersionUID = 1187688660361981049L;

	public SessaoInvalidaException() {
	}

	public SessaoInvalidaException(String message) {
		super(message);
	}

	public SessaoInvalidaException(Throwable cause) {
		super(cause);
	}

	public SessaoInvalidaException(String message, Throwable cause) {
		super(message, cause);
	}

}
