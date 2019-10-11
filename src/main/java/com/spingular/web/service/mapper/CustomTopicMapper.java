package com.spingular.web.service.mapper;

import com.spingular.web.domain.Topic;
import com.spingular.web.service.dto.CustomTopicDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tag and its DTO TagDTO.
 */
@Mapper(componentModel = "spring", uses = {PostMapper.class})
public interface CustomTopicMapper extends EntityMapper<CustomTopicDTO, Topic> {


	
    default Topic fromId(Long id) {
        if (id == null) {
            return null;
        }
        Topic topic = new Topic();
        topic.setId(id);
        return topic;
    }
}
