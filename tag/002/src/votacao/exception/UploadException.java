package votacao.exception;

public class UploadException extends Exception {

	private static final long serialVersionUID = 376800021895555861L;

	public UploadException() {
	}

	public UploadException(String message) {
		super(message);
	}

	public UploadException(Throwable cause) {
		super(cause);
	}

	public UploadException(String message, Throwable cause) {
		super(message, cause);
	}

}
