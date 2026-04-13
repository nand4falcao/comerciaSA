package com.muralis.agenda.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "clientes")
public class Cliente {
//declaração de id como auto incrementar
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
//colunas das tabelas ou atributos dos clientes
    @Column(nullable=false, length = 150)
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Column(nullable = false, unique = true, length = 14)
    @NotBlank(message = "CPF é obrigatório")
    private String cpf;

    @Column(name = "dataNasc")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNasc;

    @Column(length = 255)
    @NotBlank(message = "Endereço é obrigatório")
    private String endereco;

    @Column(length = 120)
    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;

    @Column(length = 60)
    @NotBlank(message = "Estado é obrigatório")
    private String estado;

    @Column(length = 15)
    @NotBlank(message = "CEP é obrigatório")
    private String cep;

//relacionamento, um pra varios
// O 'CascadeType.ALL' faz com que, se você apagar o cliente, os contatos sumam junto

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Contato> contatos = new ArrayList<>();

    //getters e setters ou oq vai fazer o o java acessar as variaveis privadas

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}


    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getCpf() {return cpf;}
    public void setCpf(String cpf) {this.cpf = cpf;}

    public LocalDate getDataNasc() {return dataNasc;}
    public void setDataNasc(LocalDate dataNasc) {this.dataNasc = dataNasc;}

    public String getEndereco() {return endereco;}
    public void setEndereco(String endereco) {this.endereco = endereco;}

    public String getCidade() {return cidade;}
    public void setCidade(String cidade) {this.cidade = cidade;}

    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}

    public String getCep() {return cep;}
    public void setCep(String cep) {this.cep = cep;}

    //public (torna o comando public para o programa) + tipo de dado + get (pegar o) variavel() + {retorna a variavel}
    //public + void (não retona nada, só faz uma ação) + setValor (ele pega o valor do return) + (tipo de dado + nome da variavel) + {this.NomeVariavel(cria uma nova variavel pra colocar o valor do return) + nomeVariavel;}

    public List <Contato> getContatos() {return contatos;}
    public void setContatos(List<Contato> contatos) {this.contatos = contatos;}
}




