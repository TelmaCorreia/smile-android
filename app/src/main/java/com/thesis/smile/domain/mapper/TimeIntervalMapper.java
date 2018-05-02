package com.thesis.smile.domain.mapper;

import com.thesis.smile.data.remote.models.TimeIntervalRemote;
import com.thesis.smile.domain.mapper.base.BaseMapper;
import com.thesis.smile.domain.models.TimeInterval;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TimeIntervalMapper extends BaseMapper<TimeInterval, TimeIntervalRemote> {

    TimeIntervalMapper INSTANCE = Mappers.getMapper(TimeIntervalMapper.class);

    TimeIntervalRemote domainToRemote(TimeInterval domain);

    TimeInterval remoteToDomain(TimeIntervalRemote remote);

    List<TimeInterval> remoteToDomain(List<TimeIntervalRemote> remote);


}
