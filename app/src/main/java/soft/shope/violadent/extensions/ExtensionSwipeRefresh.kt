package soft.shope.violadent.extensions

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import soft.shope.violadent.R
// for start and stop refresh layout
fun SwipeRefreshLayout.startAndStop(moveValue: Boolean, color: Int = R.color.blue_gear) {
    this.setColorSchemeResources(color)
    this.isRefreshing = moveValue
    this.isEnabled = moveValue
}