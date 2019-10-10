package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.FeedbackDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Feedback} and its DTO {@link FeedbackDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FeedbackMapper extends EntityMapper<FeedbackDTO, Feedback> {



    default Feedback fromId(Long id) {
        if (id == null) {
            return null;
        }
        Feedback feedback = new Feedback();
        feedback.setId(id);
        return feedback;
    }
}
