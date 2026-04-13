package com.muralis.agenda.controller;

import com.muralis.agenda.model.Cliente;
import com.muralis.agenda.model.Contato;
import com.muralis.agenda.repository.ClienteRepository;
import com.muralis.agenda.repository.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller; 
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

@Controller
public class ClienteController {

    public record ContatoAtualizacao(String tipoContato, String valorContato, String observacoes) {
    }
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContatoRepository contatoRepository;

    @GetMapping("/")
    public String paginaInicial() {
        return "clientes";
    }

    @GetMapping("/cadastroClientes")
    public String cadastroClientes(Model model) {
        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", new Cliente());
        }
        return "cadastroClientes";
    }

    @GetMapping("/consultaClientes")
    public String consultaClientes(@RequestParam(required = false) String busca, Model model) {
        String termo = busca == null ? "" : busca.trim();
        List<Cliente> clientes;
        boolean buscou = !termo.isEmpty();

        if (!buscou) {
            clientes = clienteRepository.findAll();
        } else {
            Map<Integer, Cliente> porId = new LinkedHashMap<>();
            for (Cliente c : clienteRepository.findByNomeContainingIgnoreCase(termo)) {
                porId.put(c.getId(), c);
            }
            for (Cliente c : clienteRepository.findByCpf(termo)) {
                porId.put(c.getId(), c);
            }
            String digitos = termo.replaceAll("\\D", "");
            if (!digitos.isEmpty()) {
                for (Cliente c : clienteRepository.findByCpfSomenteDigitos(digitos)) {
                    porId.put(c.getId(), c);
                }
            }
            clientes = new ArrayList<>(porId.values());
        }

        model.addAttribute("clientes", clientes);
        model.addAttribute("busca", busca != null ? busca : "");
        model.addAttribute("buscou", buscou);
        return "consultaClientes";
    }

    @GetMapping("/api/clientes")
    @ResponseBody
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @GetMapping("/api/clientes/{id}/contatos")
    @ResponseBody
    public ResponseEntity<List<Contato>> contatosDoCliente(@PathVariable int id) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contatoRepository.findByClienteIdOrderByIdAsc(id));
    }

    @DeleteMapping("/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity<Void> excluirCliente(@PathVariable int id) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/api/clientes/{clienteId}/contatos/{contatoId}")
    @ResponseBody
    public ResponseEntity<Contato> atualizarContato(
            @PathVariable int clienteId,
            @PathVariable int contatoId,
            @RequestBody ContatoAtualizacao body) {
        if (body.tipoContato() == null || body.tipoContato().isBlank()
                || body.valorContato() == null || body.valorContato().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return contatoRepository.findByIdAndClienteId(contatoId, clienteId)
                .map(c -> {
                    c.setTipoContato(body.tipoContato().trim());
                    c.setValorContato(body.valorContato().trim());
                    String obs = body.observacoes();
                    if (obs != null) {
                        obs = obs.trim();
                        c.setObservacoes(obs.isEmpty() ? null : obs);
                    }
                    return contatoRepository.save(c);
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/clientes/salvar")
    public String salvar(
            @Valid Cliente cliente,
            BindingResult bindingResult,
            @RequestParam String tipoContato,
            @RequestParam String valorContato,
            @RequestParam(required = false) String observacoes,
            @RequestParam(required = false) String dataNasc,
            Model model) {

        if(dataNasc != null && !dataNasc.isEmpty()){
        try {    
            cliente.setDataNasc(LocalDate.parse(dataNasc));
        } catch (Exception e) {
            model.addAttribute("erroMensagem", "Data de nascimento inválida.");
            model.addAttribute("cliente", cliente);
            return "cadastroClientes";
        }
     } else {
           model.addAttribute("erroMensagem", "Data de nascimento inválida.");
            model.addAttribute("cliente", cliente);
            return "cadastroClientes"; 
        }

        sanitizeCliente(cliente);
        tipoContato = trim(tipoContato);
        valorContato = trim(valorContato);
        observacoes = trim(observacoes);

        if (bindingResult.hasErrors()) {
            model.addAttribute("erroMensagem", "Verifique os dados do cliente e tente novamente.");
            model.addAttribute("cliente", cliente);
            return "cadastroClientes";
        }

        if (tipoContato == null || tipoContato.isBlank() || valorContato == null || valorContato.isBlank()) {
            model.addAttribute("erroMensagem", "Tipo e valor do contato são obrigatórios.");
            model.addAttribute("cliente", cliente);
            return "cadastroClientes";
        }

        Contato contato = new Contato();
        contato.setTipoContato(tipoContato.trim());
        contato.setValorContato(valorContato.trim());
        if (observacoes != null) {
            String obs = observacoes.trim();
            contato.setObservacoes(obs.isEmpty() ? null : obs);
        }
        contato.setCliente(cliente);

        List<Contato> contatos = new ArrayList<>();
        contatos.add(contato);
        cliente.setContatos(contatos);

        try {
            clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("erroMensagem", "Erro ao salvar: CPF já cadastrado ou dados inválidos.");
            model.addAttribute("cliente", cliente);
            return "cadastroClientes";
        } catch (Exception ex) {
            model.addAttribute("erroMensagem", "Erro inesperado ao salvar o cliente. Tente novamente.");
            model.addAttribute("cliente", cliente);
            return "cadastroClientes";
        }

        return "redirect:/consultaClientes"; }
    

    private void sanitizeCliente(Cliente cliente) {
        if (cliente == null) {
            return;
        }
        cliente.setNome(trim(cliente.getNome()));
        cliente.setCpf(trim(cliente.getCpf()));
        cliente.setEndereco(trim(cliente.getEndereco()));
        cliente.setCidade(trim(cliente.getCidade()));
        cliente.setEstado(trim(cliente.getEstado()));
        cliente.setCep(trim(cliente.getCep()));
    }

    private String trim(String value) {
        return value == null ? null : value.trim();
    }
}
