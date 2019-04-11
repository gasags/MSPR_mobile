package com.test.gatien.mspr_mobile

import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.support.v4.content.ContextCompat
import android.support.v4.widget.CompoundButtonCompat
import android.util.Log
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_gestion_materiel.*

class GestionMateriel : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion_materiel)

        //On récupère l'id de l'utilisateur
        val idUser = intent.getStringExtra("idUser") as String

        getMaterial(idUser)

        //listener du bouton "Accueil" pour rediriger sur la page principale
        val btnHome = findViewById<Button>(R.id.btnHome)
        btnHome.setOnClickListener {
            redirectHome()
        }

        // On récupère l'image envoyée en ByteArray pour l'afficher
        val byteArrayBmUser =  intent.getByteArrayExtra("bmUser")
        val bmUser = BitmapFactory.decodeByteArray(byteArrayBmUser, 0, byteArrayBmUser.size)
        ivUser.setImageBitmap(bmUser)

    }

    // Redirection vers la page principale "MaintActivity"
    private fun redirectHome(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun getMaterial(idUser : String){

        val linearLayoutForCheckBoxs = findViewById<LinearLayout>(R.id.llCheckBox)

        db.collection("materials")
                .addSnapshotListener(EventListener<QuerySnapshot>{ value, e ->
                    if(e != null){
                        Log.w("error", "Listener error" ,  e)
                    }

                    // On boucle sur tous les documents de la collection "materials" en vérifiant qu'ils ne soient pas null puis se lance à chaque modif en bdd
                    for ((index, doc) in value!!.documentChanges.withIndex()) {
                        when(doc.type){
                            DocumentChange.Type.ADDED -> {
                                // init du checkBox
                                val cb = CheckBox(this)
                                val usersMaterial = doc.document["Users"] as ArrayList<String>

                                cb.id = index
                                cb.text = doc.document["Nom"] as String
                                cb.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                                CompoundButtonCompat.setButtonTintList(cb, ContextCompat.getColorStateList(this, R.color.colorTitleAndButton))
                                cb.setTextColor(ContextCompat.getColor(this, R.color.colorTxt))

                                // On vérifie si l'utilisateur a déjà le materiel et si la quantité est > à 0
                                if(usersMaterial.contains(idUser)){
                                    cb.isChecked = true
                                }
                                else if((doc.document["Quantite"] as Number).toInt() > 0){
                                    cb.isActivated = false
                                }

                                // On ajoute le chechBox au layout
                                linearLayoutForCheckBoxs.addView(cb)
                            }
                            DocumentChange.Type.MODIFIED -> {
                                // TODO bloquer la checkBox si le matériel a une quantite de 0 en base et qu'il n'est pas coché par l'utilisateur
                            }
                        }
                    }
                })
    }
}
