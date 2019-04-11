package com.test.gatien.mspr_mobile

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Controle : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controle)

        // TODO Afficher la photo de l'utilisateur dans l'imageView

        //listener du bouton "Accueil" pour rediriger sur la page principale
        val btnHome = findViewById<Button>(R.id.btnHome)
        btnHome.setOnClickListener {
            redirectHome()
        }
    }

    // Redirection vers la page principale "MaintActivity"
    private fun redirectHome(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // TODO Affichage camera pour prendre la CI en photo
    // TODO Traitemet de la photo de la CI
}
