package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.ConfigVariablesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConfigVariables} and its DTO {@link ConfigVariablesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConfigVariablesMapper extends EntityMapper<ConfigVariablesDTO, ConfigVariables> {



    default ConfigVariables fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConfigVariables configVariables = new ConfigVariables();
        configVariables.setId(id);
        return configVariables;
    }
}
