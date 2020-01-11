package pokemon.planner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth

class RegisterAccountActivity : AppCompatActivity() {
    private lateinit var emailInput: EditText
    private lateinit var password1Input: EditText
    private lateinit var password2Input: EditText
    private lateinit var registerButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_account)
        auth = FirebaseAuth.getInstance()

        registerButton = findViewById(R.id.registerButton)
        registerButton.isEnabled = false

        emailInput = findViewById(R.id.emailText)
        password1Input = findViewById(R.id.passwordText)
        password2Input = findViewById(R.id.passwordRepeatText)

        emailInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                checkForCompleteData()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        password1Input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                checkForCompleteData()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        password2Input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                checkForCompleteData()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        registerButton.setOnClickListener {
            auth.createUserWithEmailAndPassword(emailInput.text.toString(), password1Input.text.toString())
                .addOnCompleteListener(this){task ->
                    if(task.isSuccessful){
                        //registration successful
                        setResult(1)
                        finish()
                    }else{
                        //registration failed
                    }
                }
        }
    }

    private fun checkForCompleteData(){
        registerButton.isEnabled = (emailInput.text.toString() != "" && password1Input.text.toString() != "" && password1Input.text.toString() == password2Input.text.toString())
    }
}
