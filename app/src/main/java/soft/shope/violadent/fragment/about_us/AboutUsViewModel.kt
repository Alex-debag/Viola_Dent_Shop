package soft.shope.violadent.fragment.about_us

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import soft.shope.violadent.parcer_file.ContactData

class AboutUsViewModel: ViewModel() {
    // for update recycler with information about us
    fun updateRecyclerInform(recycler: RecyclerView, context: Context?) {

        val listInform = listOf(
            Inform("", ContactData().introduction),
            Inform("Адреса", ContactData().address),
            Inform("Телефон", ContactData().contactPhone),
            Inform("E-mail", ContactData().email)
        )

        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = ContactDataAdapter(listInform)

    }
   // for go to url address // make extension String
    fun goToUrl(activity: FragmentActivity?, URl: String) {
        val i = Intent(Intent.ACTION_VIEW, Uri.parse(URl))
        activity?.startActivity(i)
    }
}