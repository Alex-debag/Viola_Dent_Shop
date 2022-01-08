package soft.shope.violadent.fragment.retail_product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import soft.shope.violadent.MainActivity
import soft.shope.violadent.R
import soft.shope.violadent.extensions.imageOnTheLincToView
import soft.shope.violadent.fragment.person_product.PersonProductViewModel
import soft.shope.violadent.parcer_file.ItemsShopList

class GoodsAdapter (private val list: List<ItemsShopList>,
                    private val holder: Int,
                    private val productViewModel: RetailProductViewModel,
                    private val personProductViewModel: PersonProductViewModel,
                    private val activity: FragmentActivity?)
    : RecyclerView.Adapter<GoodsAdapter.GoodsHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): GoodsHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(productViewModel.getHolder(holder), parent, false)
        return GoodsHolder(view, list, activity, personProductViewModel)
    }

    override fun onBindViewHolder(holder: GoodsHolder, position: Int) {

        val productList = list[position]

        productList.image?.imageOnTheLincToView(holder.imageProduct)

        if (holder.imageProduct.drawable == null){
            holder.imageProduct.setImageResource(R.drawable.empty_photo)
        }

        holder.nameProduct.text = productList.name_item
        holder.manufacturer.text = productList.manufacturer

        val loverPrise = "₴" + productList.price?.removeSuffix("00")?.lowercase()

        holder.priseProduct.text = loverPrise
    }

    override fun getItemCount() = list.size

    class GoodsHolder(view: View, list: List<ItemsShopList>?, activity: FragmentActivity?,
                      personProductViewModel: PersonProductViewModel) : RecyclerView.ViewHolder(view){

        val imageProduct: ImageView = view.findViewById(R.id.image_product)
        val nameProduct: TextView = view.findViewById(R.id.name_product)
        val manufacturer: TextView = view.findViewById(R.id.manufacturer)
        val priseProduct: TextView = view.findViewById(R.id.prise_product)

        init {
            view.setOnClickListener {
                val itemProduct = list?.get(layoutPosition)
                personProductViewModel.dataPageProduct.value = itemProduct

                (activity as MainActivity).navController
                    .navigate(R.id.action_retailProduct_to_personProduct)
            }
        }
    }

}