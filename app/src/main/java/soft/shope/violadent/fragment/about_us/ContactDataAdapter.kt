package soft.shope.violadent.fragment.about_us

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import soft.shope.violadent.R
import soft.shope.violadent.extensions.makePhoneCall
import soft.shope.violadent.extensions.openGmail
import soft.shope.violadent.extensions.openMap

class ContactDataAdapter(private val list: List<Inform>,
                         private val fragment: Fragment)
    : RecyclerView.Adapter<ContactDataAdapter.ContactDataHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ContactDataHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.holder_about_us, parent, false)

        return ContactDataHolder(view, list, fragment)
    }

    override fun onBindViewHolder(holder: ContactDataHolder, position: Int) {
        val list = list[position]
        holder.header.text = list.header
        holder.footer.text = list.footer
    }

    override fun getItemCount() = list.size

    class ContactDataHolder(view: View, list: List<Inform>, fragment: Fragment) : RecyclerView.ViewHolder(view){
        val header: TextView = view.findViewById(R.id.about_us_up_text)
        val footer: TextView = view.findViewById(R.id.about_us_down_text)

        init {
            view.setOnClickListener {
               when (layoutPosition){
                   1 -> { "https://goo.gl/maps/APQFnNWP836H49t39".openMap(fragment) }
                   2 -> list[2].footer.makePhoneCall(fragment)
                   3 -> {  list[3].footer.openGmail(fragment) }
               }
            }
        }
    }
}


// model class for inform recycler
data class Inform(var header: String = "",
                  var footer: String = "")
