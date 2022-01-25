package soft.shope.violadent.extensions

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.squareup.picasso.Picasso

// to follow the link
fun String.followLink(fragment: Fragment){
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(this))
    fragment.startActivity(browserIntent)
}
// for convert image to link in bitmap
fun String.convertPhotoBase64ToBitmap(): Bitmap? {

    val imageBytes = Base64.decode( this, Base64.DEFAULT)

    return BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.size)
}
// for download photos from the Internet and upload them to View
fun String.imageOnTheLincToView (view: ImageView){
    Picasso.get().load(this).into(view)
}
// same as function above
fun String.getImageFromLink(context: Context, imageView: ImageView){
    Glide.with(context)
        .asBitmap()
        .load(this)
        .into(object : CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                imageView.setImageBitmap(resource)
            }
            override fun onLoadCleared(placeholder: Drawable?) {
                // this is called when imageView is cleared on lifecycle call or for
                // some other reason.
                // if you are referencing the bitmap somewhere else too other than this imageView
                // clear it here as you can no longer have the bitmap
            }
        })
}

// for request permission to call and create a call
fun String.makePhoneCall(thisFragment: Fragment) {

    thisFragment.checkPermission(Manifest.permission.CALL_PHONE){
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$this")

        thisFragment.activity?.startActivityFromFragment(thisFragment, intent, 0)
    }
}

// for open gmail
fun String.openGmail( fragment: Fragment ){
    try {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse("mailto:$this"))
//                           intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject")
//                           intent.putExtra(Intent.EXTRA_TEXT, "your_text")
        fragment.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Log.e("ERROR", e.message.toString())
    }
}

// for open google map
fun String.openMap(fragment: Fragment){

    val gmmIntentUri = Uri.parse(this)
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    fragment.startActivity(mapIntent)

}