package com.spingular.web.service.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.spingular.web.domain.Urllink;
import com.spingular.web.repository.UrllinkRepository;;

/**
 * Mapper for the entity UrlLink and its DTO UrlLinkDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public abstract class FrontPageUrllinkMapper {

    @Autowired
    private UrllinkRepository urllinkRepository;

    public Urllink postFromId(Long id) {
        if (id == null) {
            return null;
        }
        return urllinkRepository.findById(id).orElse(new Urllink());
    }
    public Long idFromUrllink(Urllink urllink) {
        return urllink.getId();
    }
}
