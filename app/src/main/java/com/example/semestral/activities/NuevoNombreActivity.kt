package com.example.semestral.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.example.semestral.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class NuevoNombreActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var buttonSave: Button
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_nombre)

        editTextName = findViewById(R.id.editTextName)
        buttonSave = findViewById(R.id.buttonSave)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        buttonSave.setOnClickListener {
            val newName = editTextName.text.toString().trim()
            if (newName.isNotEmpty()) {
                updateName(newName)
            }
        }
    }

    private fun updateName(newName: String) {
        val user = auth.currentUser
        user?.let {
            // Actualizar nombre en Firebase Auth
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build()

            it.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Actualizar nombre en Firebase Realtime Database
                        databaseReference.child("users").child(it.uid).child("name").setValue(newName)
                            .addOnCompleteListener { dbTask ->
                                if (dbTask.isSuccessful) {
                                    // Iniciar MainActivity con el nuevo nombre
                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.putExtra("newName", newName)
                                    intent.putExtra("navigateToProfile", true)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                } else {
                                    // Manejar error al actualizar en Firebase Database
                                    // Por ejemplo, mostrar un mensaje al usuario
                                }
                            }
                    } else {
                        // Manejar error al actualizar en Firebase Auth
                        // Por ejemplo, mostrar un mensaje al usuario
                    }
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("newName", newName)
                    intent.putExtra("navigateToProfile", true)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
        }
    }
}
