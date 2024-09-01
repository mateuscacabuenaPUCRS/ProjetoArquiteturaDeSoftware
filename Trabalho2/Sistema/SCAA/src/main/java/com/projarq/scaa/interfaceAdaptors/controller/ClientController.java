package com.projarq.scaa.interfaceAdaptors.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projarq.scaa.domain.entity.ClientEntity;
import com.projarq.scaa.interfaceAdaptors.DTOs.Client.CreateClientDTO;
import com.projarq.scaa.interfaceAdaptors.DTOs.Client.EditClientDTO;
import com.projarq.scaa.interfaceAdaptors.useCases.IClientUseCases;

@CrossOrigin(origins = "*") 
@RequestMapping("/servcad/clientes")
@RestController
public class ClientController {
    private IClientUseCases clientService; 
 
    @Autowired
    public ClientController(IClientUseCases clientService) {
        this.clientService = clientService;
    }

    /** Cadastrar na base de aplicativos */
    @PostMapping()
    public ClientEntity addClient(@RequestBody final CreateClientDTO client) {
        String clientName = client.getName();
        String clientEmail = client.getEmail();
        return clientService.create(clientName, clientEmail);
    }

    @PutMapping("/{idCliente}")
    public ClientEntity editClient(
        @PathVariable("idCliente") final Long clientId,
        @RequestBody final EditClientDTO client
    ) {
        String clientName = client.getName();
        String clientEmail = client.getEmail();
        return clientService.edit(clientId, clientName, clientEmail);
    }

    /** Lista com todos os clientes cadastrados */
    @GetMapping()
    public List<ClientEntity> getClients() {
        return clientService.getAllClients();
    }
}
