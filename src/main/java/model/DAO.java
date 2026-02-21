package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
	/** MÓDULO DE CONEXÃO**/
	// PARÂMETROS DE CONEXÃO
	private String driver   = "com.mysql.cj.jdbc.Driver";	
	private String url      = "jdbc:mysql://127.0.0.1:3306/dbGerenciadorPedidos?useTimezone=true&serverTimezone=UTC";	
	private String user     = "root";
	private String password = "root";
	// MÉTODO DE CONEXÃO
	protected Connection conectar() {
		Connection conexao = null;
		try {
			Class.forName(driver);
			conexao = DriverManager.getConnection(url, user, password);
			return conexao;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
