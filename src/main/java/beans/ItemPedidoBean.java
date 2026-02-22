package beans;

public class ItemPedidoBean {
    public static final String NM_COL_IdItem        = "idItem";
    public static final String NM_COL_IdPedido      = "idPedido";
    public static final String NM_COL_IdProduto     = "idProduto";
    public static final String NM_COL_ValorUnitario = "valorUnitario";
    public static final String NM_COL_Quantidade    = "quantidade";
    public static final String NM_COL_Desconto      = "desconto";

    private int idItem;
    private int idPedido;
    private int idProduto;
    private double valorUnitario;
    private int quantidade;
    private double desconto;

    public ItemPedidoBean() {
    	super();
    }
    
	public ItemPedidoBean(int idItem) {
		super();
		this.idItem = idItem;
	}

	public ItemPedidoBean(int idItem, int idPedido, int idProduto, double valorUnitario, int quantidade, double desconto) {
		super();
		this.idItem = idItem;
		this.idPedido = idPedido;
		this.idProduto = idProduto;
		this.valorUnitario = valorUnitario;
		this.quantidade = quantidade;
		this.desconto = desconto;
	}

	public int getIdItem() {
		return idItem;
	}

	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}
}