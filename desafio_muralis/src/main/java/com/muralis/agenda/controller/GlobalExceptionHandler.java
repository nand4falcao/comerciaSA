package com.muralis.agenda.controller;

import com.muralis.agenda.model.Cliente;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("erroMensagem", "Erro inesperado: " + ex.getMessage());
        model.addAttribute("cliente", new Cliente());
        return "cadastroClientes";
    }
}
