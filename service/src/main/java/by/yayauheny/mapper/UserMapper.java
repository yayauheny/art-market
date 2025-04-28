package by.yayauheny.mapper;

import by.yayauheny.dto.UserSaveRequest;
import by.yayauheny.dto.UserResponse;
import by.yayauheny.dto.UserUpdateRequest;
import by.yayauheny.entity.UserEntity;
import by.yayauheny.util.MapperUtil;
import java.time.LocalDateTime;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.SPRING,
    uses = {MapperUtil.class},
    imports = {LocalDateTime.class},
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

  UserResponse toResponse(UserEntity user);

  List<UserResponse> toResponseList(List<UserEntity> users);

  @Mappings({
      @Mapping(
          target = "password",
          qualifiedByName = {"MapperUtil", "encodePassword"}, source = "password"
      ),
      @Mapping(target = "email", qualifiedByName = {"MapperUtil", "normalizeEmail"})
  }
  )
  UserEntity toEntity(UserSaveRequest request);

  UserEntity toEntity(UserUpdateRequest updateRequest, @MappingTarget UserEntity user);
}
