package votacao.servlet;

import votacao.exception.BaseException;

public class AreaRestritaException extends BaseException {

	private static final long serialVersionUID = 4458352080375280165L;

	public AreaRestritaException() {
	}

	public AreaRestritaException(String message) {
		super(message);
	}

	public AreaRestritaException(Throwable cause) {
		super(cause);
	}

	public AreaRestritaException(String message, Throwable cause) {
		super(message, cause);
	}

}
