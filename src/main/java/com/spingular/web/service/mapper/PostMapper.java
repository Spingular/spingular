package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.PostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppuserMapper.class, BlogMapper.class, UserMapper.class, AppphotoMapper.class})
public interface PostMapper extends EntityMapper<PostDTO, Post> {

    @Mapping(source = "appuser.id", target = "appuserId")
    @Mapping(source = "blog.id", target = "blogId")
    @Mapping(source = "blog.title", target = "blogTitle")
    @Mapping(source = "appuser.user.login", target = "userLogin")				
    @Mapping(source = "appuser.user.firstName", target = "userFirstName")				
    @Mapping(source = "appuser.user.lastName", target = "userLastName")
    @Mapping(source = "appuser.appphoto.image", target = "userImage")
    @Mapping(source = "appuser.appphoto.imageContentType", target = "userImageContentType")
    PostDTO toDto(Post post);

    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "removeComment", ignore = true)
    @Mapping(target = "proposals", ignore = true)
    @Mapping(target = "removeProposal", ignore = true)
    @Mapping(source = "appuserId", target = "appuser")
    @Mapping(source = "blogId", target = "blog")
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "removeTag", ignore = true)
    @Mapping(target = "topics", ignore = true)
    @Mapping(target = "removeTopic", ignore = true)
    Post toEntity(PostDTO postDTO);

    default Post fromId(Long id) {
        if (id == null) {
            return null;
        }
        Post post = new Post();
        post.setId(id);
        return post;
    }
}
