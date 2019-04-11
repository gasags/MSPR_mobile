package com.test.gatien.mspr_mobile

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.provider.MediaStore
import android.widget.TextView
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1

    enum class buttonType{
        AUTH, CONTROL, NONE
    }

    var btnEnum = buttonType.NONE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val db = FirebaseFirestore.getInstance()
        db.collection("users").document("X8PZxh8S3ZiVHBruKDBC").get()
                .addOnSuccessListener { document ->
                    TvTest.text = document["Nom"].toString()
                }*/





        // Listener du bouton "s'authentifier" pour prendre une photo
        val btnAuth = findViewById<Button>(R.id.btnAuthentication)
        btnAuth.setOnClickListener {
            btnEnum = buttonType.AUTH
            pictureAppIntent()
        }

        // Listener du bouton "Effectuer un controle" pour prendre une photo
        val btnControl = findViewById<Button>(R.id.btnControl)
        btnControl.setOnClickListener {
            btnEnum = buttonType.CONTROL
            pictureAppIntent()
        }
    }

    // On récupère la photo prise pour la traiter
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val bmUser = data?.extras?.get("data") as Bitmap

            // TODO Comparaison de l'image avec la bdd

            val streamBmUser = ByteArrayOutputStream()
            bmUser.compress(Bitmap.CompressFormat.PNG, 100, streamBmUser)

            // Permet de vérifier la page de redirection
            if(btnEnum == buttonType.AUTH) {
                val intent = Intent(this, GestionMateriel::class.java)
                intent.putExtra("bmUser", streamBmUser.toByteArray())
                intent.putExtra("idUser", "X8PZxh8S3ZiVHBruKDBC")
                startActivity(intent)
                finish()
            }
            else if(btnEnum == buttonType.CONTROL){
                val intent = Intent(this, Controle::class.java)
                intent.putExtra("bmUser", streamBmUser.toByteArray())
                startActivity(intent)
                finish()
            }
        }
    }

    // On ouvre l'appli d'appareil photo
    private fun pictureAppIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            takePictureIntent -> takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }
}
