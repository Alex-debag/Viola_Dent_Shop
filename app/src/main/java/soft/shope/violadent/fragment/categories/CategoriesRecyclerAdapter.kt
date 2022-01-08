package soft.shope.violadent.fragment.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import soft.shope.violadent.MainActivity
import soft.shope.violadent.R
import soft.shope.violadent.fragment.retail_product.RetailProductViewModel
import soft.shope.violadent.parcer_file.CategoryList

class CategoriesRecyclerAdapter(private val categoriesList: List<CategoryList>?,
                                private val categoriesViewModel: CategoriesViewModel,
                                private val activity: FragmentActivity?,
                                private val buttonSaveList: TextView,
                                private val retailViewModel: RetailProductViewModel
)
    : RecyclerView.Adapter<CategoriesRecyclerAdapter.HolderCategoriesRecycler>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): HolderCategoriesRecycler {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.holder_categories_filter, parent, false)

        return  HolderCategoriesRecycler(view, categoriesList, buttonSaveList, retailViewModel)
    }

    override fun onBindViewHolder(holder: HolderCategoriesRecycler, position: Int) {
        val list = categoriesList?.get(position)

        changeCondition = { layoutPosition ->
            categoriesList?.get(layoutPosition)?.check = !categoriesList?.get(layoutPosition)?.check!!

            notifyItemChanged(layoutPosition)
        }

        when(list?.check){
            true -> holder.radioButton.setImageResource(R.drawable.ic_image_two_round)
            else -> holder.radioButton.setImageResource(R.drawable.ic_image_one_round)
        }

        holder.radioText.text = list?.name

    }

    override fun getItemCount() = categoriesList?.size ?: 0

    inner class HolderCategoriesRecycler(view: View, categoriesList: List<CategoryList>? = null,
                                         buttonSaveList: TextView,
                                         retailViewModel: RetailProductViewModel)
        : RecyclerView.ViewHolder(view){

        val radioButton: ImageView = view.findViewById(R.id.radio_button)
        val radioText: TextView = view.findViewById(R.id.radio_text)

        init {
            view.setOnClickListener {
                changeCondition(layoutPosition)
            }

            buttonSaveList.setOnClickListener {
                categoriesList?.let { list ->

                    retailViewModel.categoryListId.value =
                        categoriesViewModel.filteringCategories(list)

                    categoriesViewModel.getCategoriesIdList(list)
                }
//                retailViewModel.categoryListId.observe(activity as LifecycleOwner){
                    (activity as MainActivity).navController.popBackStack()
              //  }
            }

        }
    }

    var changeCondition = { _: Int -> }

}