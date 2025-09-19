package br.com.gerenciadordeprojetos.service;

import br.com.gerenciadordeprojetos.domain.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsuarioService {
    private final List<Usuario> usuarios = Collections.synchronizedList(new ArrayList<>());
    private final String arquivo = "dados/usuarios.txt";

    public UsuarioService() {
        carregarUsuarios();
    }

    public void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        salvarUsuarios();
    }

    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios); // cópia defensiva
    }

    public Usuario buscarPorLogin(String login) {
        if (login == null) return null;
        for (Usuario u : usuarios) {
            if (u.getLogin() != null && login.equalsIgnoreCase(u.getLogin())) {
                return u;
            }
        }
        return null;
    }

    public void removerUsuario(String login) {
        if (login == null) return;
        usuarios.removeIf(u -> u.getLogin() != null && login.equalsIgnoreCase(u.getLogin()));
        salvarUsuarios();
    }

    public boolean editarUsuario(String login, Usuario novoUsuario) {
        Usuario usuario = buscarPorLogin(login);
        if (usuario != null) {
            usuario.setNomeCompleto(novoUsuario.getNomeCompleto());
            usuario.setCpf(novoUsuario.getCpf());
            usuario.setEmail(novoUsuario.getEmail());
            usuario.setCargo(novoUsuario.getCargo());
            usuario.setSenha(novoUsuario.getSenha()); // ⚠️ considerar hash
            usuario.setPerfil(novoUsuario.getPerfil());
            salvarUsuarios();
            return true;
        }
        return false;
    }

    private void salvarUsuarios() {
        System.out.println("[DEBUG] Iniciando salvamento de usuários...");
        File dir = new File("dados");
        if (!dir.exists()) {
            boolean criado = dir.mkdirs();
            System.out.println("[DEBUG] Diretório 'dados' criado: " + criado);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            for (Usuario u : usuarios) {
                bw.write(
                    (u.getNomeCompleto() != null ? u.getNomeCompleto() : "") + ";" +
                    (u.getCpf() != null ? u.getCpf() : "") + ";" +
                    (u.getEmail() != null ? u.getEmail() : "") + ";" +
                    (u.getCargo() != null ? u.getCargo() : "") + ";" +
                    (u.getLogin() != null ? u.getLogin() : "") + ";" +
                    (u.getSenha() != null ? u.getSenha() : "") + ";" +
                    (u.getPerfil() != null ? u.getPerfil().name() : "")
                );
                bw.newLine();
            }
            System.out.println("[DEBUG] Usuários salvos com sucesso em '" + arquivo + "'.");
        } catch (IOException e) {
            System.err.println("[ERRO] Falha ao salvar usuários: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void carregarUsuarios() {
        usuarios.clear();
        File file = new File(arquivo);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";", -1); // mantém campos vazios
                if (dados.length == 7) {
                    Usuario u = new Usuario();
                    u.setNomeCompleto(dados[0]);
                    u.setCpf(dados[1]);
                    u.setEmail(dados[2]);
                    u.setCargo(dados[3]);
                    u.setLogin(dados[4]);
                    u.setSenha(dados[5]);

                    try {
                        if (!dados[6].isEmpty()) {
                            u.setPerfil(Usuario.Perfil.valueOf(dados[6]));
                        }
                    } catch (IllegalArgumentException ex) {
                        System.err.println("[ERRO] Perfil inválido no arquivo: " + dados[6]);
                    }

                    usuarios.add(u);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }
}
