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
package com.pbl.Interfaces;

import java.util.List;
import java.util.UUID;

/**
 * A interface {@code Store} define operações básicas para o armazenamento e
 * manipulação de objetos do tipo {@code T}.
 *
 * Esta interface inclui métodos para adicionar, remover, obter objetos pelo
 * identificador e listar todos os objetos, além de métodos para serialização
 * e desserialização dos dados.
 *
 * @param <T> o tipo de objeto que será armazenado e manipulado pela store
 *
 * @author Guilherme Fernandes Sardinha
 */
public interface Store<T> {

    /**
     * Adiciona um objeto à store.
     *
     * @param obj o objeto a ser adicionado
     */
    void add(T obj);

    /**
     * Remove um objeto da store.
     *
     * @param obj o objeto a ser removido
     */
    void remove(T obj);

    /**
     * Retorna um objeto da store com base em seu identificador único.
     *
     * @param ID o identificador único do objeto
     * @return o objeto associado ao identificador fornecido, ou {@code null} se não encontrado
     */
    T getByID(UUID ID);

    /**
     * Retorna uma lista contendo todos os objetos armazenados na store.
     *
     * @return uma lista com todos os objetos da store
     */
    List<T> get();

    /**
     * Serializa os dados da store, salvando-os em um formato persistente.
     */
    void serializer();

    /**
     * Desserializa os dados da store, carregando-os de um formato persistente.
     */
    void desserializer();
}
