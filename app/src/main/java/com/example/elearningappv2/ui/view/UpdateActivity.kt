package com.example.elearningappv2.ui.view

import android.app.Dialog
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.elearningappv2.R
import com.example.elearningappv2.databinding.ActivityUpdateBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private var imageUri: Uri?= null
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere...")
        progressDialog.setCanceledOnTouchOutside(false)


        //indicamos vamos a utilizar los método de autencación en este activity
        auth =  FirebaseAuth.getInstance()  //Firebase.auth
        //initui()
        //getUserLogged()

        binding.imageEdit.setOnClickListener{
            showDialog()
        }

        binding.buttonSave.setOnClickListener{
            validateImage()
        }

    }

    private fun validateImage(){
        if(imageUri == null) {
            Toast.makeText(this, "Imagen no encontrada", Toast.LENGTH_SHORT).show()
        }else{
            updateImage()
        }
    }

    private fun updateImage() {
        progressDialog.setMessage("Actualizando imagen...")
        progressDialog.show()
        val pathImage = "User_profile/"+auth.uid    //Ruta del directorio donde se guardan las imagenes
        val referenceStorage = FirebaseStorage.getInstance().getReference(pathImage)
        referenceStorage.putFile(imageUri!!).addOnSuccessListener {
            val uriImageDownload: Task<Uri> = it.storage.downloadUrl    //Corroborar Task
            while (!uriImageDownload.isSuccessful);
            val urlImage = "${uriImageDownload.result}"
            updateImageFB(urlImage)
        }.addOnFailureListener{
            Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }
    }

    private fun updateImageFB(urlImage: String) {
        progressDialog.setMessage("Actualizando imagen de perfil")
        val hashMap: HashMap<String, Any> = HashMap()
        if(imageUri != null){
            hashMap["image"] = urlImage //Nombre del campo "imagen de perfil" del usuario en Firebase
        }

        val reference = FirebaseDatabase.getInstance().getReference("Users")  //Nombre de la base de datos
        reference.child(auth.uid!!).updateChildren(hashMap).addOnSuccessListener {
            progressDialog.dismiss()
            Toast.makeText(this, "Imagen actualizada", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryResultLauncher.launch(intent)
    }

    private val galleryResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult>{
            if(it.resultCode == RESULT_OK){
                val data = it.data
                imageUri = data!!.data
                binding.imageProfile.setImageURI(imageUri)
            }else{
                Toast.makeText(applicationContext, "Cancelado", Toast.LENGTH_SHORT).show()
            }
        }
    )

    private fun openCam(){
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Título")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripción")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        camResultLauncher.launch(intent)
    }

    private val camResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult>{
            if(it.resultCode == RESULT_OK){
                binding.imageProfile.setImageURI(imageUri)
            }else{
                Toast.makeText(applicationContext, "Cancelado", Toast.LENGTH_SHORT).show()
            }
        }
    )

    private fun showDialog(){

        val btn_gallery: Button
        val btn_cam: Button
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_edit_image)
        btn_gallery = dialog.findViewById(R.id.button_gallery)
        btn_cam = dialog.findViewById(R.id.button_cam)

        btn_gallery.setOnClickListener{
            openGallery()
            dialog.dismiss()
        }

        btn_cam.setOnClickListener{
            openCam()
            dialog.dismiss()
        }

        dialog.show()
    }

//    private fun getUserLogged(){
//        if (Helpers.isInternetAvailable(this)){
//
//            //val user = FirebaseAuth.getInstance().currentUser //auth.currentUser
//            if (user != null) {
//                // User is signed in
//
//                binding.etName.setText(user?.name)
//                binding.etEmailAddress.setText(user?.email)
//                binding.etMobile.setText(user?.phoneNumber)
//                Picasso.get().load(user?.image).into(binding.imageProfile)
//            } else {
//                // No user is signed in
//                //tvProfile.text = "No user";
//            }
//        }
//        else{
//            //tvProfile.text = "Anónimo";
//        }

//        private fun initUI(){
//            database = Room.databaseBuilder(
//                applicationContext, CourseDatabase::class.java
//            ).allowMainThreadQueries()
//                .build()
//
//        }
//    }
}