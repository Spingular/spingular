package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.BlogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Blog} and its DTO {@link BlogDTO}.
 */
@Mapper(componentModel = "spring", uses = {CommunityMapper.class, AppuserMapper.class, UserMapper.class})
public interface BlogMapper extends EntityMapper<BlogDTO, Blog> {

    @Mapping(source = "appuser.id", target = "appuserId")
    @Mapping(source = "community.id", target = "communityId")
    @Mapping(source = "community.communityName", target = "communityCommunityName")
    @Mapping(source = "community.appuser.user.id", target = "userId")
    BlogDTO toDto(Blog blog);

    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "removePost", ignore = true)
    @Mapping(source = "appuserId", target = "appuser")
    @Mapping(source = "communityId", target = "community")
    Blog toEntity(BlogDTO blogDTO);

    default Blog fromId(Long id) {
        if (id == null) {
            return null;
        }
        Blog blog = new Blog();
        blog.setId(id);
        return blog;
    }
}
