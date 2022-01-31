package kz.busjol.utils

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 *
 * @author Jose Alcerreca, spizjeno by Aibek
 * @see <a href="https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150">
 *     Article
 *     </a>
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
     */
    fun get(): T? {
        return if (hasBeenHandled) null
        else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peek(): T = content
}