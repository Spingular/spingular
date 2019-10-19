package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.AppprofileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Appprofile} and its DTO {@link AppprofileDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppuserMapper.class, UserMapper.class, AppphotoMapper.class})
public interface AppprofileMapper extends EntityMapper<AppprofileDTO, Appprofile> {

    @Mapping(source = "appuser.id", target = "appuserId")
    @Mapping(source = "appuser.user.id", target = "userId")
    @Mapping(source = "appuser.user.login", target = "userLogin")				
    @Mapping(source = "appuser.user.firstName", target = "userFirstName")				
    @Mapping(source = "appuser.user.lastName", target = "userLastName")
    @Mapping(source = "appuser.appphoto.image", target = "userImage")
    @Mapping(source = "appuser.appphoto.imageContentType", target = "userImageContentType")
    AppprofileDTO toDto(Appprofile appprofile);

    @Mapping(source = "appuserId", target = "appuser")
    Appprofile toEntity(AppprofileDTO appprofileDTO);

    default Appprofile fromId(Long id) {
        if (id == null) {
            return null;
        }
        Appprofile appprofile = new Appprofile();
        appprofile.setId(id);
        return appprofile;
    }
}
