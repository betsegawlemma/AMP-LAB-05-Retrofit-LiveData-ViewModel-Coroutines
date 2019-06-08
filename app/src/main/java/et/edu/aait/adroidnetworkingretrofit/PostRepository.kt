package et.edu.aait.adroidnetworkingretrofit

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class PostRepository(private val postApiService: PostApiService) {

    suspend fun getPostById(id: Long): Response<Post> =
        withContext(Dispatchers.IO){
        postApiService.getPostById(id).await()
    }
    suspend fun getPostByUserId(userId: Long): Response<List<Post>> =
        withContext(Dispatchers.IO){
        postApiService.getPostsByUserId(userId).await()
    }
    suspend fun updatePost(id: Long, post: Post): Response<Post> =
        withContext(Dispatchers.IO){
        postApiService.updatePost(id, post).await()
    }
    suspend fun insertPost(post: Post): Response<Post> =
        withContext(Dispatchers.IO) {
        postApiService.insertPost(post).await()
    }
    suspend fun deletePost(id: Long): Response<Void> =
        withContext(Dispatchers.IO){
        postApiService.deletePost(id).await()
    }
}

