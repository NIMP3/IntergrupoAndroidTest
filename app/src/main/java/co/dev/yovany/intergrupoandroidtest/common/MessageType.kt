package co.dev.yovany.intergrupoandroidtest.common

import co.dev.yovany.intergrupoandroidtest.R

enum class MessageType(val data: String, val animation: Int) {
    SERVER("ERROR EN EL SERVIDOR", R.raw.error),
    SYSTEM("ERROR EN EL SISTEMA", R.raw.error),
    WARNING("ALERTA", R.raw.warning),
    LOAD("CARGANDO", R.raw.loader),
    INFO("INFORMACIÓN", R.raw.info)
}