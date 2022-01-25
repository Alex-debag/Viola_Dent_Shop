package soft.shope.violadent.fragment.about_us

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import soft.shope.violadent.MainActivity
import soft.shope.violadent.R
import soft.shope.violadent.parcer_file.ContactData

class AboutUs : Fragment() {
// buttonNavigate
    private lateinit var buttonBackProduct: ImageView
// recycler
    private lateinit var recyclerInform: RecyclerView
// buttonSocialNetworks
    private lateinit var facebook: ImageView
    private lateinit var telegram: ImageView
    private lateinit var viber: ImageView
    private lateinit var youtube: ImageView
// buttonLinkWebsite
    private lateinit var privacyPolicy: TextView
    private lateinit var website: TextView
// viewModels
    private val aboutUsViewModel: AboutUsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.about_us, container, false)
// buttonNavigate
        buttonBackProduct = view.findViewById(R.id.back_product)
// recycler
        recyclerInform = view.findViewById(R.id.recycler_inform)
// buttonSocialNetworks
        facebook = view.findViewById(R.id.facebook)
        telegram = view.findViewById(R.id.telegram)
        viber = view.findViewById(R.id.viber)
        youtube = view.findViewById(R.id.youtube)
// buttonLinkWebsite
        privacyPolicy = view.findViewById(R.id.link_privacy_policy)
        website = view.findViewById(R.id.link_website)
// back product
        buttonBackProduct.setOnClickListener {
            (activity as MainActivity).navController.popBackStack()
        }
// added all link social network in button
        facebook.setOnClickListener { aboutUsViewModel.goToUrl(activity, ContactData().facebookUrl) }
        telegram.setOnClickListener { aboutUsViewModel.goToUrl(activity, ContactData().telegramUrl) }
        viber.setOnClickListener { aboutUsViewModel.goToUrl(activity, ContactData().viberUrl) }
        youtube.setOnClickListener { aboutUsViewModel.goToUrl(activity, ContactData().youtubeUrl) }

        privacyPolicy.setOnClickListener { aboutUsViewModel.goToUrl(activity, ContactData().privacyPolicyUrl) }
        website.setOnClickListener { aboutUsViewModel.goToUrl(activity, ContactData().urlSite) }
// update recycler with information about us
        aboutUsViewModel.updateRecyclerInform(recyclerInform, context, this)
        recyclerInform.isNestedScrollingEnabled = false // prohibition scroll

        return view
    }
    override fun onStart() {
        super.onStart()
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.light_grey)
    }

}






