package soft.shope.violadent.fragment.person_product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import soft.shope.violadent.parcer_file.ItemsShopList

class PersonProductViewModel: ViewModel() {

    val dataPageProduct: MutableLiveData<ItemsShopList> by lazy{
        MutableLiveData()
    }
}