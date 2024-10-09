/*******************************************************************************
 Autor: Guilherme Fernandes Sardinha
 Componente Curricular: MI de Programação
 Concluido em: 12/09/2024
 Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
 trecho de código de outro colega ou de outro autor, tais como provindos de livros e
 apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
 de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
 do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
 ******************************************************************************************/
package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

// Classe Evento
public class Evento {
    protected String name;
    protected String description;
    protected Date date;
    protected boolean status;
    protected List<String> seats;
    protected UUID ID;
    protected double rate;

    // Construtor padrão de Evento
    public Evento(String name, String description, Date date) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.status = setStatus();
        this.seats = new ArrayList<>();
        this.ID = UUID.randomUUID();
        this.rate = 0;
    }

    // Metodos Getters
    public String getName() { return name; }

    public String getDescription() { return description; }

    public UUID getID() { return ID; }

    public double getRate() { return rate; }

    public List<String> getSeats() { return seats; }

    public Date getDate() { return date; }

    // Metodos Setters
    public boolean isAtivo() { return status; }

    public boolean setStatus() {
        Date dateAux = new Date();
        this.status = dateAux.before(date);
        return this.status;
    }

    public void setfalse(){ this.status = false; }

    public void setRate(double rate){ this.rate = rate; }

    // Metodos referentes a lista de assentos

    public void addSeats(String seats) { this.seats.add(seats); }

    public void removeSeats(String seats) { this.seats.remove(seats); }

}
