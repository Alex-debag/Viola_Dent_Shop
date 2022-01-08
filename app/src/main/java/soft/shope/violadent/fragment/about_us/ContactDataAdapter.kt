package soft.shope.violadent.fragment.about_us

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import soft.shope.violadent.R

class ContactDataAdapter(private val list: List<Inform>)
    : RecyclerView.Adapter<ContactDataAdapter.ContactDataHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ContactDataHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.holder_about_us, parent, false)

        return ContactDataHolder(view)
    }

    override fun onBindViewHolder(holder: ContactDataHolder, position: Int) {
        val list = list[position]
        holder.header.text = list.header
        holder.footer.text = list.footer
    }

    override fun getItemCount() = list.size

    class ContactDataHolder(view: View) : RecyclerView.ViewHolder(view){
        val header: TextView = view.findViewById(R.id.about_us_up_text)
        val footer: TextView = view.findViewById(R.id.about_us_down_text)
    }
}


// model class for inform recycler
data class Inform(var header: String = "",
                  var footer: String = "")
