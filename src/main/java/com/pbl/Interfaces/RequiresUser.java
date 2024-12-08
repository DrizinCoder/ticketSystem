package com.pbl.Interfaces;

import com.pbl.models.Usuario;

/**
 * Interface responsável por exigir que a classe que a implementa tenha um método para definir o usuário.
 * Esta interface é útil para classes que precisam de informações do usuário para realizar ações específicas.
 */
public interface RequiresUser {

    /**
     * Método utilizado para associar um usuário à classe que implementa esta interface.
     *
     * @param user O usuário que será associado à classe.
     */
    void setUser(Usuario user);
}
