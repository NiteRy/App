package com.example.hortadovizinho.clas;

public class Produto {

    public String nome;
    public String descricao;
    public String unidade;
    public float preco;
    public float preco_promo;
    public String data_precopromo_fixado;
    public String data_precopromo_limite;
    public float stock;
    public String foto;
    public String slug;
    public String timestamp_alterado;
    public String seccao;



    public Produto(){}

    public Produto(String nome,
                   String descricao,
                   String unidade,
                   float preco,
                   float preco_promo,
                   String data_precopromo_fixado,
                   String data_precopromo_limite,
                   float stock,
                   String foto,
                   String slug,
                   String timestamp_alterado,
                   String seccao) {

        this.nome = nome;
        this.descricao = descricao;
        this.unidade = unidade;
        this.preco = preco;
        this.preco_promo = preco_promo;
        this.data_precopromo_fixado = data_precopromo_fixado;
        this.data_precopromo_limite = data_precopromo_limite;
        this.stock = stock;
        this.foto = foto;
        this.slug = slug;
        this.timestamp_alterado = timestamp_alterado;
        this.seccao = seccao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getUnidade() {
        return unidade;
    }

    public float getPreco() {
        return preco;
    }

    public float getPreco_promo() {
        return preco_promo;
    }

    public String getData_precopromo_fixado() {
        return data_precopromo_fixado;
    }

    public String getData_precopromo_limite() {
        return data_precopromo_limite;
    }

    public float getStock() {
        return stock;
    }

    public String getFoto() {
        return foto;
    }

    public String getSlug() {
        return slug;
    }

    public String getTimestamp_alterado() {
        return timestamp_alterado;
    }

    public String getSeccao() {
        return seccao;
    }
}
