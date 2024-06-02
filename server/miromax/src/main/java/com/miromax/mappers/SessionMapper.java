package com.miromax.mappers;

import com.miromax.dtos.SessionInfoDto;
import com.miromax.models.Session;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SessionMapper {
    SessionMapper MAPPER = Mappers.getMapper(SessionMapper.class);
//    Session toSessionEntity(SessionPostDto sessionPostDto);
//    @Mapping(target = "hall.location.cityId", source = "hall.location.city.id")
//    @Mapping(target = "hall.location.cityName", source = "hall.location.city.name")
//    SessionDto toSessionDto(Session session);

    SessionInfoDto toSessionInfoDto(Session session);
//    default Page<SessionDto> toSessionDtoPage(Page<Session> sessionPage) {
//        return sessionPage.map(this::toSessionDto);
//    }

    default List<SessionInfoDto> toSessionInfoDtoList(List<Session> sessionList) {
        return sessionList.stream()
                .map(this::toSessionInfoDto)
                .collect(Collectors.toList());
    }
}