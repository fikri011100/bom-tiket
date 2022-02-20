package bncc.net.bom.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import bncc.net.bom.HomeActivity
import bncc.net.bom.R
import bncc.net.bom.databinding.ActivitySignUpBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignIn.setOnClickListener{
            startActivity(Intent(this, SignInActivity::class.java))
        }

        binding.btnSignUp.setOnClickListener {
            val name = binding.etFullname.text.toString()
            val email = binding.etEmail.text.toString()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            database = FirebaseDatabase.getInstance().getReference("User")
            // Database Firebase
            database = FirebaseDatabase.getInstance("https://bom-ticket-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")
            val Users = User(email, name, password, "", "", username)
            database.child(username).setValue(Users).addOnSuccessListener {
                binding.etFullname.text.clear()
                binding.etEmail.text.clear()
                binding.etUsername.text.clear()
                binding.etPassword.text.clear()

                Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}