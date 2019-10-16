package id.trian.omkoro.view.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.android.synthetic.main.activity_verification.*
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import android.content.Intent
import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import androidx.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseTooManyRequestsException
import id.trian.omkoro.R
import id.trian.omkoro.view.ui.beranda.BerandaActivity


class VerificationActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var phoneNumber: String
    lateinit var mVerificationId: String
    val TAG: String = "VerificationActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        mAuth = FirebaseAuth.getInstance()
        phoneNumber = intent.getStringExtra("phonenumber")

        sendVerificationCode(phoneNumber)

        Verification_btnMasuk.setOnClickListener {
            verifyVerificationCode(login_edtOtp.text.toString().trim())
        }

    }


    fun sendVerificationCode(no: String){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+62" + no,
            60,
            TimeUnit.SECONDS,
            this,
            callbacks)
        Log.d("wew", "+62" + no)
    }


    var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d("VerificationActivity", "onVerificationCompleted:$credential")
            val code = credential.smsCode

            if (code != null) {
                login_edtOtp.setText(code)
                verifyVerificationCode(code)
            }

        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.w("VerificationActivity", "onVerificationFailed", e)
            if (e is FirebaseAuthInvalidCredentialsException) {
            } else if (e is FirebaseTooManyRequestsException) {
            }
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            Log.d(TAG, "onCodeSent:$verificationId")
            mVerificationId = verificationId
        }
    }

//    val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
//
//            //Getting the code sent by SMS
//            val code = phoneAuthCredential.smsCode
//
//            //sometime the code is not detected automatically
//            //in this case the code will be null
//            //so user has to manually enter the code
//            if (code != null) {
//                login_edtOtp.setText(code)
//                //verifying the code
//                Log.d("wew", "siniterah")
//                verifyVerificationCode(code)
//            }
//        }
//
//        override fun onVerificationFailed(e: FirebaseException) {
//            Toast.makeText(this@VerificationActivity, e.message, Toast.LENGTH_LONG).show()
//        }
//
//        override fun onCodeSent(
//            s: String,
//            forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
//            super.onCodeSent(s, forceResendingToken)
//            Log.d("wew", "sini")
//            //storing the verification id that is sent to the user
//            mVerificationId = s
//        }
//    }

    private fun verifyVerificationCode(code: String) {
        //creating the credential
        val credential = PhoneAuthProvider.getCredential(mVerificationId, code)

        //signing the user
        signInWithPhoneAuthCredential(credential)
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    startActivity(Intent(this, ProfileSettingActivity::class.java))

                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                }
            }
    }

}
