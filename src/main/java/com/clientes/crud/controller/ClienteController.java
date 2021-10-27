package com.clientes.crud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clientes.crud.model.Cliente;
import com.clientes.crud.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;
	
	// getAllClientes
	
	@GetMapping
	public List<Cliente> getAllClientes() {
		return this.clienteRepository.findAll();
	}
	
	// getClienteById
	
	@GetMapping("{id}")
	public ResponseEntity<Cliente> getClienteById(@PathVariable(value = "id") Long clienteId) throws Exception {
		Cliente cliente = clienteRepository.findById(clienteId)
				.orElseThrow(() -> new Exception("Não foi encontrado um cliente com id " + clienteId));
		return ResponseEntity.ok().body(cliente);
	}
	
	// Create Cliente
	
	@PostMapping
	public Cliente createCliente(@RequestBody Cliente cliente) {
		return this.clienteRepository.save(cliente);
	}
	
	// Update Cliente
	
	@PutMapping("{id}")
	public ResponseEntity<Cliente> updateCliente(@PathVariable(value = "id") Long clienteId,
			@Validated @RequestBody Cliente clienteUpd) throws Exception {
		Cliente cliente = clienteRepository.findById(clienteId)
				.orElseThrow(() -> new Exception("Não foi encontrado um cliente com o id " + clienteId));
		cliente.setEmail(clienteUpd.getEmail());
		cliente.setNome(clienteUpd.getNome());
		cliente.setTelefone(clienteUpd.getTelefone());
		return ResponseEntity.ok(this.clienteRepository.save(cliente));
	}
	
	// Delete Cliente
	
	@DeleteMapping("{id}")
	public Map<String, Boolean> deleteCliente(@PathVariable(value = "id") Long clienteId) throws Exception {
		Cliente cliente = clienteRepository.findById(clienteId)
				.orElseThrow(() -> new Exception("Não foi encontrado um cliente com o id " + clienteId));
		this.clienteRepository.delete(cliente);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
}
