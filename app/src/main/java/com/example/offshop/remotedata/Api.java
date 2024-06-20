package com.example.offshop.remotedata;

import com.example.offshop.models.Category;
import com.example.offshop.models.CurrentUser;
import com.example.offshop.models.LoginResponse;
import com.example.offshop.models.Order;
import com.example.offshop.models.Product;
import com.example.offshop.models.Review;
import com.example.offshop.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {
    @GET("api/v1/product/all")
    Call<List<Product>> getStoreProducts();
    @POST("api/v1/product/create/")Call<Product> createNewProduct();
    @GET("api/v1/product/{id}/")
    Call<Product> getProductById(@Path("id") String productId);
    @PUT("api/v1/product/{id}/")Call<Product> updateProductById();
    @DELETE("api/v1/product/{id}/")Call<Product> deleteProductById(@Path("id") String productId);
    @PATCH("api/v1/product/{id}/")Call<Product> changeProductById();
    @POST("api/v1/auth/register")Call<User> registrationNewUser(@Body User user);
    @POST("api/v1/auth/login")Call<LoginResponse> checkLoginUser(@Body CurrentUser currentUser);
    @POST("api/v1/auth/logout")Call<User> makeLogOutUser();
    @POST("api/v1/order/create/")Call<Order> createNewOrder(@Body  Order order);
    @GET("api/v1/order/all")
    Call<List<Order>> getAllOrders();
    @GET("api/v1/review/all")
    Call<List<Review>> getAllReviews();
    @POST("api/v1/review/create/")
    Call<Review> createNewReview(@Body Review review);
    @GET("api/v1/category/all")
    Call<List<Category>> getAllCategories();
    @POST("api/v1/category/create/")
    Call<Category> createNewCategory(@Body Category category);
}
