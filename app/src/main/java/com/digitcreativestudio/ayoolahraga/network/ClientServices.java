package com.digitcreativestudio.ayoolahraga.network;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ClientServices {

    @FormUrlEncoded
    @POST("api/user/login")
    Call<LoginResponse> loginPOST(@FieldMap HashMap<String, String> params);

    @GET("api/type")
    Call<TypeResponse> typeGET();

    @GET("api/type-c")
    Call<TypeResponse> communityGET();

    @GET("api/venue")
    Call<ListVenueResponse> listVenueGET(@Query("type") String type, @Query("query") String query);

    @GET("api/community")
    Call<ListCommunityResponse> listCommunityGET(@Query("type") String type, @Query("query") String query);

    @GET("api/venue/{id_venue}")
    Call<DetailVenueResponse> detailVenueGET(@Path("id_venue") String id_venue);

    @GET("api/community/{id_community}")
    Call<DetailCommunityResponse> detailCommunityGET(@Path("id_community") String id_community);

    @FormUrlEncoded
    @POST("api/user/edit/{id_user}")
    Call<EditAccountResponse> editAccountPOST(@Path("id_user") String id_user, @FieldMap HashMap<String, String> params);

    @GET("api/user/show/{id_user}")
    Call<ShowAccountResponse> showAccountGET(@Path("id_user") String id_user);

    @GET("search/photos")
    Call<UnsplashResponse> getPhoto(
            @Query("query") String query,
            @Query("page") String page,
            @Query("per_page") String per_page,
//            @Query("orientation") String orientation,
            @Query("client_id") String client_id
    );

    @FormUrlEncoded
    @POST("api/user/register")
    Call<RegisterResponse> registerPOST(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("api/rating/{id_venue}")
    Call<BasicResponse> giveRatePOST(@Path("id_venue") String id_venue, @FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("api/rating/{id_venue}")
    Call<BasicResponse> checkRatePOST(@Path("id_venue") String id_venue, @FieldMap HashMap<String, String> params);

    @GET("feeds/posts/default")
    Call<BlogResponse> listBlogGET();

    @GET("blog?call_custom_simple_rss=1")
    Call<NewBlogResponse> listNewBlogGET();
}
