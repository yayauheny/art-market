package by.yayauheny.mapper;

import by.yayauheny.dto.UserResponse;
import by.yayauheny.entity.UserEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING,
    imports = {LocalDateTime.class},
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

//  @Mapping(target = "username", qualifiedByName = {"UserMapperUtil", "getUsername"}, source = "email")
//  @Mapping(target = "roles", qualifiedByName = {"UserMapperUtil", "setRoles"}, source = "role")
//  @Mapping(target = "lastLogin", expression = "java(LocalDateTime.now())")
//  @Mapping(target = "birthDate", dateFormat = "yyyy-MM-dd")
//  @Mapping(target = "email", expression = "java(dto.getEmail().trim().toLowerCase())")
//  UserEntity fromCreateRequest(UserCreateRequest dto);

//  @Mapping(target = "roles", qualifiedByName = {"UserMapperUtil", "convertRolesToRoleNames"}, source = "roles")
  UserResponse toResponse(UserEntity user);

  List<UserResponse> toResponseList(List<UserEntity> users);
}
