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

    SessionInfoDto toSessionInfoDto(Session session);

    default List<SessionInfoDto> toSessionInfoDtoList(List<Session> sessionList) {
        return sessionList.stream()
                .map(this::toSessionInfoDto)
                .collect(Collectors.toList());
    }
}