package com.nhan.mobilestore.mapper;

import com.nhan.mobilestore.dto.RegisterDTO;
import com.nhan.mobilestore.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel="spring")
@Component
public interface UserMapper {
    @Mapping(target = "role", source = "role.id") // call role.getId()
    RegisterDTO toDTO(User user);

    @Mapping(target = "role.id", source = "role") // call role.setId()
    User toEntity(RegisterDTO registerDTO);
}
