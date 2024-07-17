package com.example.semestral.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.semestral.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class NuevoNombre: Fragment() {

    private lateinit var editTextName: EditText
    private lateinit var buttonSave: Button
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_nuevo_nombre, container, false)

        editTextName = view.findViewById(R.id.editTextName)
        buttonSave = view.findViewById(R.id.buttonSave)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        buttonSave.setOnClickListener {
            buttonSave.setOnClickListener {
                val newName = editTextName.text.toString().trim()
                if (newName.isNotEmpty()) {
                    updateName(newName)

                }
                navigateToProfile()

            }

        }

        return view
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
                                    // No es necesario notificar al ProfileFragment aquí
                                    // porque ya se hace al navegar de regreso
                                } else {
                                    // Manejar error al actualizar en Firebase Database
                                    // Por ejemplo, mostrar un mensaje al usuario
                                }
                            }
                    } else {
                        // Manejar error al actualizar en Firebase Auth
                        // Por ejemplo, mostrar un mensaje al usuario
                    }
                }
        }
    }
    private fun navigateToProfile() {
        val nuevoNombreFragment = ProfileFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, nuevoNombreFragment)
            .addToBackStack(null)
            .commit()
    }


}
