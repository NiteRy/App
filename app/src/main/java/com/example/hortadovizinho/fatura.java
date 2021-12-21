package com.example.hortadovizinho;

public class fatura {
    private Integer idf;
    private Integer idu;
    private Integer idp;
    private String nome;
    private Integer nif;
    private String produto;
    private Integer quantidade;
    private Float preco;
    private Float total;

    public Integer getId() {return idf;}

    public void setId(Integer id) {this.idf = idf;}


    public Integer getIdu() {return idu;}

    public void setIdu(Integer id) {this.idu = idu;}


    public Integer getIdp() {return idp;}

    public void setIdp(Integer id) {this.idp = idp;}


    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}


    public Integer getNif() {return nif;}

    public void setNif(Integer nif) {this.nif = nif;}


    public String getProduto() {return produto;}

    public void setProduto(String produto) {this.produto = produto;}


    public Integer getQuantidade() {return quantidade;}

    public void setQuantidade(Integer quantidade) {this.quantidade = quantidade;}


    public Float getPreco() {return preco;}

    public void setPreco(Float preco) {this.preco = preco;}


    public Float getTotal() {return total;}

    public void setTotal(Float total) {this.total = total;}


    @Override
    public String toString(){
        return nome;
    }
}
