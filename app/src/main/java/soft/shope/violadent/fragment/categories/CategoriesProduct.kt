package soft.shope.violadent.fragment.categories

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import soft.shope.violadent.MainActivity
import soft.shope.violadent.R
import soft.shope.violadent.extensions.startAndStop
import soft.shope.violadent.fragment.retail_product.RetailProductViewModel
import soft.shope.violadent.parcer_file.CategoryList

class CategoriesProduct : Fragment() {
// buttonNavigation
    private lateinit var backCategoriesButton: ImageView
    private lateinit var saveCategoriesButton: TextView
// recyclerView
    private lateinit var recyclerCategories: RecyclerView
// refresh
    private lateinit var refreshCategories: SwipeRefreshLayout
// viewModels
    private val retailViewModel: RetailProductViewModel by activityViewModels()
    private val categoriesViewModel: CategoriesViewModel by activityViewModels()

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.categories, container, false)
// buttonNavigation
        backCategoriesButton = view.findViewById(R.id.back_product)
        saveCategoriesButton = view.findViewById(R.id.save_id_categories) // to save the selected categories
// recyclerView
        recyclerCategories = view.findViewById(R.id.categories_filter)
// refresh
        refreshCategories = view.findViewById(R.id.refresh_categories)

        backCategoriesButton.setOnClickListener {
            (activity as MainActivity).navController.popBackStack()
            categoriesViewModel.categoryList.value = emptyList()
        }
// for start and stop refresh layout
        refreshCategories.startAndStop(true)
// make response for categories and update recycler
        var listCat = emptyList<CategoryList>()
        var listFilterCat = emptyList<CategoryList>()

        categoriesViewModel.categoryList.observe(activity as LifecycleOwner){ list ->
            listFilterCat = list
        }

        categoriesViewModel.selectedListCategory.observe(activity as LifecycleOwner){ list ->
            listCat = list
        }

        when(listCat.isEmpty()){
            true -> categoriesViewModel.getAndUpdateCategoriesList(recyclerCategories, context,
                activity, saveCategoriesButton, refreshCategories, retailViewModel)

            else -> {
                listCat.map { i -> i.check = listFilterCat.contains(i) }

                categoriesViewModel.updatesCategoriesRecycler(recyclerCategories, listCat, context,
                    activity, saveCategoriesButton, retailViewModel)
                    refreshCategories.startAndStop(false) }
        }

        return view
    }

}