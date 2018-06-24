package com.geardao.phoneservice.service;


import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.geardao.phoneservice.domain.Phone;
import com.geardao.phoneservice.domain.*; // for static metamodels
import com.geardao.phoneservice.repository.PhoneRepository;
import com.geardao.phoneservice.repository.search.PhoneSearchRepository;
import com.geardao.phoneservice.service.dto.PhoneCriteria;

import com.geardao.phoneservice.service.dto.PhoneDTO;
import com.geardao.phoneservice.service.mapper.PhoneMapper;

/**
 * Service for executing complex queries for Phone entities in the database.
 * The main input is a {@link PhoneCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PhoneDTO} or a {@link Page} of {@link PhoneDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PhoneQueryService extends QueryService<Phone> {

    private final Logger log = LoggerFactory.getLogger(PhoneQueryService.class);


    private final PhoneRepository phoneRepository;

    private final PhoneMapper phoneMapper;

    private final PhoneSearchRepository phoneSearchRepository;

    public PhoneQueryService(PhoneRepository phoneRepository, PhoneMapper phoneMapper, PhoneSearchRepository phoneSearchRepository) {
        this.phoneRepository = phoneRepository;
        this.phoneMapper = phoneMapper;
        this.phoneSearchRepository = phoneSearchRepository;
    }

    /**
     * Return a {@link List} of {@link PhoneDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PhoneDTO> findByCriteria(PhoneCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Phone> specification = createSpecification(criteria);
        return phoneMapper.toDto(phoneRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PhoneDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PhoneDTO> findByCriteria(PhoneCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Phone> specification = createSpecification(criteria);
        final Page<Phone> result = phoneRepository.findAll(specification, page);
        return result.map(phoneMapper::toDto);
    }

    /**
     * Function to convert PhoneCriteria to a {@link Specifications}
     */
    private Specifications<Phone> createSpecification(PhoneCriteria criteria) {
        Specifications<Phone> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Phone_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Phone_.name));
            }
            if (criteria.getBrand() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBrand(), Phone_.brand));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Phone_.price));
            }
        }
        return specification;
    }

}
