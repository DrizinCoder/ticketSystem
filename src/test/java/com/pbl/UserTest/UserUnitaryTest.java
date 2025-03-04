/*******************************************************************************
 * Autor: Guilherme Fernandes Sardinha
 * Componente Curricular: MI de Programação
 * Concluído em: 12/09/2024
 * Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
 * trecho de código de outro colega ou de outro autor, tais como provindos de livros e
 * apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
 * de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
 * do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
 ******************************************************************************************/
package com.pbl.UserTest;

import com.pbl.models.Usuario;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserUnitaryTest {

    @Test
    public void testCadastrarUsuario() {
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        assertNotNull(usuario);
        assertEquals("johndoe", usuario.getLogin());
        assertEquals("John Doe", usuario.getNome());
        assertEquals("12345678901", usuario.getCpf());
        assertEquals("john.doe@example.com", usuario.getEmail());
        assertFalse(usuario.isAdmin());

    }

    @Test
    public void testCadastrarUsuarioAdmin() {
        Usuario admin = new Usuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);

        assertNotNull(admin);
        assertEquals("admin", admin.getLogin());
        assertEquals("Admin User", admin.getNome());
        assertEquals("00000000000", admin.getCpf());
        assertEquals("admin@example.com", admin.getEmail());
        assertTrue(admin.isAdmin());
    }

    @Test
    public void testLogin() {
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        assertTrue(usuario.getLogin("johndoe", "senha123"));
        assertFalse(usuario.getLogin("johndoe", "senhaErrada"));
    }

    @Test
    public void testAtualizarSenha() {
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        usuario.setSenha("novaSenha123");
        assertTrue(usuario.getLogin("johndoe", "novaSenha123"));
        assertFalse(usuario.getLogin("johndoe", "senha123"));
    }

    @Test
    public void testDadosUsuario() {
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        usuario.setNome("Jonathan Doe");
        usuario.setCpf("10987654321");
        usuario.setEmail("jon.doe@example.com");

        assertEquals("Jonathan Doe", usuario.getNome());
        assertEquals("10987654321", usuario.getCpf());
        assertEquals("jon.doe@example.com", usuario.getEmail());
    }

    @Test
    public void testUsuarioDuplicado() {
        Usuario usuario1 = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        Usuario usuario2 = new Usuario("johndoe", "senha456", "John Doe", "12345678901", "john.doe@example.com", false);

        assertEquals(usuario1, usuario2);
    }
}
