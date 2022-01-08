package com.example.hortadovizinho;

public class user {
    private String nome;
    private String apelido;
    private String mail;
    private String morada;
    private String codPostal;
    private String password;
    private String password2;


    public user(String nome,String apelido,String mail,String morada,String codPostal,String password,String password2)
    {
        this.nome=nome;
        this.apelido=apelido;
        this.mail=mail;
        this.morada=morada;
        this.codPostal=codPostal;
        this.password=password;
        this.password2=password2;
    }

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}


    public String getMail() {return mail;}

    public void setMail(String mail) {this.mail = mail;}


    public String getMorada() {return morada;}

    public void setMorada(String morada) {this.morada = morada;}


    public String getCodP() {return codPostal;}

    public void setCodP(String codPostal) {this.codPostal = codPostal;}


    public String getApl() {return apelido;}

    public void setApl(String apelido) {this.apelido = apelido;}


    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}


    public String getPassword2() {return password2;}

    public void setPassword2(String password2) {this.password2 = password2;}

}
