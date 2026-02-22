package beans;

import java.time.LocalDate;

public class PedidoBean {
    private int idPedido;
    private int idCliente;
    private LocalDate dtPedido;
    
    public static String NM_COL_IdPedido  = "idPedido";
    public static String NM_COL_IdCliente = "idCliente";
    public static String NM_COL_DtPedido  = "dtPedido";
    
    public static String NM_ENTIDADE      = "PedidoBean";
    
    public PedidoBean() {
        super();
    }
    
    public PedidoBean(int idPedido) {
        super();
        this.idPedido = idPedido;
    }
    
    public PedidoBean(int idPedido, int idCliente, LocalDate dtPedido) {
        super();
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.dtPedido = dtPedido;
    }

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public LocalDate getDtPedido() {
		return dtPedido;
	}

	public void setDtPedido(LocalDate dtPedido) {
		this.dtPedido = dtPedido;
	}
}