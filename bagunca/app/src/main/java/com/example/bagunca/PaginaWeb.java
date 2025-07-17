 package com.example.bagunca;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PaginaWeb extends AppCompatActivity {
    WebView baguncaWeb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagina_web);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Validação do ID
        baguncaWeb = findViewById(R.id.baguncaWeb);
        //Configuração para visualização do site dentro do APP
        baguncaWeb.getSettings().setJavaScriptEnabled(true);
        baguncaWeb.setWebViewClient(new WebViewClient());
        baguncaWeb.loadUrl("https://ruandd9.github.io/webagunca/public/board-page.html");

    }
}
