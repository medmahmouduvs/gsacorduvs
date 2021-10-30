package com.gsacorduvs.po.web.rest;

import com.gsacorduvs.po.domain.EtablisUser;
import com.gsacorduvs.po.repository.EtablisUserRepository;
import com.gsacorduvs.po.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.gsacorduvs.po.domain.EtablisUser}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EtablisUserResource {

    private final Logger log = LoggerFactory.getLogger(EtablisUserResource.class);

    private static final String ENTITY_NAME = "etablisUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtablisUserRepository etablisUserRepository;

    public EtablisUserResource(EtablisUserRepository etablisUserRepository) {
        this.etablisUserRepository = etablisUserRepository;
    }

    /**
     * {@code POST  /etablis-users} : Create a new etablisUser.
     *
     * @param etablisUser the etablisUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etablisUser, or with status {@code 400 (Bad Request)} if the etablisUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etablis-users")
    public ResponseEntity<EtablisUser> createEtablisUser(@RequestBody EtablisUser etablisUser) throws URISyntaxException {
        log.debug("REST request to save EtablisUser : {}", etablisUser);
        if (etablisUser.getId() != null) {
            throw new BadRequestAlertException("A new etablisUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtablisUser result = etablisUserRepository.save(etablisUser);
        return ResponseEntity
            .created(new URI("/api/etablis-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etablis-users/:id} : Updates an existing etablisUser.
     *
     * @param id the id of the etablisUser to save.
     * @param etablisUser the etablisUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etablisUser,
     * or with status {@code 400 (Bad Request)} if the etablisUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etablisUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etablis-users/{id}")
    public ResponseEntity<EtablisUser> updateEtablisUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EtablisUser etablisUser
    ) throws URISyntaxException {
        log.debug("REST request to update EtablisUser : {}, {}", id, etablisUser);
        if (etablisUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etablisUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etablisUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EtablisUser result = etablisUserRepository.save(etablisUser);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etablisUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /etablis-users/:id} : Partial updates given fields of an existing etablisUser, field will ignore if it is null
     *
     * @param id the id of the etablisUser to save.
     * @param etablisUser the etablisUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etablisUser,
     * or with status {@code 400 (Bad Request)} if the etablisUser is not valid,
     * or with status {@code 404 (Not Found)} if the etablisUser is not found,
     * or with status {@code 500 (Internal Server Error)} if the etablisUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/etablis-users/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EtablisUser> partialUpdateEtablisUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EtablisUser etablisUser
    ) throws URISyntaxException {
        log.debug("REST request to partial update EtablisUser partially : {}, {}", id, etablisUser);
        if (etablisUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etablisUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etablisUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EtablisUser> result = etablisUserRepository
            .findById(etablisUser.getId())
            .map(
                existingEtablisUser -> {
                    if (etablisUser.getNome() != null) {
                        existingEtablisUser.setNome(etablisUser.getNome());
                    }
                    if (etablisUser.getEmail() != null) {
                        existingEtablisUser.setEmail(etablisUser.getEmail());
                    }
                    if (etablisUser.getPosition() != null) {
                        existingEtablisUser.setPosition(etablisUser.getPosition());
                    }
                    if (etablisUser.getUsername() != null) {
                        existingEtablisUser.setUsername(etablisUser.getUsername());
                    }
                    if (etablisUser.getPassword() != null) {
                        existingEtablisUser.setPassword(etablisUser.getPassword());
                    }

                    return existingEtablisUser;
                }
            )
            .map(etablisUserRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etablisUser.getId().toString())
        );
    }

    /**
     * {@code GET  /etablis-users} : get all the etablisUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etablisUsers in body.
     */
    @GetMapping("/etablis-users")
    public List<EtablisUser> getAllEtablisUsers() {
        log.debug("REST request to get all EtablisUsers");
        return etablisUserRepository.findAll();
    }

    /**
     * {@code GET  /etablis-users/:id} : get the "id" etablisUser.
     *
     * @param id the id of the etablisUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etablisUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etablis-users/{id}")
    public ResponseEntity<EtablisUser> getEtablisUser(@PathVariable Long id) {
        log.debug("REST request to get EtablisUser : {}", id);
        Optional<EtablisUser> etablisUser = etablisUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(etablisUser);
    }

    /**
     * {@code DELETE  /etablis-users/:id} : delete the "id" etablisUser.
     *
     * @param id the id of the etablisUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etablis-users/{id}")
    public ResponseEntity<Void> deleteEtablisUser(@PathVariable Long id) {
        log.debug("REST request to delete EtablisUser : {}", id);
        etablisUserRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
