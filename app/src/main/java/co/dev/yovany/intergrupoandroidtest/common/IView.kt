package co.dev.yovany.intergrupoandroidtest.common

public interface IView {
    fun showProgressbar()
    fun hideProgressbar()
    fun showMessage(error: String?, messageType: MessageType)
}