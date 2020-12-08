package com.example.endterm1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_detail.view.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailFragment : Fragment() {
    val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()

        val apiService = retrofit.create(ApiService::class.java)

        val position = args.passedId

        apiService.getTodoById(position+1).enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable){
                Log.e("Error", t.message!!)
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                view.useridTextView.text = "User ID: "+response.body()!!.userId.toString()
                view.idTextView.text = "ID: "+response.body()!!.id.toString()
                view.titleTextView.text = "title: "+response.body()!!.title
                view.statusTextView.text = "body: "+response.body()!!.body

            }
        })
        view.returnBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigateFromPostToList)
        }
        view.commentBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigateToComment)
        }

        return view
    }


}