package et.edu.aait.adroidnetworkingretrofit

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val postViewModel = ViewModelProviders.of(this).get(PostViewModel::class.java)

        search_button.setOnClickListener {

            val id = search_edit_text.text.toString().toLong()

            if (connected()) {
                postViewModel.getPostById(id)
                postViewModel.getResponse.observe(this, Observer { response ->
                   response.body()?.run{
                     updateFields(this)
                   }
                    updateStatusMessageField(response.code().toString())
                })
            }
        }

        add_button.setOnClickListener {

            val post = readFields()
            if (connected()) {
                postViewModel.insertPost(post)
                postViewModel.insertResponse.observe(this, Observer { response ->
                   response.body()?.run{
                     updateFields(this)
                   }
                    updateStatusMessageField(response.code().toString())
                })
            }
            clearFields()
        }

        update_button.setOnClickListener {

            val post = readFields()
            if (connected()) {
                postViewModel.updatePost(post.id,post)
                postViewModel.updateResponse.observe(this, Observer { response ->
                   response.body()?.run{
                     updateFields(this)
                   }
                    updateStatusMessageField(response.code().toString())
                })
            }
            clearFields()
        }

        delete_button.setOnClickListener {

            val post = readFields()
            if (connected()) {
                postViewModel.deletePost(post.id)
                postViewModel.deleteResponse.observe(this, Observer { response ->
                    updateStatusMessageField(response.code().toString())
                })
            }
            clearFields()
        }

    }

    private fun updateStatusMessageField(message: String){
        status_text_view.text = message
    }

    private fun updateFields(post: Post){
        post.run{
            id_edit_text.setText(id.toString())
            user_id_edit_text.setText(userId.toString())
            title_edit_text.setText(title)
            body_edit_text.setText(HtmlCompat.fromHtml(body, HtmlCompat.FROM_HTML_MODE_COMPACT))
        }
    }

    private fun readFields() = Post(
        user_id_edit_text.text.toString().toLong(),
        id_edit_text.text.toString().toLong(),
        title_edit_text.text.toString(),
        body_edit_text.text.toString()
    )

    private fun clearFields() {
        user_id_edit_text.setText("")
        id_edit_text.setText("")
        title_edit_text.setText("")
        body_edit_text.setText("")
        search_edit_text.setText("")
    }

    private fun connected():Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected
    }
}
