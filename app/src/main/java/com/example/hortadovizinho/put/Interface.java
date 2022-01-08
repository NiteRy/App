package com.example.hortadovizinho.put;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Interface
{
    @FormUrlEncoded
    @POST("/api/produtos/")
    Call<Produto> getUserInformation(@Field("nome") String nome,
                                  @Field("descricao") String descricao,
                                  @Field("unidade") String unidade,
                                  @Field("preco") String preco,
                                  @Field("preco_promo") String preco_promo,
                                  @Field("data_precopromo_fixado") String data_precopromo_fixado,
                                  @Field("data_precopromo_limite") String data_precopromo_limite,
                                  @Field("stock") String stock,
                                  @Field("foto") String foto,
                                  @Field("campo_slug") String campo_slug,
                                  @Field("publicado") boolean publicado,
                                  @Field("categoria") String categoria,
                                  @Field("sub_categoria") String sub_categoria,
                                  @Field("seccao") String seccao);
}
