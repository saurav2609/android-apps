package com.example.bookmyshowclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    companion object {
        private const val API_KEY = "3d9fd9b0e8cd3ab0e475f8ebaae40620"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchMovies()
    }

    private fun fetchMovies() {
        val networkHelper=NetworkHelper( context= this)

        if (networkHelper.isNetworkConnected()){
            //MAKE API CALL
            val request : MovieService=RetrofitBuilder.buildService()
            val call: Call<MovieResponse> = request.getMovies(API_KEY)

            showProgress()

            call.enqueue(object :Callback<MovieResponse>{
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    hideProgress()
                    if(response.isSuccessful && response.body()!=null){
                        val movieResponse=response.body()!!
                        val movies:List<Movie> = movieResponse.results
                        showMovies((movies as ArrayList<Movie>))
                    }else{
                        showErrorMessage(resources.getString(R.string.error_msg))
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    hideProgress()
                    showErrorMessage(t.message)
                }

            })
        }else{
            showErrorMessage(resources.getString((R.string.no_internet)))
        }
    }

    private fun showMovies(movies: ArrayList<Movie>) {
        recyclerView.visibility=View.VISIBLE
        progressBar.visibility=View.GONE
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator=DefaultItemAnimator()
        recyclerView.adapter=MoviesAdapter(movies)
    }

    private fun showProgress(){
        progressBar.visibility=View.VISIBLE
    }
    private fun hideProgress(){
        progressBar.visibility=View.GONE
    }
    private fun showErrorMessage(errorMessage: String?){
        errorView.visibility=View.VISIBLE
        errorView.text=errorMessage
    }
}