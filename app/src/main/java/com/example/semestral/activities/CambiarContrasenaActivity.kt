package com.example.semestral.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.semestral.R
import com.example.semestral.fragments.ProfileFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class CambiarContrasenaActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var changePasswordButton: Button
    private lateinit var editTextOldPassword: EditText
    private lateinit var editTextNewPassword: EditText
    private lateinit var editTextConfirmPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cambiar_contrasena)

        auth = FirebaseAuth.getInstance()
        changePasswordButton = findViewById(R.id.ChangePasswordButton)
        editTextOldPassword = findViewById(R.id.editTextoldPassword)
        editTextNewPassword = findViewById(R.id.editTextPassword)
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword)

        // Handle change password button click
        changePasswordButton.setOnClickListener {
            val oldPassword = editTextOldPassword.text.toString()
            val newPassword = editTextNewPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()

            if (oldPassword.isNotEmpty() && newPassword.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (newPassword == confirmPassword) {
                    // Call method to change password
                    changePassword(oldPassword, newPassword)
                } else {
                    // Show error dialog for password mismatch
                    showErrorDialog("Las nuevas contraseñas no coinciden.")
                }
            } else {
                // Show error dialog for empty fields
                showErrorDialog("Por favor, completa todos los campos.")
            }
        }
    }

    private fun changePassword(oldPassword: String, newPassword: String) {
        val user = auth.currentUser

        if (user != null) {
            // Reauthenticate user to confirm old password
            val credential = auth.currentUser?.email?.let {
                EmailAuthProvider.getCredential(
                    it,
                    oldPassword
                )
            }
            user.reauthenticate(credential!!)
                .addOnCompleteListener { reAuthTask ->
                    if (reAuthTask.isSuccessful) {
                        // Password re-authenticated, now update password
                        user.updatePassword(newPassword)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Password change successful
                                    showSuccessDialog()
                                } else {
                                    // Password change failed, display a message to the user
                                    showErrorDialog("Error al cambiar la contraseña. Inténtalo de nuevo más tarde.")
                                }
                            }
                    } else {
                        // Re-authentication failed, show error dialog
                        showErrorDialog("Error de autenticación. La contraseña antigua no es correcta.")
                    }
                }
        }
    }

    private fun showSuccessDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Contraseña cambiada")
            .setMessage("Tu contraseña ha sido cambiada exitosamente. Serás deslogueado.")
            .setPositiveButton("Aceptar") { _, _ ->
                // Handle positive button click
                signOut()
            }
            .setCancelable(false)
            .show()
    }

    private fun signOut() {
        auth.signOut()
        val intent = Intent(this, MainActivity::class.java)

        intent.putExtra("navigateToProfile", true)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun showErrorDialog(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("Aceptar", null)
            .show()
    }
}
