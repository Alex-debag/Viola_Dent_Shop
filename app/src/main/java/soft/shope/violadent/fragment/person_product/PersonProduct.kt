package soft.shope.violadent.fragment.person_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import soft.shope.violadent.MainActivity
import soft.shope.violadent.R
import soft.shope.violadent.extensions.followLink
import soft.shope.violadent.extensions.imageOnTheLincToView
import soft.shope.violadent.extensions.makePhoneCall
import soft.shope.violadent.parcer_file.ContactData

class PersonProduct: Fragment() {
// textView
    private lateinit var textManufacturer: TextView
    private lateinit var textNameProduct: TextView
    private lateinit var textQuantity: TextView
    private lateinit var textPrise: TextView
    private lateinit var textDescription: TextView
// button
    private lateinit var buttonClose: ImageView
    private lateinit var buttonCall: TextView
    private lateinit var buttonTransitionLink: TextView
//Image
    private lateinit var imageProduct: ImageView
// viewModels
    private val personProductViewModel: PersonProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.product_personal_page, container, false)
// button
        buttonClose = view.findViewById(R.id.close_view)
        buttonCall = view.findViewById(R.id.call_button)
        buttonTransitionLink = view.findViewById(R.id.link_button_transition)
// textView
        textManufacturer = view.findViewById(R.id.text_name_manufacturer)
        textNameProduct = view.findViewById(R.id.text_name_product)
        textQuantity = view.findViewById(R.id.text_quantity)
        textPrise = view.findViewById(R.id.text_prise)
        textDescription = view.findViewById(R.id.text_description_product)
//Image
        imageProduct = view.findViewById(R.id.image_personal_product)

        buttonClose.setOnClickListener {
            (activity as MainActivity).navController.popBackStack()
        }
        
        var url: String? = null
// get show data personal product
        personProductViewModel.dataPageProduct.observe(activity as LifecycleOwner){
            textManufacturer.text = it.manufacturer
            textNameProduct.text = it.name_item

            url = it.link

            val quantity = "Наявнiсть: " + it.quantity
            val prise = "₴" + it.price?.removeSuffix("00")?.lowercase()

            textQuantity.text = quantity
            textPrise.text = prise
            textDescription.text = it.description

            println("it.category == ${it.image.toString()} ")

            it.image?.imageOnTheLincToView(imageProduct)

            if (imageProduct.drawable == null){
                imageProduct.setImageResource(R.drawable.empty_photo)
            }

        }
// call on support phone
        buttonCall.setOnClickListener { // string extension
            ContactData().contactPhone.makePhoneCall( thisFragment = this,
                                                      requestCall = 0,
                                                      activity = activity )
        }
        buttonTransitionLink.setOnClickListener {
            url?.followLink(this)
        }

        return view
    }

}