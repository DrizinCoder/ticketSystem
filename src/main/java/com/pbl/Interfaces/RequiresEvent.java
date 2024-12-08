package com.pbl.Interfaces;

import com.pbl.models.Evento;

/**
 * Interface responsável por exigir que a classe que a implementa tenha um método para definir um evento.
 * Esta interface é útil para classes que precisam manipular ou exibir informações relacionadas a um evento específico.
 */
public interface RequiresEvent {

    /**
     * Método utilizado para associar um evento à classe que implementa esta interface.
     *
     * @param event O evento a ser associado à classe.
     */
    void setEvent(Evento event);
}
