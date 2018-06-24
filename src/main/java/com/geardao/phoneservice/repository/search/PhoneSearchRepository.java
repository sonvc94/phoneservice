package com.geardao.phoneservice.repository.search;

import com.geardao.phoneservice.domain.Phone;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Phone entity.
 */
public interface PhoneSearchRepository extends ElasticsearchRepository<Phone, Long> {
}
