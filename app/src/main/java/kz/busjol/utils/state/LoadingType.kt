package kz.busjol.utils.state

sealed class LoadingType {
    object Progress : LoadingType()
    object Skeleton : LoadingType()
    object BlockingDialog : LoadingType()
}
