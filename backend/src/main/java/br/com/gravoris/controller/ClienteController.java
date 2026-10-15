package br.com.gravoris.controller;

import br.com.gravoris.dto.ClienteRequest;
import br.com.gravoris.dto.ClienteResponse;
import br.com.gravoris.dto.LoginRequest;
import br.com.gravoris.dto.LoginResponse;
import br.com.gravoris.dto.RedefinirSenhaRequest;
import br.com.gravoris.model.Cliente;
import br.com.gravoris.service.ClienteService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * Controller REST para expor operações de cliente para o frontend Angular.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrar(@Valid @RequestBody ClienteRequest request) {
        Cliente cliente = clienteService.salvar(request);
        return ResponseEntity.ok(toResponse(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
        Cliente cliente = clienteService.alterar(id, request);
        return ResponseEntity.ok(toResponse(cliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(toResponse(cliente));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(clienteService.login(request));
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<Void> redefinirSenha(@Valid @RequestBody RedefinirSenhaRequest request) {
        clienteService.redefinirSenha(request);
        return ResponseEntity.ok().build();
    }

    private ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getCpf(),
                cliente.getDataNascimento() != null ? cliente.getDataNascimento().toString() : null,
                cliente.isAtivo()
        );
    }
}
