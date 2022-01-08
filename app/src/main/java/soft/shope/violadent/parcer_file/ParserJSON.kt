package soft.shope.violadent.parcer_file

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

interface RequestTypeProtocol

class ParserJsonViewModel : ViewModel() {

   fun <T : JSONModelProtocol> getResponse( dataClass: T? , baseURLString: String,
                                            typeRequest: String = TypeRequest().get,
                                            body: RequestBody? = null,
                                            res: (T?) -> (Unit)) {
       // println(baseURLString)
       val request = isValidURL(baseURLString, typeRequest, body)

       val client = OkHttpClient()

       if (request != null) {
           client.newCall(request).enqueue(object : Callback {

               override fun onFailure(call: Call, e: IOException) {
                   viewModelScope.launch(Dispatchers.IO) { res(null) }
                   println(" Request Failure ")
               }

               override fun onResponse(call: Call, response: Response) {
                   if (response.code == 200){

                       val bodyRequest = response.body?.string()

                       val gson = GsonBuilder().create()

                   //    println("bodyRequest $bodyRequest")

                       when {
                           dataClass != null -> {
                       //        println("bodyRequest $bodyRequest")
                               try {
                                   val mUser = gson.fromJson(bodyRequest?.trimIndent(), dataClass::class.java)
                                   viewModelScope.launch(Dispatchers.IO) { res(mUser) }
                                   println("mUser $mUser")

                               }catch (e: Exception){
                                   viewModelScope.launch(Dispatchers.IO) { res(null) }
                                   println("Error convert data to JSON - $e")
                               }
                           }
                           else -> {
                               println("dataClass == null")
                               viewModelScope.launch(Dispatchers.IO) { res(null) }
                           }
                       }

                   }else{ viewModelScope.launch(Dispatchers.IO) { res(null) } }
               }
           })

       } else { viewModelScope.launch(Dispatchers.IO) { res(null) } }

   }


// parser for array json // add this functionality to main parsers
    fun requestMethodArray(baseURLString: String, res: (String?) -> (Unit)) {
        println(baseURLString)

        val request = Request.Builder().url(baseURLString).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Null")
            }
            override fun onResponse(call: Call, response: Response) {
                res(response.body?.string())
            }
        })
    }

// check actual url
    private fun isValidURL(URL: String, typeRequest: String, body: RequestBody? = null): Request? {
        return try {

            when (typeRequest) {
                "GET" -> Request.Builder().get().url(URL).build()
                "POST" -> body?.let { Request.Builder().post(it).url(URL).build() }
                "PUT" -> body?.let { Request.Builder().put(it).url(URL).build() }
                "DELETE"-> Request.Builder().delete().url(URL).build()
                else -> null
            }

        }catch (e: Exception){
            null
        }

    }
}


// model type request
data class TypeRequest(
    val get: String = "GET",
    val post: String = "POST",
    val delete: String = "DELETE",
    val put: String = "PUT"
) : RequestTypeProtocol