package kz.busjol.base

import androidx.annotation.StringRes
import androidx.navigation.NavDirections
import java.io.Serializable

interface EventHandler {

    fun getViewModel(): BaseViewModel?
    fun onActionEvent(event: ActionEvent)
    fun onNavigationEvent(event: NavigationEvent)

    fun onBackPressed(): (() -> Unit)?

}

sealed class SystemEvent : Serializable


/**
 * NavigationEvent can be used to:
 * 1) navigate to other activity
 * 2) navigate to other fragment
 * 3) show dialogs of DialogFragment (including BottomSheetDialogFragment), if pointed through navigation graph
 */
class NavigationEvent(val direction: NavDirections) : SystemEvent()

/**
 * Intended for local events that handle on EndpointFragment
 */
open class ActionEvent : SystemEvent()

object NoInternetErrorEvent : ActionEvent()
object TimeoutErrorEvent : ActionEvent()
object OpenMainActivityEvent : ActionEvent()
class OpenAuthActivityEvent() : ActionEvent()
object CallBackPressedEvent : ActionEvent()
object InvalidPhoneFormatEvent : ActionEvent()

/**
 * Intended for showing simple common error dialogs with fixed title, common to whole application
 */
class CommonErrorEvent(@StringRes val message: Int) : ActionEvent()
class NetworkErrorEvent(val errorText: String) : ActionEvent()