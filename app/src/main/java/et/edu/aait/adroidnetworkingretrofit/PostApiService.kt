package et.edu.aait.adroidnetworkingretrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface PostApiService {

 @GET("posts/{id}")
 fun getPostById(@Path("id") id: Long): Deferred<Response<Post>>

 @GET("posts")
 fun getPostsByUserId(@Query("userId") userId: Long): Deferred<Response<List<Post>>>

 @POST("posts")
 fun insertPost(@Body post: Post): Deferred<Response<Post>>

 @PUT("posts/{id}")
 fun updatePost(@Path("id") id: Long, @Body post: Post): Deferred<Response<Post>>

 @DELETE("posts/{id}")
 fun deletePost(@Path("id") id: Long): Deferred<Response<Void>>

 companion object {

  private val baseUrl = "https://jsonplaceholder.typicode.com/"

  fun getInstance(): PostApiService {

   val interceptor = HttpLoggingInterceptor()
   interceptor.level = HttpLoggingInterceptor.Level.BASIC

   val client = OkHttpClient
    .Builder()
    .addInterceptor(interceptor)
    .build()

   val retrofit: Retrofit =  Retrofit.Builder()
    .baseUrl(baseUrl)
    .client(client)
    .addConverterFactory(MoshiConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

   return retrofit.create(PostApiService::class.java)

  }

 }

}

