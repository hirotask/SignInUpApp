package me.hirotask.loginformcompose.model

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * SharedPreferencesを扱うクラス
 */
class SpConf(private val context: Context) {
    suspend fun saveAccountSession(email: String, password: String) =
        withContext(Dispatchers.IO) {
            val prefData = context.getSharedPreferences("account", MODE_PRIVATE)
            val editor = prefData.edit();

            //パスワードの暗号化
            val keySpec = SecretKeySpec("abcdefg098765432".toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec)
            val encrypted = cipher.doFinal(password.toByteArray())
            val up = Base64.encodeToString(encrypted, Base64.DEFAULT)

            //入力されたメアドとパスワードを記録
            editor.putString("email", email)
            editor.putString("pass", up)

            //保存
            editor.commit()
        }

    fun deleteAccountSession() {
        val prefData = context.getSharedPreferences("account", MODE_PRIVATE)
        val editor = prefData.edit();

        editor.remove("email")
        editor.remove("pass")

        editor.apply()
    }

    fun getSavedAccountSession(): Pair<String, String> {
        val prefData = context.getSharedPreferences("account", MODE_PRIVATE)
        val email = prefData.getString("email", "")
        val rawPass = prefData.getString("pass", "")

        //パスワード複合化
        var pass = ""
        val keySpec = SecretKeySpec("abcdefg098765432".toByteArray(), "AES")
        try {
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.DECRYPT_MODE, keySpec)
            val decByte = Base64.decode(rawPass, Base64.DEFAULT)
            val decrypted = cipher.doFinal(decByte)
            pass = decrypted.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //空チェック
        if (email != null && email.isNotEmpty()) {
            if (pass.isNotEmpty()) {
                return Pair(email, pass)
            }
        }

        return Pair("","")
    }

    fun isSignIn() : Boolean {
        val accountSession = getSavedAccountSession()
        return accountSession.first.isNotEmpty() && accountSession.second.isNotEmpty()
    }
}