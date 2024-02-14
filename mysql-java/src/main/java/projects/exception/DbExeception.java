package projects.exception;


@SuppressWarnings("serial")
public class DbExeception extends RuntimeException{
	
	
	public DbExeception() {
	}

	public DbExeception(String message) {
		super(message);
	}

	public DbExeception(Throwable cause) {
		super(cause);
	}

	public DbExeception(String message, Throwable cause) {
		super(message, cause);
	}

	public DbExeception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	

}
