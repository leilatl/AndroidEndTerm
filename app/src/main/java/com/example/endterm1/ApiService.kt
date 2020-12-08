package com.example.endterm1

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("posts/")
    fun getPosts(): Call<List<Post>>

    @GET("posts/{id}/")
    fun getTodoById(@Path("id") postInt: Int): Call<Post>
//https://jsonplaceholder.typicode.com/comments?postId=​{id}
    @Headers("Cache-Control: max-age=640000", "User-Agent: My-App-Name")
    @GET("comments/")
    fun getComentsByUserId(
    @Query("userId") userId: Int): Call<List<Comment>>

    @GET("comments/")
    fun getComments(): Call<List<Comment>>
}