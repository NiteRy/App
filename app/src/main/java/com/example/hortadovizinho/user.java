package com.example.hortadovizinho;

public class user {
    private Integer id;
    private String nome;
    private String mail;
    private String morada;
    private String pais;
    private int datanasc;
    private String genero;
    private Integer nif;
    private Integer telefone;
    private String password;
    private String password2;

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}


    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}


    public String getMail() {return mail;}

    public void setMail(String mail) {this.mail = mail;}


    public String getMorada() {return morada;}

    public void setMorada(String morada) {this.morada = morada;}


    public String getp() {return pais;}

    public void setp(String pais) {this.pais = pais;}


    public Integer getData() {return datanasc;}

    public void setData(Integer datanasc) {this.datanasc = datanasc;}


    public String getGen() {return genero;}

    public void setGen(String genero) {this.genero = genero;}


    public Integer getNif() {return nif;}

    public void setNif(Integer nif) {this.nif = nif;}


    public Integer getTelefone() {return telefone;}

    public void setTelefone(Integer telefone) {this.telefone = telefone;}


    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}


    public String getPassword2() {return password2;}

    public void setPassword2(String password2) {this.password2 = password2;}


    @Override
    public String toString(){
        return nome;
    }
}
