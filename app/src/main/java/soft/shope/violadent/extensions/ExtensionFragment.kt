package soft.shope.violadent.extensions

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

// for multiCheck permission, I was used tailrec because
// I don't how it function will be used in the future
fun Fragment.checkPermission(vararg permissions: String, res : (Boolean) -> (Unit)) {

    var checkPermission = 0

    permissions.forEach { permission ->

        if(ActivityCompat.checkSelfPermission(this.requireContext(),
                permission ) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this.requireActivity(),
                permissions, 1)

        }else { checkPermission++

            println(checkPermission)
        }
    }

    if (permissions.size == checkPermission){
        res(true)
    }

}