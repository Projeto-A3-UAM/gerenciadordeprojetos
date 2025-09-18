package br.com.gerenciadordeprojetos.service;

import br.com.gerenciadordeprojetos.domain.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class UsuarioService {
    private List<Usuario> usuarios = new ArrayList<>();

    private final String arquivo = "dados/usuarios.txt";

    public UsuarioService() {
        carregarUsuarios();
    }

    public void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        salvarUsuarios();
    }

    public List<Usuario> listarUsuarios() {
        return usuarios;
    }

    public Usuario buscarPorLogin(String login) {
        for (Usuario u : usuarios) {
            if (u.getLogin().equals(login)) {
                return u;
            }
        }
        return null;
    }

    public void removerUsuario(String login) {
        usuarios.removeIf(u -> u.getLogin().equals(login));
        salvarUsuarios();
    }

    public boolean editarUsuario(String login, Usuario novoUsuario) {
        Usuario usuario = buscarPorLogin(login);
        if (usuario != null) {
            usuario.setNomeCompleto(novoUsuario.getNomeCompleto());
            usuario.setCpf(novoUsuario.getCpf());
            usuario.setEmail(novoUsuario.getEmail());
            usuario.setCargo(novoUsuario.getCargo());
            usuario.setSenha(novoUsuario.getSenha());
            usuario.setPerfil(novoUsuario.getPerfil());
            salvarUsuarios();
            return true;
        }
        return false;
    }

    private void salvarUsuarios() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            for (Usuario u : usuarios) {
                bw.write(u.getNomeCompleto() + ";" + u.getCpf() + ";" + u.getEmail() + ";" + u.getCargo() + ";" + u.getLogin() + ";" + u.getSenha() + ";" + u.getPerfil());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    private void carregarUsuarios() {
        File file = new File(arquivo);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 7) {
                    Usuario u = new Usuario();
                    u.setNomeCompleto(dados[0]);
                    u.setCpf(dados[1]);
                    u.setEmail(dados[2]);
                    u.setCargo(dados[3]);
                    u.setLogin(dados[4]);
                    u.setSenha(dados[5]);
                    u.setPerfil(Usuario.Perfil.valueOf(dados[6]));
                    usuarios.add(u);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }
}
