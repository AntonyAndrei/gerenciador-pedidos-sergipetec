package beans;

import java.time.LocalDate;

public class ClienteBean {
	private String idCliente;
	private String nome;
	private String email;
	private LocalDate dtCadastro;
	
	public static String NM_COL_IdCliente  = "idCliente";
	public static String NM_COL_Nome       = "nome";
	public static String NM_COL_Email      = "email";
	public static String NM_COL_DtCadastro = "dt_cadastro";
	
	public static String NM_ENTIDADE       = "ClienteBean";
	
	public ClienteBean() {
		super();
	}
	
	public ClienteBean(String idCliente) {
		super();
		this.idCliente = idCliente;
	}
	
	public ClienteBean(String idCliente, String nome, String email, LocalDate dtCadastro) {
		super();
		this.idCliente = idCliente;
		this.nome = nome;
		this.email = email;
		this.dtCadastro = dtCadastro;
	}

	public String getIdCliente() {
		return idCliente;
	}
	
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public LocalDate getDtCadastro() {
		return dtCadastro;
	}
	
	public void setDtCadastro(LocalDate localDate) {
		this.dtCadastro = localDate;
	}
	
	
}
