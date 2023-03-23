package server;
/**
 *Esta classe retornara a mensagem com base no status HTTP
 *  @author Alexjr
 * @version 0.0.1
 */
public class Status {
	public static final int OK = 200; // Requisição bem sucedida
	public static final int CREATED = 201; // Recurso criado com sucesso
	public static final int NO_CONTENT = 204; // Sem conteúdo
	public static final int BAD_REQUEST = 400; // Requisição inválida
	public static final int UNAUTHORIZED = 401; // Não autorizado
	public static final int FORBIDDEN = 403; // Proibido
	public static final int NOT_FOUND = 404; // Não encontrado
	public static final int INTERNAL_SERVER_ERROR = 500; // Erro interno do servidor

	/**
	 * O método getMessage retorna uma mensagem de erro com base no status HTTP informado à requisição HTTP.
	 * @param status o código de status HTTP para o qual se deseja obter a mensagem de erro correspondente
	 * @return a mensagem de erro correspondente ao código de status HTTP informado
	 */
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

/**
 * O método isError retorna true se o código de status HTTP informado corresponder a um erro.
 * @param status o código de status HTTP para o qual se deseja verificar se corresponde a um erro
 * @return true se o código de status HTTP informado corresponder a um erro, false caso contrário
 */
	public static boolean isError(int status) {
		return status >= 400;
	}
}
