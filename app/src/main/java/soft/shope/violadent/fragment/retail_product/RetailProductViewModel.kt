package soft.shope.violadent.fragment.retail_product

import android.content.Context
import android.content.SharedPreferences
import android.text.Editable
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import soft.shope.violadent.R
import soft.shope.violadent.extensions.startAndStop
import soft.shope.violadent.fragment.categories.CategoriesViewModel
import soft.shope.violadent.fragment.person_product.PersonProductViewModel
import soft.shope.violadent.parcer_file.CategoryList
import soft.shope.violadent.parcer_file.ItemsShopList
import soft.shope.violadent.parcer_file.ParserJsonViewModel

class RetailProductViewModel : ViewModel(){

    private val checkFormat: MutableLiveData<Int> by lazy {
        MutableLiveData()
    }
    val productList: MutableLiveData<List<ItemsShopList>> by lazy {
        MutableLiveData()
    }
    private val searchProductList: MutableLiveData<List<ItemsShopList>> by lazy {
        MutableLiveData()
    }
    val categoryListId: MutableLiveData<List<String>> by lazy {
        MutableLiveData()
    }
// for update categories
    fun updateCategoriesRecycler( categoriesRecycler: RecyclerView, categoryList: List<CategoryList>?,
          context: Context?, categoriesViewModel: CategoriesViewModel, activity: FragmentActivity?){

        categoriesRecycler.layoutManager = LinearLayoutManager( context,
            LinearLayoutManager.HORIZONTAL,false )

        categoriesRecycler.adapter = categoryList?.let {
            CategoriesRetailAdapter( categoriesList = it,
                                     retailProductViewModel = this,
                                     categoriesViewModel = categoriesViewModel,
                                     activity = activity )
        }

    }

// get product list if the list still empty
    inline fun getProductList(parserViewModel: ParserJsonViewModel,
                              crossinline output: (List<ItemsShopList>?) -> (Unit)){
        parserViewModel.requestMethodArray(
            "https://violadent.com/AndroidAPI/new/json/feed.json"){

            val gson = GsonBuilder().create()

            try {
                val mUser = gson.fromJson(it?.trimIndent(), Array<ItemsShopList>::class.java).toList()
                runBlocking { launch(Dispatchers.Main) {
                    output(mUser)
                }
                }
            }catch (e: Error){
                println("Error")
            }
        }
    }

// for filtering product through the search line
    fun getSearchProduct(list: List<ItemsShopList>?, searchValue: Editable? = null){

        viewModelScope.launch(Dispatchers.IO) {

            val productList = mutableListOf<ItemsShopList>()

        when (searchValue) {
            null -> {
                list?.let {
                    searchProductList.postValue(productList)
                }
            }
            else -> {

                val listT = list?.filter { item ->
                    item.name_item
                        ?.lowercase()
                        ?.contains(searchValue.toString().lowercase()) ?: false
                }

                searchProductList.postValue(listT)
            }
        }
        }
    }
// change format product slots
private fun changeChoseFormat(format: Int) : Int{
       return when(format){
            0 -> format + 1
            1 -> format + 1
            2 -> format - 2
           else -> 0
       }
    }
// change view formatButton
    fun changeChoseButton(numFormat: Int, changeView: ImageView){
        when(numFormat){
            0 ->  changeView.setImageResource(R.drawable.ic_one_big_slot)
            1 -> changeView.setImageResource(R.drawable.ic_one_small_slot)
            else -> changeView.setImageResource(R.drawable.ic_double_slot)
        }
    }
// get holder format product slots
    fun getHolder (checkFormat: Int) : Int {
        return when(checkFormat) {
            0 -> R.layout.holder_single_vertical
            1 -> R.layout.holder_single_horizontal
            else -> R.layout.holder_double_horizontal
        }
    }
// update view and data recyclerProduct
    private fun updateGoodsRecycler(recyclerView: RecyclerView, list: List<ItemsShopList>?,
                                    context: Context?, holder: Int, personViewModel: PersonProductViewModel,
                                    activity: FragmentActivity?){
        when(holder){
            2 -> {
                recyclerView.layoutManager = StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL)
            }
            else -> {
                recyclerView.layoutManager = LinearLayoutManager(context)
            }
        }

        recyclerView.adapter = list?.let { GoodsAdapter(it, holder, this,
            personViewModel, activity) }
    }
    @Deprecated("",
        replaceWith = ReplaceWith(
        expression = "startStopRefresh(refresh, false)",
        imports = ["refresh.startAndStop(false)"]
    ))
// use swipeLayout Extension
    fun startStopRefresh(refresh: SwipeRefreshLayout, moveValue: Boolean){
        refresh.setColorSchemeResources(R.color.normal_grey)
        refresh.isRefreshing = moveValue
        refresh.isEnabled = moveValue
    }
// save product list in memory end return list during search
    fun cashProduct(listProduct: List<ItemsShopList>?, share: SharedPreferences?, activity: FragmentActivity?,
    personViewModel: PersonProductViewModel, context: Context?, goodsRecycler: RecyclerView,
    refresh: SwipeRefreshLayout) {

        refresh.startAndStop(false)

        val getFormat = share?.getInt(ShareKeyToSave.FORMAT.name, 0) ?: 0

        searchProductList.observe(activity as LifecycleOwner){ list ->

            updateGoodsRecycler(goodsRecycler, list, context, getFormat, personViewModel, activity)

            checkFormat.observe(activity as LifecycleOwner) {
                updateGoodsRecycler(goodsRecycler, list, context, it, personViewModel, activity)

                share?.edit()?.putInt(ShareKeyToSave.FORMAT.name, it)?.apply()

            }

        }

        updateGoodsRecycler(goodsRecycler, listProduct, context, getFormat, personViewModel,
            activity)

        checkFormat.observe(activity as LifecycleOwner) {

            updateGoodsRecycler(goodsRecycler, listProduct, context, it,
                personViewModel, activity)

            share?.edit()?.putInt(ShareKeyToSave.FORMAT.name, it)?.apply()

        }

    }
    // change format slot and image on button
    fun changeFormatSlotWithButton (retailProductViewModel: RetailProductViewModel,
                                    share: SharedPreferences?, changeView: ImageView,
                                    goodsRecycler: RecyclerView){

        retailProductViewModel.checkFormat.value =
            share?.getInt(ShareKeyToSave.FORMAT.name, 0)
                ?.let { num -> retailProductViewModel.changeChoseFormat(num) }

        share?.getInt(ShareKeyToSave.FORMAT.name, 0)
            ?.let { num -> retailProductViewModel.changeChoseButton(num,changeView) }

        goodsRecycler.scheduleLayoutAnimation()
    }
}