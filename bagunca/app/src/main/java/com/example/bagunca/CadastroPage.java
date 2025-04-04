package com.example.bagunca;

import static android.widget.Toast.LENGTH_LONG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class CadastroPage extends AppCompatActivity {
    TextView txtLogin;
    TextInputEditText email, senha, usuario;
    Button btCadastro;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = findViewById(R.id.email);
        senha = findViewById(R.id.senha);
        usuario = findViewById(R.id.usuario);
        btCadastro = findViewById(R.id.btCadastro);
        txtLogin = findViewById(R.id.txtLogin);

        btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailDigitado = email.getText().toString().trim();
                String senhaDigitada = senha.getText().toString().trim();
                String usuarioDigitado = usuario.getText().toString().trim();

                try {
                    Connection con = ConexaoMysql.conectar();
                    String sql = "INSERT INTO login_usuario(usuario, email, senha) VALUES(?, ?, ?);";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.setString(1, usuario.getText().toString());
                    stmt.setString(2, email.getText().toString());
                    stmt.setString(3, senha.getText().toString());
                    stmt.execute();
                    Toast.makeText(getApplicationContext(), "Cadastro efetuado com sucesso!", LENGTH_LONG).show();

                    con.close();
                    stmt.close();

                    if (emailDigitado.isEmpty() || senhaDigitada.isEmpty() || usuarioDigitado.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Email enviado para recuperação de senha!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CadastroPage.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 0500);
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