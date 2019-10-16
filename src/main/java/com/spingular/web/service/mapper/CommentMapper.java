package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.CommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppuserMapper.class, PostMapper.class, UserMapper.class, AppphotoMapper.class})
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {

    @Mapping(source = "appuser.id", target = "appuserId")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "appuser.user.firstName", target = "commenterFirstName")				
    @Mapping(source = "appuser.user.lastName", target = "commenterLastName")
    @Mapping(source = "appuser.appphoto.image", target = "commenterImage")
    @Mapping(source = "appuser.appphoto.imageContentType", target = "commenterImageContentType")
    CommentDTO toDto(Comment comment);

    @Mapping(source = "appuserId", target = "appuser")
    @Mapping(source = "postId", target = "post")
    Comment toEntity(CommentDTO commentDTO);

    default Comment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setId(id);
        return comment;
    }
}
