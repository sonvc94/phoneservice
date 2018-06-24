package com.geardao.phoneservice.service.impl;

import com.geardao.phoneservice.service.PhoneService;
import com.geardao.phoneservice.domain.Phone;
import com.geardao.phoneservice.repository.PhoneRepository;
import com.geardao.phoneservice.repository.search.PhoneSearchRepository;
import com.geardao.phoneservice.service.dto.PhoneDTO;
import com.geardao.phoneservice.service.mapper.PhoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Phone.
 */
@Service
@Transactional
public class PhoneServiceImpl implements PhoneService {

    private final Logger log = LoggerFactory.getLogger(PhoneServiceImpl.class);

    private final PhoneRepository phoneRepository;

    private final PhoneMapper phoneMapper;

    private final PhoneSearchRepository phoneSearchRepository;

    public PhoneServiceImpl(PhoneRepository phoneRepository, PhoneMapper phoneMapper, PhoneSearchRepository phoneSearchRepository) {
        this.phoneRepository = phoneRepository;
        this.phoneMapper = phoneMapper;
        this.phoneSearchRepository = phoneSearchRepository;
    }

    /**
     * Save a phone.
     *
     * @param phoneDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PhoneDTO save(PhoneDTO phoneDTO) {
        log.debug("Request to save Phone : {}", phoneDTO);
        Phone phone = phoneMapper.toEntity(phoneDTO);
        phone = phoneRepository.save(phone);
        PhoneDTO result = phoneMapper.toDto(phone);
        phoneSearchRepository.save(phone);
        return result;
    }

    /**
     * Get all the phones.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PhoneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Phones");
        return phoneRepository.findAll(pageable)
            .map(phoneMapper::toDto);
    }

    /**
     * Get one phone by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PhoneDTO findOne(Long id) {
        log.debug("Request to get Phone : {}", id);
        Phone phone = phoneRepository.findOne(id);
        return phoneMapper.toDto(phone);
    }

    /**
     * Delete the phone by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Phone : {}", id);
        phoneRepository.delete(id);
        phoneSearchRepository.delete(id);
    }

    /**
     * Search for the phone corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PhoneDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Phones for query {}", query);
        Page<Phone> result = phoneSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(phoneMapper::toDto);
    }
}
