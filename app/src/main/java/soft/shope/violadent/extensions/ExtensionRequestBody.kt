package soft.shope.violadent.extensions

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import soft.shope.violadent.parcer_file.JSONBodyProtocol

// get RequestBody by set the desired data class
fun JSONBodyProtocol.getBody(): RequestBody {

    val json = Gson().toJson(this)

    return json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
}