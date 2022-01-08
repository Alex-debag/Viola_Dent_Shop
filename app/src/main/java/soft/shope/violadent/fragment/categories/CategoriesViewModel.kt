package soft.shope.violadent.fragment.categories

import android.content.Context
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import soft.shope.violadent.extensions.getBody
import soft.shope.violadent.extensions.startAndStop
import soft.shope.violadent.fragment.retail_product.RetailProductViewModel
import soft.shope.violadent.parcer_file.*

class CategoriesViewModel : ViewModel() {

    val categoryList: MutableLiveData<List<CategoryList>> by lazy {
        MutableLiveData()
    }
// update recycler with categories
    private fun updatesCategoriesRecycler(recyclerView: RecyclerView, list: List<CategoryList>?,
                                          context: Context?, activity: FragmentActivity?,
                                          buttonSaveList: TextView,
                                          retailProductViewModel: RetailProductViewModel){
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = CategoriesRecyclerAdapter( list,
            this,
            activity,
            buttonSaveList,
            retailProductViewModel)
    }
// make response for categories and update recycler
    fun getAndUpdateCategoriesList(recyclerCategories: RecyclerView, context: Context?,
                                   activity: FragmentActivity?, buttonSaveList: TextView,
                                   refreshCategories: SwipeRefreshLayout,
                                   retailProductViewModel: RetailProductViewModel){
    // make response
        ParserJsonViewModel().getResponse( dataClass = Category(),
                                           baseURLString = "https://violadent.com/AndroidAPI/",
                                           typeRequest = TypeRequest().post,
                                           body = CategoriesBody().getBody()){ category ->

            viewModelScope.launch(Dispatchers.Main) {
                // update categories recycler
                updatesCategoriesRecycler( recyclerView = recyclerCategories,
                                           list = category?.data,
                                           context = context,
                                           activity = activity,
                                           buttonSaveList = buttonSaveList,
                                           retailProductViewModel = retailProductViewModel)
                refreshCategories.startAndStop(false)
            }
        }
    }
// filtering selected categories
    fun filteringCategories(listCategories: List<CategoryList>?) : List<String> {

        val filteringCategories = listCategories?.filter { item ->
            item.check == true
        }

        val checkCategoriesList = mutableListOf<String>()

        if (filteringCategories != null) {
            for (i in filteringCategories){
                if (i.check == true){
                    i.category_id?.let { checkCategoriesList.add(it) }
                }
            }
        }
        return checkCategoriesList
    }
// fun get list with id categories
    fun getCategoriesIdList(listCategories: List<CategoryList>?){
        val filteringCategories = listCategories?.filter { item ->
            item.check == true
        }
        categoryList.postValue(filteringCategories)
    }

// filtering actual product with select category
    fun filteringProducts(listProducts: List<ItemsShopList>?, listFilteredCategories: List<String>?) : List<ItemsShopList>? {

    return when{
        listProducts?.isEmpty() == true && listFilteredCategories?.isEmpty() == true -> emptyList()

        listFilteredCategories?.size != 0 -> {
            listProducts?.filter { item ->
                listFilteredCategories?.contains(item.category_id) ?: true
            }
        }
            else -> null
        }
    }

//// fun for observer and update actual category list id
//    fun updateCategoriesId (categoriesList: List<CategoryList>?,
//                            retailProductViewModel: RetailProductViewModel) = viewModelScope.launch(Dispatchers.IO) {
//            println("listCat == ${filteringCategories(categoriesList)}")
//            retailProductViewModel.categoryListId.postValue(filteringCategories(categoriesList))
//        }

}
