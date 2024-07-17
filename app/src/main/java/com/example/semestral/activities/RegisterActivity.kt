package com.example.semestral.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.semestral.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class RegisterActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var editTextemail: EditText
    private lateinit var editTextpassword: EditText
    private lateinit var editTextConfPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
        FirebaseAuth.getInstance()

        val editText1 = this.findViewById<EditText>(R.id.editTextTextEmailAddress)
        editText1.setHintTextColor(resources.getColor(R.color.grisClaro)) // Cambia el color del hint si es necesario

        val editText2 = this.findViewById<EditText>(R.id.editTextTextPassword)
        editText2.setHintTextColor(resources.getColor(R.color.grisClaro))

        val editText3 = this.findViewById<EditText>(R.id.editTextConfirmPassword)
        editText3.setHintTextColor(resources.getColor(R.color.grisClaro))

        button = findViewById(R.id.signInButton)
        editTextemail = findViewById(R.id.editTextTextEmailAddress)
        editTextpassword = findViewById(R.id.editTextTextPassword)
        editTextConfPassword = findViewById(R.id.editTextConfirmPassword)

        //SETUP
        setup()
    }

    private fun setup() {
        title = "Autenticación"
        button.setOnClickListener {
            if (editTextemail.text.isNotEmpty() && editTextpassword.text.isNotEmpty() && editTextConfPassword.text.isNotEmpty()) {
                if (editTextConfPassword.text.toString() == editTextpassword.text.toString()) {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(editTextemail.text.toString(), editTextpassword.text.toString()).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showSignIn(it.result?.user?.email ?: "", ProviderType.BASIC)
                        } else {
                            // Captura errores específicos de Firebase
                            val exception = it.exception
                            if (exception is FirebaseAuthException) {
                                showAlert("Error: ${exception.errorCode}")
                            } else {
                                showAlert()
                            }
                        }
                    }
                } else {
                    showAlert2()
                }
            } else {
                showAlert3()
            }
        }
    }

    private fun showAlert(message: String = "Se ha producido un error autenticando al usuario") {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ERROR")
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showAlert2() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ERROR")
        builder.setMessage("El campo contraseña y confirmar contraseña no son iguales")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showAlert3() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ERROR")
        builder.setMessage("Debe ingresar todos sus datos")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showSignIn(email: String, provider: ProviderType) {
        val homeIntent: Intent = Intent(this, SignIn::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
        finish()
    }
}

