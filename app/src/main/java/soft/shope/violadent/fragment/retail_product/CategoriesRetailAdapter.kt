package soft.shope.violadent.fragment.retail_product

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import soft.shope.violadent.R
import soft.shope.violadent.fragment.categories.CategoriesViewModel
import soft.shope.violadent.parcer_file.CategoryList

class CategoriesRetailAdapter(categoriesList: List<CategoryList>?,
                              private val retailProductViewModel: RetailProductViewModel,
                              private val categoriesViewModel: CategoriesViewModel)
    : RecyclerView.Adapter<CategoriesRetailAdapter.CategoriesRetailHolder>(){

    private val listCategory: MutableList<CategoryList>? = categoriesList?.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesRetailHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.holder_chose_recycler, parent, false)

        return CategoriesRetailHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriesRetailHolder, position: Int) {
        val list = listCategory?.get(position)

        deleteCategories = { deleteAndUpdateCategory(it) }

        holder.textCategories.text = list?.name
    }

    override fun getItemCount() = listCategory?.size ?: 0

    inner class CategoriesRetailHolder(view: View)
        : RecyclerView.ViewHolder(view){

        val textCategories: TextView = view.findViewById(R.id.chose_categories_slot)

        init {
            view.setOnClickListener {
                deleteCategories(layoutPosition)
            }
        }

    }
    var deleteCategories = { _: Int -> }

// delete categories and update categoriesIdList
@SuppressLint("NotifyDataSetChanged")
private fun deleteAndUpdateCategory(positionCategory: Int)  {
  //  listCategory?.get(positionCategory)?.check = !listCategory?.get(positionCategory)?.check!!

    listCategory?.removeAt(positionCategory)
    notifyDataSetChanged()

    retailProductViewModel.categoryListId.value =
        categoriesViewModel.filteringCategories(listCategory)
   //   categoriesViewModel.updateCategoriesId(listCategory, retailProductViewModel)
}
}