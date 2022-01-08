package soft.shope.violadent.extensions

import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.hint (otherView: View, list: List<Any?>? = null){
    if (list?.isEmpty() == true || list == null){

        otherView.visibility = View.VISIBLE
        this.visibility = View.GONE

    }else{
        otherView.visibility = View.GONE
        this.visibility = View.VISIBLE
    }
}