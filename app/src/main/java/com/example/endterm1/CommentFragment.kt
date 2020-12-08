package com.example.endterm1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_comment.view.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.fragment_list.view.toDoRecyclerView
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CommentFragment : Fragment() {

    var list = mutableListOf<Comment>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_comment, container, false)

        val adapter = CommentAdapter()
        val recyclerView = view.toDoRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()

        val apiService = retrofit.create(ApiService::class.java)
        /*apiService.getComentsByUserId(1).enqueue(object : Callback<List<Comment>> {
            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.e("Error", t.message!!)
            }

            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                Log.e("Response size: ", response.body()!!.size.toString() + "")
            }
       })*/
        apiService.getComments().enqueue(object : Callback<List<Comment>> {
            override fun onFailure(call: Call<List<Comment>>, t: Throwable){
                Log.e("Error", t.message!!)
            }

            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                Log.e("Response size: ", response.body()!!.size.toString()+"")
                list.addAll(response.body()!!)
                adapter.setData(list)
            }
        })
        view.goToPosts.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigateFromCommentToList)
        }

        return view
    }


}