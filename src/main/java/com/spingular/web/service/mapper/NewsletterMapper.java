package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.NewsletterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Newsletter} and its DTO {@link NewsletterDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NewsletterMapper extends EntityMapper<NewsletterDTO, Newsletter> {



    default Newsletter fromId(Long id) {
        if (id == null) {
            return null;
        }
        Newsletter newsletter = new Newsletter();
        newsletter.setId(id);
        return newsletter;
    }
}
