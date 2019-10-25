package id.trian.omkoro.view.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import id.trian.omkoro.R
import id.trian.omkoro.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import id.trian.omkoro.view.ui.beranda.BerandaActivity
import id.trian.omkoro.view.ui.beranda.HomeActivity


class LoginActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 7
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth


    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        updateBeranda(currentUser)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)


        sign_in_button.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("Login", "Google sign in failed", e)
                // ...
            }

        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("Login", "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Login", "signInWithCredential:success")
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Login", "signInWithCredential:failure", task.exception)
                    Toast.makeText(this,"Auth Failed",Toast.LENGTH_LONG).show()
                    updateUI(null)
                }

                // ...
            }
    }

    fun updateBeranda(user: FirebaseUser?){
        if (user != null){
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    fun updateUI(user: FirebaseUser?){
        if(user != null){
            //Check if user exist in Firestore just go to Beranda
            startActivity(Intent(this, ProfileSettingActivity::class.java))
        } else {
            Log.d("wew", "null")
        }
    }

//    fun savedata(){
//        val user = User()
//        user.nama = editText.text.toString()
//        user.nip = editText2.text.toString()
//        user.uuid = editText3.text.toString()
//
//        yourViewModel.saveAddressToFirebase(user)
//    }
//
//    fun getdata(){
//        yourViewModel.getSavedAddresses().observe(this, Observer { it->
//            var mylist: List<User> = it
//            textView.text = mylist[0].nama
//            textView2.text = mylist[0].nip
//            textView3.text = mylist[0].uuid
//        })
//    }
}
