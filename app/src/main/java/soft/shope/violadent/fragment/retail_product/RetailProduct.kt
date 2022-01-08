package soft.shope.violadent.fragment.retail_product

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import soft.shope.violadent.MainActivity
import soft.shope.violadent.R
import soft.shope.violadent.extensions.hint
import soft.shope.violadent.extensions.startAndStop
import soft.shope.violadent.fragment.categories.CategoriesViewModel
import soft.shope.violadent.fragment.person_product.PersonProductViewModel
import soft.shope.violadent.parcer_file.*

class RetailProduct : Fragment(){
// buttonNavigation
    private lateinit var changeView: ImageView
    private lateinit var goToAboutUs: ImageView
    private lateinit var goToCategories: TextView
// Recycler
    private lateinit var goodsRecycler: RecyclerView
    private lateinit var categoriesRecycler: RecyclerView
// searchProduct
    private lateinit var searchProduct: EditText
// textHintForCategoriesRecycler
    private lateinit var textHint: TextView
// viewModels
    private val parserViewModel: ParserJsonViewModel by activityViewModels()
    private val retailProductViewModel: RetailProductViewModel by activityViewModels()
    private val personProductViewModel: PersonProductViewModel by activityViewModels()
    private val categoriesViewModel: CategoriesViewModel by activityViewModels()
// SwipeRefresh
    private lateinit var refresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.retail_product_main, container, false)
// sharedPreferences
        val share = activity?.getSharedPreferences("SHARED", Context.MODE_PRIVATE)
// buttonNavigation
        changeView = view.findViewById(R.id.change_view)
        goToAboutUs = view.findViewById(R.id.go_to_about_us)
        goToCategories = view.findViewById(R.id.come_to_categories)
// searchProduct
        searchProduct = view.findViewById(R.id.search_product)
// Recycler
        goodsRecycler = view.findViewById(R.id.goods_recycler)
        categoriesRecycler = view.findViewById(R.id.recycler_selected_categories)
// textHintForCategoriesRecycler
        textHint = view.findViewById(R.id.text_hint)
// refresh
        refresh = view.findViewById(R.id.refresh_product)

        categoriesRecycler.hint(textHint)

        categoriesViewModel.categoryList.observe(activity as LifecycleOwner){
            categoriesRecycler.hint(textHint, it)
            retailProductViewModel.updateCategoriesRecycler(categoriesRecycler, it, context, categoriesViewModel)
        }

        goToAboutUs.setOnClickListener {
            (activity as MainActivity).navController.navigate(R.id.action_retailProduct_to_aboutUs)
        }
        goToCategories.setOnClickListener {
            (activity as MainActivity).navController.navigate(R.id.action_retailProduct_to_categoriesProduct)
        }

        refresh.startAndStop(true)

        var listProduct: List<ItemsShopList>? = null

        retailProductViewModel.productList.observe(activity as LifecycleOwner) {
            listProduct = it
            retailProductViewModel.cashProduct(it, share, activity, personProductViewModel, context,
            goodsRecycler, refresh)
        }

        when(listProduct){
            null -> retailProductViewModel.getProductList(parserViewModel){
                retailProductViewModel.productList.postValue(it)
            }
        }

        retailProductViewModel.categoryListId.observe(activity as LifecycleOwner){
            listProduct = listProduct?.let { list ->
                categoriesViewModel.filteringProducts(list, it)
            }

            if (listProduct?.isEmpty() == true && it.isEmpty()){
                categoriesRecycler.hint(textHint)
                retailProductViewModel.getProductList(parserViewModel){ fullList ->
                    retailProductViewModel.productList.postValue(fullList)
                }
            }
            retailProductViewModel.cashProduct(listProduct, share, activity, personProductViewModel, context,
                goodsRecycler, refresh)
          //  println("listProduct == $listProduct, itList == $it")
        }

        val numFormat = share?.getInt(ShareKeyToSave.FORMAT.name, 0) ?: 0
        retailProductViewModel.changeChoseButton(numFormat,changeView)

        changeView.setOnClickListener {
            retailProductViewModel.changeFormatSlotWithButton(retailProductViewModel, share, changeView, goodsRecycler)
        }

        searchProduct.doAfterTextChanged {
            retailProductViewModel.getSearchProduct(listProduct, it)
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.light_grey)
    }
}






// set keys
enum class ShareKeyToSave {
    FORMAT
}



