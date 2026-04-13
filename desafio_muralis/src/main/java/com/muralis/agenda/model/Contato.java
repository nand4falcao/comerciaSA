package com.muralis.agenda.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

    @Entity
    @Table (name = "contatos")

public class Contato{
    //declaração de id como auto incrementar
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    //colunas das tabelas ou atributos
    @Column (nullable = false, length = 20)
    @NotBlank(message = "Tipo de contato é obrigatório")
    private String tipoContato; //Telefone ou email

    @Column (nullable = false, length = 100)
    @NotBlank(message = "Valor do contato é obrigatório")
    private String valorContato; //ex: xx xxxxx-xxxx ou fulanoDeTal@gmail.com

    @Column (nullable = true, length = 255)
    private String observacoes;

    //relacionamento
    //join juntou as duas tabelas
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false) // Nome da coluna que o SQL vai criar
    private Cliente cliente;

    public int getId() {return id;}
    public void setId (int id) {this.id = id;}

    public String getTipoContato() {return tipoContato;}
    public void setTipoContato (String tipoContato) {this.tipoContato = tipoContato;}

    public String getValorContato() {return valorContato;}
    public void setValorContato (String valorContato) {this.valorContato = valorContato;}

    public String getObservacoes() {return observacoes;}
    public void setObservacoes (String observacoes) {this.observacoes = observacoes;}

    @JsonIgnore
    public Cliente getCliente() {return cliente;}
    public void setCliente (Cliente cliente) {this.cliente = cliente;}
}
