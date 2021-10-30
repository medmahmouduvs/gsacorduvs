package com.gsacorduvs.po.web.rest;

import com.gsacorduvs.po.domain.Accorde;
import com.gsacorduvs.po.repository.AccordeRepository;
import com.gsacorduvs.po.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.gsacorduvs.po.domain.Accorde}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AccordeResource {

    private final Logger log = LoggerFactory.getLogger(AccordeResource.class);

    private static final String ENTITY_NAME = "accorde";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccordeRepository accordeRepository;

    public AccordeResource(AccordeRepository accordeRepository) {
        this.accordeRepository = accordeRepository;
    }

    /**
     * {@code POST  /accordes} : Create a new accorde.
     *
     * @param accorde the accorde to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accorde, or with status {@code 400 (Bad Request)} if the accorde has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/accordes")
    public ResponseEntity<Accorde> createAccorde(@RequestBody Accorde accorde) throws URISyntaxException {
        log.debug("REST request to save Accorde : {}", accorde);
        if (accorde.getId() != null) {
            throw new BadRequestAlertException("A new accorde cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Accorde result = accordeRepository.save(accorde);
        return ResponseEntity
            .created(new URI("/api/accordes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /accordes/:id} : Updates an existing accorde.
     *
     * @param id the id of the accorde to save.
     * @param accorde the accorde to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accorde,
     * or with status {@code 400 (Bad Request)} if the accorde is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accorde couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/accordes/{id}")
    public ResponseEntity<Accorde> updateAccorde(@PathVariable(value = "id", required = false) final Long id, @RequestBody Accorde accorde)
        throws URISyntaxException {
        log.debug("REST request to update Accorde : {}, {}", id, accorde);
        if (accorde.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accorde.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accordeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Accorde result = accordeRepository.save(accorde);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accorde.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /accordes/:id} : Partial updates given fields of an existing accorde, field will ignore if it is null
     *
     * @param id the id of the accorde to save.
     * @param accorde the accorde to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accorde,
     * or with status {@code 400 (Bad Request)} if the accorde is not valid,
     * or with status {@code 404 (Not Found)} if the accorde is not found,
     * or with status {@code 500 (Internal Server Error)} if the accorde couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/accordes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Accorde> partialUpdateAccorde(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Accorde accorde
    ) throws URISyntaxException {
        log.debug("REST request to partial update Accorde partially : {}, {}", id, accorde);
        if (accorde.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accorde.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accordeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Accorde> result = accordeRepository
            .findById(accorde.getId())
            .map(
                existingAccorde -> {
                    if (accorde.getTitre() != null) {
                        existingAccorde.setTitre(accorde.getTitre());
                    }
                    if (accorde.getTeritornature() != null) {
                        existingAccorde.setTeritornature(accorde.getTeritornature());
                    }
                    if (accorde.getStatusacord() != null) {
                        existingAccorde.setStatusacord(accorde.getStatusacord());
                    }
                    if (accorde.getDateAccord() != null) {
                        existingAccorde.setDateAccord(accorde.getDateAccord());
                    }
                    if (accorde.getSignaturereacteru() != null) {
                        existingAccorde.setSignaturereacteru(accorde.getSignaturereacteru());
                    }
                    if (accorde.getSignatureDiircore() != null) {
                        existingAccorde.setSignatureDiircore(accorde.getSignatureDiircore());
                    }
                    if (accorde.getSignatureChefEtab() != null) {
                        existingAccorde.setSignatureChefEtab(accorde.getSignatureChefEtab());
                    }
                    if (accorde.getArticle() != null) {
                        existingAccorde.setArticle(accorde.getArticle());
                    }

                    return existingAccorde;
                }
            )
            .map(accordeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accorde.getId().toString())
        );
    }

    /**
     * {@code GET  /accordes} : get all the accordes.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accordes in body.
     */
    @GetMapping("/accordes")
    public ResponseEntity<List<Accorde>> getAllAccordes(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Accordes");
        Page<Accorde> page;
        if (eagerload) {
            page = accordeRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = accordeRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /accordes/:id} : get the "id" accorde.
     *
     * @param id the id of the accorde to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accorde, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/accordes/{id}")
    public ResponseEntity<Accorde> getAccorde(@PathVariable Long id) {
        log.debug("REST request to get Accorde : {}", id);
        Optional<Accorde> accorde = accordeRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(accorde);
    }

    /**
     * {@code DELETE  /accordes/:id} : delete the "id" accorde.
     *
     * @param id the id of the accorde to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/accordes/{id}")
    public ResponseEntity<Void> deleteAccorde(@PathVariable Long id) {
        log.debug("REST request to delete Accorde : {}", id);
        accordeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
