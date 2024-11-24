package com.pbl.Resources;

/**
 * A classe FilePaths contém constantes que representam os caminhos dos arquivos JSON
 * utilizados no sistema. Esses arquivos armazenam informações importantes, como usuários,
 * eventos, compras, ingressos e avaliações.
 *
 * <p>Os caminhos são usados para facilitar o acesso aos arquivos JSON durante o processo
 * de leitura e escrita de dados.</p>
 * @author Guilherme Fernandes Sardinha
 */
public class FilePaths {

    /**
     * Caminho para o arquivo JSON que contém os dados dos usuários.
     */
    public static final String USER_JSON = "src/main/java/com/pbl/Resources/json/user.json";

    /**
     * Caminho para o arquivo JSON que contém os dados dos cartões.
     */
    public static final String CARD_JSON = "src/main/java/com/pbl/Resources/json/card.json";

    /**
     * Caminho para o arquivo JSON que contém os dados dos eventos.
     */
    public static final String EVENT_JSON = "src/main/java/com/pbl/Resources/json/event.json";

    /**
     * Caminho para o arquivo JSON que contém os dados das compras.
     */
    public static final String PURCHASE_JSON = "src/main/java/com/pbl/Resources/json/purchase.json";

    /**
     * Caminho para o arquivo JSON que contém os dados dos ingressos.
     */
    public static final String TICKET_JSON = "src/main/java/com/pbl/Resources/json/ticket.json";

    /**
     * Caminho para o arquivo JSON que contém os dados das avaliações.
     */
    public static final String REVIEW_JSON = "src/main/java/com/pbl/Resources/json/review.json";
}