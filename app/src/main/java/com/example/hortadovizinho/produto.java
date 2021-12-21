package com.example.hortadovizinho;

public class produto {
    private Integer id;
    private String nome;
    private String tipo;
    private Integer quantidade;
    private Float preco;
    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}


    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}


    public String getTipo() {return tipo;}

    public void setTipo(String tipo) {this.tipo = tipo;}


    public Integer getQuantidade() {return quantidade;}

    public void setQuantidade(Integer quantidade) {this.quantidade = quantidade;}


    public Float getPreco() {return preco;}

    public void setPreco(Float preco) {this.preco = preco;}

    public produto(Integer id, String nome, String tipo, Integer quantidade, Float preco) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    @Override
    public String toString(){
        return nome;
    }
}
