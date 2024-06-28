package com.example.session16newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.session16newsapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//Handling data from a RESTful API and parsing JSON.
//Making HTTP requests using libraries like Retrofit or
//HttpURLConnection. - Retrofit
//- Parsing JSON responses and extracting data using libraries like Gson or
//JSON parsing APIs. -using gson
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var adapter: NewsAdapter
    //newsApi: An instance of the Retrofit interface
    //This line creates a new instance of the Retrofit.Builder class. Retrofit.Builder is a class provided by
    // Retrofit library that helps configure and build a Retrofit instance.
    private val newsApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        //GsonConverterFactory.create() to specify that Gson should be used for JSON conversion.
        .addConverterFactory(GsonConverterFactory.create())
        .build()

        //Creates an implementation of the NewsApi interface using the configured Retrofit instance.
        .create(NewsApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        setUpApiCall()

    }

    private fun setUpApiCall() {
        //This line invokes the getTopHeadlines method of the newsApi object, which is an instance of the NewsApi interface created using Retrofit.
        // It passes two parameters: the country code (e.g., "US") and an API key.
        val call = newsApi.getTopHeadlines(Constants.US, Constants.API_KEY)
        //The enqueue method is used to make the API call asynchronously.
        // It takes a Callback as a parameter, defining how to handle the response or failure.
        call.enqueue(object : Callback<NewsResponse> {

            //onResponse method:
            //
            //This method is called when the API call is successful and a response is received.
            //if (response.isSuccessful) checks if the HTTP response code indicates success (status code 2xx).
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
               //Inside the if block:
                //val articles = response.body()?.articles ?: emptyList() extracts the list of articles from the response body.
                // If the response body or the articles list is null, it defaults to an empty list.

                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: emptyList()
                    //adapter.updateList(articles) updates the data in the adapter with the new list of articles.
                    adapter.updateList(articles)
                } else {
                    Toast.makeText(this@MainActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Something went wrong ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }

    private fun initViews(){
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

         adapter = NewsAdapter(arrayListOf())
        binding.recyclerView.adapter = adapter
    }

}
