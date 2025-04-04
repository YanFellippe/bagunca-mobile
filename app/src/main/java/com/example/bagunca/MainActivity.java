package com.example.bagunca;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    EditText usuario, senha;
    TextView esqueceuSenha;
    Button btLogin, btCadastro;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usuario = findViewById(R.id.usuario);
        senha = findViewById(R.id.senha);
        btLogin = findViewById(R.id.btLogin);
        btCadastro = findViewById(R.id.btCadastro);
        esqueceuSenha = findViewById(R.id.esqueceuSenha);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailDigitado = usuario.getText().toString().trim();
                String senhaDigitada = senha.getText().toString().trim();

                if (emailDigitado.isEmpty() || senhaDigitada.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (verificarLogin(emailDigitado, senhaDigitada)) {
                    //Toast.makeText(getApplicationContext(), "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    //startActivity(intent);
                    //finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Usuário ou senha incorretos!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent cadastroPage = new Intent(MainActivity.this, CadastroPage.class);
                        startActivity(cadastroPage);
                        finish();
                    }
                }, 0500);
            }
        });

        esqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EsqueceuSenha.class);
                startActivity(intent);
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
