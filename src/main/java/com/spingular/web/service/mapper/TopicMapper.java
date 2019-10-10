package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.TopicDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Topic} and its DTO {@link TopicDTO}.
 */
@Mapper(componentModel = "spring", uses = {PostMapper.class})
public interface TopicMapper extends EntityMapper<TopicDTO, Topic> {


    @Mapping(target = "removePost", ignore = true)

    default Topic fromId(Long id) {
        if (id == null) {
            return null;
        }
        Topic topic = new Topic();
        topic.setId(id);
        return topic;
    }
}
