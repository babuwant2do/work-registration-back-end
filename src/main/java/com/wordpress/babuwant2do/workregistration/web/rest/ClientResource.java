package com.wordpress.babuwant2do.workregistration.web.rest;

import com.wordpress.babuwant2do.workregistration.domain.Client;
import com.wordpress.babuwant2do.workregistration.security.SecurityUtils;
import com.wordpress.babuwant2do.workregistration.service.ClientService;
import com.wordpress.babuwant2do.workregistration.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;

/**
 * REST controller for managing Client.
 */
@RestController
@RequestMapping("/api")
public class ClientResource {

    private final Logger log = LoggerFactory.getLogger(ClientResource.class);

    private static final String ENTITY_NAME = "client";

    private final ClientService clientService;

    public ClientResource(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * POST  /clients : Create a new client.
     *
     * @param client the client to create
     * @return the ResponseEntity with status 201 (Created) and with body the new client, or with status 400 (Bad Request) if the client has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) throws URISyntaxException {
    	HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);
        log.debug("REST request to save Client : {}", client);
        if (client.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new client cannot already have an ID")).body(null);
        }
        Client result = clientService.save(client);
        return ResponseEntity.created(new URI("/api/clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clients : Updates an existing client.
     *
     * @param client the client to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated client,
     * or with status 400 (Bad Request) if the client is not valid,
     * or with status 500 (Internal Server Error) if the client couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clients")
    public ResponseEntity<Client> updateClient(@Valid @RequestBody Client client) throws URISyntaxException {
        log.debug("REST request to update Client : {}", client);
        if (client.getId() == null) {
            return createClient(client);
        }
        Client result = clientService.save(client);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, client.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clients : get all the clients.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clients in body
     */
    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients() {
        log.debug("REST request to get a page of Clients");
        System.out.println("##### CURRENT LOGGIN USER: "+ SecurityUtils.getCurrentUserLogin() + ", IS LOGGEDIN: "+ SecurityUtils.isAuthenticated());
        List<Client> page = clientService.findAll();
        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }

    /**
     * GET  /clients/:id : get the "id" client.
     *
     * @param id the id of the client to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the client, or with status 404 (Not Found)
     */
    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        log.debug("REST request to get Client : {}", id);
        Client client = clientService.findOne(id);
        if(client != null){
        	return new ResponseEntity<Client>(client, HttpStatus.OK);        	
        }
        return new ResponseEntity<Client>(client, HttpStatus.NOT_FOUND);        	
        
    }

    /**
     * DELETE  /clients/:id : delete the "id" client.
     *
     * @param id the id of the client to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        log.debug("REST request to delete Client : {}", id);
        clientService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
