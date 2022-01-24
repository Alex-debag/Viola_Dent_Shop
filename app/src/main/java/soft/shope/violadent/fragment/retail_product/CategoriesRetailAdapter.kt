package soft.shope.violadent.fragment.retail_product

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import soft.shope.violadent.MainActivity
import soft.shope.violadent.R
import soft.shope.violadent.fragment.categories.CategoriesViewModel
import soft.shope.violadent.parcer_file.CategoryList

class CategoriesRetailAdapter(private val categoriesList: List<CategoryList>,
                              private val retailProductViewModel: RetailProductViewModel,
                              private val categoriesViewModel: CategoriesViewModel,
                              private val activity: FragmentActivity?)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val goCat = 0
    private val slot = 1
    private val listCategory: MutableList<CategoryList> = categoriesList.toMutableList()

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> goCat
            else -> slot
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){
            goCat -> {
                val viewCat = LayoutInflater.from(parent.context)
                    .inflate(R.layout.holder_go_to_categories, parent, false)

                GoCategoriesHolder(viewCat, activity)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.holder_chose_recycler, parent, false)

                CategoriesRetailHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is CategoriesRetailHolder){
            val list = listCategory[position - 1]
            deleteCategories = { deleteAndUpdateCategory(it - 1) }

            holder.textCategories.text = list.name

        }else{
            println("motion in categories")
        }
    }

    override fun getItemCount() = listCategory.size + 1

    inner class CategoriesRetailHolder(view: View) : RecyclerView.ViewHolder(view){

        val textCategories: TextView = view.findViewById(R.id.chose_categories_slot)

        init {
            view.setOnClickListener {
                deleteCategories(layoutPosition)
            }
        }
    }

    inner class GoCategoriesHolder(view: View, activity: FragmentActivity?) : RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener {
                (activity as MainActivity).navController.navigate(R.id.action_retailProduct_to_categoriesProduct)

                categoriesViewModel.categoryList.value = listCategory
            }
        }
    }

    var deleteCategories = { _: Int -> }

// delete categories and update categoriesIdList
@SuppressLint("NotifyDataSetChanged")
private fun deleteAndUpdateCategory(positionCategory: Int)  {

    listCategory.removeAt(positionCategory)
    notifyDataSetChanged()

    retailProductViewModel.categoryListId.value =
        categoriesViewModel.filteringCategories(listCategory)
}
}