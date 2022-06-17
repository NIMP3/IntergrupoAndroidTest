package co.dev.yovany.intergrupoandroidtest.common

import android.content.Context
import android.util.Base64
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.xor

class SecurityUtility(val context: Context) {
    private fun encrypt(token: ByteArray): ByteArray {
        val key = generateKey(getKey().toByteArray())

        val secretKeySpec = SecretKeySpec(key, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)

        return cipher.doFinal(token)
    }

    private fun decrypt(encryptedValue: ByteArray): ByteArray {
        val key = generateKey(getKey().toByteArray())

        val secretKeySpec = SecretKeySpec(key, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)

        return cipher.doFinal(encryptedValue)
    }

    private fun generateKey(key: ByteArray): ByteArray {
        val md: MessageDigest = MessageDigest.getInstance("SHA-256")
        md.update(key)

        val mdBytes: ByteArray = md.digest()
        val key = ByteArray(mdBytes.size / 2)

        for (I in key.indices) {
            key[I] = mdBytes[I]
            key[I] = mdBytes[I] xor mdBytes[I + key.size]
        }

        return key
    }

    fun getUserToken() : String? {
        val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val value64Encrypted = preferences.getString(TOKEN_KEY, "")
        return if (!value64Encrypted.isNullOrEmpty()) {
            val encryptedValue = Base64.decode(value64Encrypted, Base64.NO_WRAP)
            String(decrypt(encryptedValue), Charsets.UTF_8)
        } else null
    }

    fun encryptUserToken(token: String) {
        val encryptedToken = encrypt(token.toByteArray())
        val tokenBase64String = Base64.encodeToString(encryptedToken, Base64.NO_WRAP)

        val editor = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
        editor.putString(TOKEN_KEY, tokenBase64String)
        editor.apply()
    }

    companion object {
        init {
            System.loadLibrary("native-lib")
        }

        private const val PREFERENCES_NAME = "IntergrupoTokenKey"
        private const val TOKEN_KEY = "tokIntergrupo"
    }

    private external fun getKey(): String
}