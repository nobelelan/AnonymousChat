package com.example.anonymuschat

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.anonymuschat.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var databaseReference: DatabaseReference

    private var selectedImage: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = Firebase.auth
        firebaseStorage = Firebase.storage
        databaseReference = Firebase.database.reference

        databaseReference.child("users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)
                    binding.editTextUserName.setText(currentUser?.fullName)
                    binding.editTextEmail.setText(currentUser?.email)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        binding.imageUser.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent,45)
        }

        binding.buttonUpdateProfile.setOnClickListener {
            val storageRef = firebaseStorage.reference.child("userProfile")
                .child(firebaseAuth.currentUser!!.uid)
            storageRef.putFile(selectedImage!!).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    storageRef.downloadUrl.addOnCompleteListener { uri ->
                        val currentUser = firebaseAuth.currentUser
                        val imageUrl = uri.toString()
                        val uid = currentUser?.uid
                        val fullName = binding.editTextUserName.text.toString()
                        val email = binding.editTextEmail.text.toString().trim()
                        val user = User(uid, fullName, email, imageUrl)

                        // TODO: photo not showing, same user profile show in others profile, etc need a deep dive 

                        databaseReference.child("users").child(uid!!).setValue(user)
                            .addOnCompleteListener {
                                Toast.makeText(this, "Updated Successfully.", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null){
            if (data.data != null){
                val uri = data.data
                val storage = Firebase.storage
                val time = Date().time
                val reference = storage.reference.child("userProfile")
                    .child(time.toString()+"")
                reference.putFile(uri!!).addOnCompleteListener { task ->
                    val filePath = uri.toString()
                    val obj = HashMap<String, Any>()
                    obj["image"] = filePath
                    databaseReference.child("users")
                        .child(firebaseAuth.currentUser!!.uid)
                        .updateChildren(obj).addOnSuccessListener {  }
                }
                binding.imageUser.setImageURI(data.data)
                selectedImage = data.data
            }
        }
    }

}