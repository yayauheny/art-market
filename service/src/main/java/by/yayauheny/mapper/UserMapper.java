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

  UserResponse toResponse(UserEntity user);

  List<UserResponse> toResponseList(List<UserEntity> users);
}
