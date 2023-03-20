package server;

public class Status {
	public static final int OK = 200;
	public static final int CREATED = 201;
	public static final int NO_CONTENT = 204;
	public static final int BAD_REQUEST = 400;
	public static final int UNAUTHORIZED = 401;
	public static final int FORBIDDEN = 403;
	public static final int NOT_FOUND = 404;
	public static final int INTERNAL_SERVER_ERROR = 500;


	//O método getMessage retorna uma mensagem de erro com base no status HTTP informado à requisição HTTP.
	public static String getMessage(int status) {
		if(status == 200) {
			return "OK";
		}else if (status == 201 ) {
			return "Created";
		}else if (status == 204 ) {
			return "No Content";

		}else if (status == 400 ) {
			return "Bad Request";
		}else if (status == 401 ) {
			return "Unauthorized";
		}else if (status == 403 ) {
			return "Forbidden";
		}else if (status == 404 ) {
			return "Not Found";
		}else if (status == 500) {
			return "Internal Server Error";
		}else{
			return "Unknown";
		}

	}

	public static boolean isError(int status) {
		return status >= 400;
	}
}
