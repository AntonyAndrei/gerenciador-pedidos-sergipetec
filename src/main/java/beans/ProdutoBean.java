package beans;

import java.time.LocalDate;

public class ProdutoBean {
    private int idProduto;
    private String descricao;
    private double valor;
    private int quantidadeEstoque;
    private LocalDate dtCadastro;
    
    public static String NM_COL_IdProduto         = "idProduto";
    public static String NM_COL_Descricao         = "descricao";
    public static String NM_COL_Valor             = "valor";
    public static String NM_COL_QuantidadeEstoque = "quantidadeEstoque";
    public static String NM_COL_DtCadastro        = "dt_cadastro";
    
    public static String NM_ENTIDADE              = "ProdutoBean";
    
    public ProdutoBean() {
        super();
    }
    
    public ProdutoBean(int idProduto) {
        super();
        this.idProduto = idProduto;
    }
    
    public ProdutoBean(int idProduto, String descricao, double valor, int quantidadeEstoque, LocalDate dtCadastro) {
        super();
        this.idProduto = idProduto;
        this.descricao = descricao;
        this.valor = valor;
        this.quantidadeEstoque = quantidadeEstoque;
        this.dtCadastro = dtCadastro;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public LocalDate getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(LocalDate dtCadastro) {
        this.dtCadastro = dtCadastro;
    }
}