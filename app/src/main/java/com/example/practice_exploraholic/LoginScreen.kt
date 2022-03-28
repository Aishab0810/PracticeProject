package com.example.practice_exploraholic

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
//import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.practice_exploraholic.R.id.reg
import com.example.practice_exploraholic.databinding.ActivityLoginScreenBinding
import com.example.practice_exploraholic.databinding.ActivityMainBinding
import com.example.practice_exploraholic.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class LoginScreen() : AppCompatActivity() {

    //viewBinding
    private lateinit var binding: ActivityLoginScreenBinding
    //viewActiobBar
    //private lateinit var actionBar: ActionBar
    //progressDialog
    private lateinit var progressDialog: ProgressDialog
    //firebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    private var email=""
    private var password=""

//    private var email: TextInputLayout? = null
//    private var password: TextInputLayout? = null
//   // lateinit var forgotpassword: TextView
//    lateinit var register: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        //configure actionbar
//        actionBar = supportActionBar!!
//        actionBar.title ="Login"

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging in...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser1()

        //handle click, Open registerScreen
        binding.reg.setOnClickListener{
            startActivity(Intent(this,RegisterScreen::class.java))
        }

        //handle click, begin login
        binding.loginbtn.setOnClickListener{
            //before logging in,validate data
            validateData()
            //startActivity(Intent(this,RegisterScreen::class.java))
        }

//        // Referencing email and password
//        email = findViewById(R.id.email)
//        password = findViewById(R.id.password)
//       // forgotpassword = findViewById(R.id.fpswd)
//        register = findViewById(R.id.reg)
//
//
//        register.setOnClickListener() {
//            intent = Intent(this, RegisterScreen::class.java)
//            startActivity(intent)
//        }

//        fun navigateFrag(fragment: Fragment, addToStack: Boolean) {
//            val transaction = supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.container,fragment)
//
//            if(addToStack)
//            {
//                transaction.addToBackStack(null)
//            }
//            transaction.commit()
//        }

//        val frgopen : TextView = findViewById(R.id.fpswd)
//        frgopen.setOnClickListener {
//            val forgotPswd = ForgotPswd()
//            val fragment : Fragment? =
//            supportFragmentManager.findFragmentByTag(ForgotPswd::class.java.simpleName)
//            if(fragment !is ForgotPswd) {
//              //  supportFragmentManager.beginTransaction()
//                getSupportFragmentManager().beginTransaction().
//                replace(R.id.relay,forgotPswd,ForgotPswd::class.java.simpleName)
//
//                    //.add(R.id.relay, forgotPswd, ForgotPswd::class.java.simpleName)
//                    .commit()
//
//                }
//        }

        //  adding the fragment
//        val forgotpswd = supportFragmentManager
//        val forgottrans =forgotpswd.beginTransaction()
//        val forfrg = ForgotPswd()
//                .add(R.id.fpswd, forgotpswd)
//                .commit()

    }

    private fun validateData() {
        //get data
        email = binding.email1.text.toString().trim()
        password = binding.password1.text.toString().trim()

        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.email1.error ="Invalid email format"
        }
        else if (TextUtils.isEmpty(password)){
            //no password entered
            binding.password1.error ="Please enter password"
        }
        else{
            //data is validate , begin login
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        //show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                //login successed
                progressDialog.dismiss()
                //get user info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"Logged in as $email",Toast.LENGTH_SHORT).show()
                //open Homepage
                startActivity(Intent(this,HomePage::class.java))
                finish()
            }
            .addOnFailureListener{ e->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(this,"Login failed due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser1() {
        //if user is already logged in then go to profile
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            //user is already logged in
            startActivity(Intent(this, ContactsContract.Profile::class.java))
            finish()
        }
    }

//    private fun validateEmail(): Boolean {
//
//        // Extract input from EditText
//        val emailInput = email!!.editText!!.text.toString().trim { it <= ' ' }
//
//        // if the email input field is empty
//        if (emailInput.isEmpty()) {
//            email!!.error = "Field can not be empty"
//            return false
//        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
//            email!!.error = "Please enter a valid email address"
//            return false
//        } else {
//            email!!.error = null
//            return true
//        }
//    }

//    private fun validatePassword(): Boolean {
//        val passwordInput = password!!.editText!!.text.toString().trim { it <= ' ' }
//        // if password field is empty
//        // it will display error message "Field can not be empty"
//        if (passwordInput.isEmpty()) {
//            password!!.error = "Field can not be empty"
//            return false
//        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
//            password!!.error = "Password is too weak"
//            return false
//        } else {
//            password!!.error = null
//            return true
//        }
//    }

//    fun confirmInput(v: View?) {
//        if (!validateEmail() or !validatePassword()) {
//            return
//        }
//
//        // if the email and password matches, direct to homepage
//        // with email and password is displayed
//        var input: String = "Email: " + email!!.editText!!.text.toString()
//        input += "\n"
//        input += "Password: " + password!!.editText!!.text.toString()
//        //Toast.makeText(this, input, Toast.LENGTH_SHORT).show()
//        val intent= Intent(this,HomePage::class.java)
//        startActivity(intent)
//    }

    companion object {
        // defining our own password pattern
        private val PASSWORD_PATTERN = Pattern.compile(
            "^" +
                    "(?=.*[@#$%^&+=])" +  // at least 1 special character
                    "(?=\\S+$)" +  // no white spaces
                    ".{4,}" +  // at least 4 characters
                    "$"
        )
    }

//    fun cnfpwd() {
//        forgotpassword?.setOnClickListener() {
//            intent = Intent(this, Forgot::class.java)
//            startActivity(intent)
//        }
//    }

//    fun registration() {
//        register?.setOnClickListener() {
//            intent = Intent(this, RegisterScreen::class.java)
//            startActivity(intent)
//        }
//    }

}