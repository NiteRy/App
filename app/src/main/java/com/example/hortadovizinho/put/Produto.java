package com.example.hortadovizinho.put;

public class Produto
{
    private int id;
    private String nome;
    private String descricao;
    private String unidade;
    private float preco;
    private float preco_promo;
    private String data_precopromo_fixado;
    private String data_precopromo_limite;
    private String stock;
    private String foto;
    private String campo_slug;
    private String timestamp_alterado;
    private boolean publicado;
    private String categoria;
    private int sub_categoria;
    private int seccao;

    public Produto(int id, String nome, String descricao, String unidade, float preco, float preco_promo, String data_precopromo_fixado, String data_precopromo_limite, String stock, String foto, String campo_slug, String timestamp_alterado, boolean publicado, String categoria, int sub_categoria, int seccao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.unidade = unidade;
        this.preco = preco;
        this.preco_promo = preco_promo;
        this.data_precopromo_fixado = data_precopromo_fixado;
        this.data_precopromo_limite = data_precopromo_limite;
        this.stock = stock;
        this.foto = foto;
        this.campo_slug = campo_slug;
        this.timestamp_alterado = timestamp_alterado;
        this.publicado = publicado;
        this.categoria = categoria;
        this.sub_categoria = sub_categoria;
        this.seccao = seccao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public float getPreco_promo() {
        return preco_promo;
    }

    public void setPreco_promo(float preco_promo) {
        this.preco_promo = preco_promo;
    }

    public String getData_precopromo_fixado() {
        return data_precopromo_fixado;
    }

    public void setData_precopromo_fixado(String data_precopromo_fixado) {
        this.data_precopromo_fixado = data_precopromo_fixado;
    }

    public String getData_precopromo_limite() {
        return data_precopromo_limite;
    }

    public void setData_precopromo_limite(String data_precopromo_limite) {
        this.data_precopromo_limite = data_precopromo_limite;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCampo_slug() {
        return campo_slug;
    }

    public void setCampo_slug(String campo_slug) {
        this.campo_slug = campo_slug;
    }

    public String getTimestamp_alterado() {
        return timestamp_alterado;
    }

    public void setTimestamp_alterado(String timestamp_alterado) {
        this.timestamp_alterado = timestamp_alterado;
    }

    public boolean getPublicado() {
        return publicado;
    }

    public void setPublicado(boolean publicado) {
        this.publicado = publicado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getSub_categoria() {
        return sub_categoria;
    }

    public void setSub_categoria(int sub_categoria) {
        this.sub_categoria = sub_categoria;
    }

    public int getSeccao() {
        return seccao;
    }

    public void setSeccao(int seccao) {
        this.seccao = seccao;
    }
}
