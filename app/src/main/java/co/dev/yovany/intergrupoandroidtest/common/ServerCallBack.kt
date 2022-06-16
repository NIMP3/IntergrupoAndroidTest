package co.dev.yovany.intergrupoandroidtest.common

import android.content.Context
import co.dev.yovany.intergrupoandroidtest.R

abstract class ServerCallBack(val view: IView, val context: Context) {

    /**
     * Procesa la respuesta de no conectividad a internet
     */
    open fun onNetworkError() {
        view.showMessage(context.getString(R.string.message_error_no_network), MessageType.WARNING)
    }

    /**
     * Procesa la respuesta para un error de tipo servidor
     * @param errorCode : Codigo de Error
     */
    open fun onServerError(errorCode: String) {
        view.showMessage("${context.getString(R.string.message_error_server)} - ERROR: $errorCode", MessageType.SERVER)
    }

    /**
     * Procesa la respuesta para un error del sistema
     * @param errorCode : Codigo de Error
     */
    open fun onSystemError(errorCode: String) {
        view.showMessage("${context.getString(R.string.message_error_system)} - ERROR: $errorCode", MessageType.SYSTEM)
    }

    /**
     * Procesa la respuesta en el caso de no obtener datos de la petición
     * @param entityName : Nombre de la entidad
     */
    open fun onNoDataError(entityName: String) {
        view.showMessage("${context.getString(R.string.message_no_data)} $entityName", MessageType.INFO)
    }

}