package et.edu.aait.adroidnetworkingretrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

class PostViewModel: ViewModel(){

    private val postRepository: PostRepository

    init{
       val postApiService = PostApiService.getInstance()
       postRepository = PostRepository(postApiService)
    }

    private  val _getResponse = MutableLiveData<Response<Post>>()
    val getResponse: LiveData<Response<Post>>
                 get() = _getResponse

    private val _getResponses = MutableLiveData<Response<List<Post>>>()
    val getResponses: LiveData<Response<List<Post>>>
                get() = _getResponses
    private val _updateResponse = MutableLiveData<Response<Post>>()
    val updateResponse: LiveData<Response<Post>>
                get() = _updateResponse
    private val _insertResponse = MutableLiveData<Response<Post>>()
    val insertResponse: LiveData<Response<Post>>
                get() = _insertResponse
    private val _deleteResponse = MutableLiveData<Response<Void>>()
    val deleteResponse: MutableLiveData<Response<Void>>
                get() = _deleteResponse
    fun getPostById(id: Long) = viewModelScope.launch{
        _getResponse.postValue(postRepository.getPostById(id))
    }

    fun updatePost(id: Long,post: Post) = viewModelScope.launch {
        _updateResponse.postValue(postRepository.updatePost(id, post))
    }

    fun insertPost(post: Post) = viewModelScope.launch {
        _insertResponse.postValue(postRepository.insertPost(post))
    }

    fun deletePost(id: Long) = viewModelScope.launch {
        _deleteResponse.postValue(postRepository.deletePost(id))
    }

    fun getPostByUserId(id: Long) = viewModelScope.launch{
        _getResponses.postValue(postRepository.getPostByUserId(id))
    }

}