package com.example.bagunca;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EsqueceuSenha extends AppCompatActivity {

    TextInputEditText user, email;
    Button btEnviar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_esqueceu_senha);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        user = findViewById(R.id.user);
        email = findViewById(R.id.email);
        btEnviar = findViewById(R.id.btEnviar);

        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userDigitado = user.getText().toString().trim();
                String emailDigitada = email.getText().toString().trim();

                if (userDigitado.isEmpty() || emailDigitada.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Email enviado para recuperação de senha!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EsqueceuSenha.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        }, 0500);
                }
            }
        });

    }

    private boolean verificarLogin(String email, String senha) {
        Connection conexao = ConexaoMysql.conectar();
        if (conexao == null) {
            return false;
        }

        String sql = "SELECT * FROM login_usuario WHERE email = ? AND senha = ?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();
            boolean loginValido = rs.next();

            rs.close();
            stmt.close();
            ConexaoMysql.fecharConexao(conexao);

            return loginValido;
        } catch (SQLException e) {
            System.out.println("Erro ao verificar login: " + e.getMessage());
            return false;
        }
    }
}