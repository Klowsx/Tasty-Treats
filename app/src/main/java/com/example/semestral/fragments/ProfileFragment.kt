package com.example.semestral.fragments




import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.semestral.R
import com.example.semestral.activities.CambiarContrasenaActivity
import com.example.semestral.activities.NuevoNombreActivity
import com.example.semestral.activities.SignIn
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private lateinit var imageButton: ImageButton
    private lateinit var button: Button
    private lateinit var nameTextView: TextView
    private lateinit var textViewCambiar: TextView
    private lateinit var buttonSave: ImageButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        mAuth = FirebaseAuth.getInstance()

        // Configuración de Google SignIn
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        // Inicialización de vistas
        buttonSave= view.findViewById(R.id.imageViewSaved)
        nameTextView = view.findViewById(R.id.name)
        imageButton = view.findViewById(R.id.imageButton)
        button = view.findViewById(R.id.button)
        textViewCambiar= view.findViewById(com.example.semestral.R.id.textViewCambiar)
        // Obtener el usuario actual
        val auth = Firebase.auth
        val user = auth.currentUser

        // Actualizar el nombre en el TextView si el usuario está autenticado
        val newName = arguments?.getString("newName")
        if (newName != null) {
            nameTextView.text = newName
        } else if (user != null) {
            val userName = user.displayName ?: getUserNameFromEmail(user.email)
            nameTextView.text = userName
        }

        // Configurar el botón de cerrar sesión
        val signOutButton = view.findViewById<Button>(R.id.logout_button)
        signOutButton.setOnClickListener {
            signOutAndStartSignInActivity()
        }

        // En el método onCreateView() o donde configures tu fragmento

        textViewCambiar.setOnClickListener {
            val intent = Intent(activity, CambiarContrasenaActivity::class.java)
            startActivity(intent)
        }


        // Configurar el botón de imageButton
        imageButton.setOnClickListener {
            val intent = Intent(activity, NuevoNombreActivity::class.java)
            startActivity(intent)
        }

        buttonSave.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RecetasGuardadasFragment())
                .addToBackStack(null)  // Agrega este fragmento al back stack
                .commit()

        }

        // Configurar el botón button
        button.setOnClickListener {
            imageButton.visibility = View.VISIBLE
        }

        // Ocultar el ImageButton al cargar el fragmento
        imageButton.visibility = View.GONE

        return view
    }

    override fun onResume() {
        super.onResume()

        // Actualizar el nombre en el TextView si el usuario está autenticado
        val auth = Firebase.auth
        val user = auth.currentUser

        if (user != null) {
            val userName = user.displayName ?: getUserNameFromEmail(user.email)
            nameTextView.text = userName
        }
    }

    private fun getUserNameFromEmail(email: String?): String {
        return email?.substringBefore('@') ?: "Unknown"
    }

    private fun signOutAndStartSignInActivity() {
        // Cerrar sesión en Firebase Auth
        mAuth.signOut()

        // Cerrar sesión en Google Sign-In
        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity()) {
            // Redirigir a la actividad de inicio de sesión
            val intent = Intent(requireContext(), SignIn::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

}
