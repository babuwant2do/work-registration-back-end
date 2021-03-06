package com.wordpress.babuwant2do.workregistration.service;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wordpress.babuwant2do.workregistration.domain.Client;
import com.wordpress.babuwant2do.workregistration.repository.ClientRepository;


/**
 * Service Implementation for managing Client.
 */
@Service
@Transactional
public class ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        this.createInitDate();
    }
    
    /**
     * BAD code: temporary soln: 
     * add initial data... I should use Spring batch.. bt.. for now.. :)
     */
    private void createInitDate(){
    	if(this.clientRepository.count() <= 2 ){
    		List<Client> cl = new ArrayList<>();
    		for (int i = 0; i < 5; i++) {
    			cl.add(new Client("Name "+i, "name"+i+"@abc.com", "phone no "+i, "Addesaa, city  , country - "+i));				
			}
    		this.clientRepository.saveAll(cl);
    	} 
    }

    /**
     * Save a client.
     *
     * @param client the entity to save
     * @return the persisted entity
     */
    public Client save(Client client) {
        log.debug("Request to save Client : {}", client);
        return clientRepository.save(client);
    }

    /**
     *  Get all the clients.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        return clientRepository.findAll(pageable);
    }

    public List<Client> findAll() {
    	log.debug("Request to get all Clients");
    	return clientRepository.findAll();
    }

    /**
     *  Get one client by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Client findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id).orElse(null);
    }

    /**
     *  Delete the  client by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }
}
