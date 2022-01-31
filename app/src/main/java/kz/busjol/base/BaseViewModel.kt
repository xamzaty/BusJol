package kz.busjol.base

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import kotlinx.coroutines.launch
import kz.busjol.R
import kz.busjol.error.ApiException
import kz.busjol.utils.Event
import java.io.InterruptedIOException
import java.io.Serializable
import java.net.SocketException
import java.net.UnknownHostException

abstract class BaseViewModel : ViewModel() {

    val loading = MutableLiveData(false)
    val refreshing = MutableLiveData(false)

    val list = MutableLiveData<List<Any>>()
    val actionEvents = MutableLiveData<Event<SystemEvent>>()

    fun fireEvent(actionEvent: ActionEvent) {
        actionEvents.value = Event(actionEvent)
    }

    fun fireCloseKeyboardEvent() {
        fireEvent(EventCloseKeyboard)
    }

    fun fireNoInternetError() {
        fireEvent(NoInternetErrorEvent)
    }

    fun fireCommonError(@StringRes message: Int) {
        fireEvent(CommonErrorEvent(message))
    }

    // Shortcuts for NAVIGATION events
    fun navigate(direction: NavDirections) {
        actionEvents.value = Event(NavigationEvent(direction))
    }

    fun navigateBack(withResult: Any? = null) {
        actionEvents.value = Event(ActionResultEvent(withResult))
    }

    fun postBack(result: Any? = null) {
        actionEvents.value = Event(ActionPostBackEvent(result))
    }

    fun openAuthActivity() {
        fireEvent(OpenAuthActivityEvent())
    }

    fun openMainActivity() {
        fireEvent(OpenMainActivityEvent)
    }

    fun callBackPressed() {
        fireEvent(CallBackPressedEvent)
    }

    fun showSnackbar(@StringRes id: Int) {
        fireEvent(ShowSnackBarEvent(id))
    }

    fun showToast(@StringRes id: Int) {
        fireEvent(ShowToastEvent(id))
    }

    fun showBlockingLoader() {
        fireEvent(BlockingLoaderEvent(true))
    }

    fun hideBlockingLoader() {
        fireEvent(BlockingLoaderEvent(false))
    }

    open fun onNavigationResult(result: Any) {}

    fun <T> networkBlockingRequest(
        request: suspend () -> T,
        onSuccess: (T) -> Unit = {},
        onError: ((Exception) -> Unit)? = null
    ) = viewModelScope.launch {
        showBlockingLoader()
        try {
            val response = request()
            hideBlockingLoader()
            onSuccess(response)
        } catch (ex: Exception) {
            hideBlockingLoader()
            try {
                onError?.invoke(ex) ?: throw ex
            } catch (ex: Exception) {
                handleException(ex)
            }
        }
    }

    fun <T> networkRequest(
        request: suspend () -> T,
        onSuccess: (T) -> Unit = {},
        onError: ((Exception) -> Unit)? = null,
        loadingState: MutableLiveData<Boolean>? = loading
    ) = viewModelScope.launch {
        if (loadingState?.value == null || loadingState.value == false) {
            loadingState?.value = true

            try {
                val response = request()
                loadingState?.value = false
                onSuccess(response)
            } catch (ex: Exception) {
                loadingState?.value = false

                try {
                    onError?.invoke(ex) ?: handleException(ex)
                } catch (ex: Exception) {
                    handleException(ex)
                }
            }
        }
    }

    fun <T> networkSilentRequest(
        request: suspend () -> T
    ) = viewModelScope.launch {
        try {
            request()
        } catch (ex: Exception) {
            println(ex)
        }
    }

    fun handleException(ex: Throwable) {
        println(ex)
        when (ex) {
            is ApiException -> handleApiException(ex)
            is UnknownHostException -> fireNoInternetError()
            is InterruptedIOException,
            is SocketException
            -> fireEvent(TimeoutErrorEvent)
            is kotlinx.coroutines.CancellationException -> {
                ex.printStackTrace()
                // do nothing, just cancelling already started coroutine
            }
            else -> {
                fireCommonError(R.string.error_unknown)
            }
        }
    }

    private fun handleApiException(ex: ApiException?) {
        when (ex?.code?.toLowerCase()) {
            ApiException.RESOURCE_BLOCKED -> {
                val seconds = if (ex.data != null) {
                    ((ex.data as Map<String, Any>)["millisecondsLeft"] as Double).toInt() / 1000
                } else 0
                fireEvent(ResourceBlocked(seconds))
            }
            ApiException.TOKEN_EXPIRED -> fireEvent(DeviceTokenExpiredErrorEvent)
            ApiException.INVALID_TOKEN -> fireEvent(TokenExpiredErrorEvent)
            ApiException.PHONE_TOKEN_EXPIRED -> fireEvent(TokenExpiredErrorEvent)
            ApiException.USER_BLOCKED -> fireEvent(UserBlockedEvent())

            else -> ex?.getErrorText()?.let { fireEvent(NetworkErrorEvent(it)) }
                ?: run { fireCommonError(R.string.error_on_server) }
        }
    }
}

object EventValidate : ActionEvent()
object EventRefreshData : ActionEvent()
object EventOpenKeyboard : ActionEvent()
object EventCloseKeyboard : ActionEvent()
object EventAlreadyHas : ActionEvent()
object EventNotFound : ActionEvent()
object TimeWarning : ActionEvent()
class SuccessEvent(val requestId: String? = null) : ActionEvent(), Serializable
object TokenExpiredErrorEvent : ActionEvent()
object DeviceTokenExpiredErrorEvent : ActionEvent()
object OpenHistoryTabEvent : ActionEvent()
object OpenMoreTabEvent : ActionEvent()
object OpenHomeTabEvent : ActionEvent()
object ShowFileChooserEvent : ActionEvent()
object ShowBodyErrorEvent : ActionEvent()
class ShowBottomTabBadgeCount(val tabId: Int, val count: Int? = null) : ActionEvent()
class NotificationEvent(val text: String) : ActionEvent()
class BlockingLoaderEvent(val show: Boolean) : ActionEvent()
class IncorrectCode(val triesLeft: Int) : ActionEvent()
class ShowSnackBarEvent(@StringRes val id: Int) : ActionEvent()
class ShowSnackBarStringEvent(val text: String) : ActionEvent()
class ShowToastEvent(@StringRes val id: Int) : ActionEvent()
class ResourceBlocked(val secondsLeft: Int) : ActionEvent()
class ActionResultEvent(val result: Any? = null) : ActionEvent()
class ActionPostBackEvent(val result: Any? = null) : ActionEvent()
class ShareFileEvent(val filename: String, val bytes: ByteArray) : ActionEvent()
class OpenFileEvent(val filename: String, val bytes: ByteArray? = null) : ActionEvent()
class IncorrectPhoneEvent : ActionEvent()
class UserBlockedEvent : ActionEvent()
class ItemChangeEvent(val position: Int? = null) : ActionEvent()
class SetTitleEvent(@StringRes val id: Int) : ActionEvent()