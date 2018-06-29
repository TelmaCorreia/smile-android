package com.thesis.smile.domain.mapper;

import com.thesis.smile.data.remote.models.HistoricalDataPointRemote;
import com.thesis.smile.data.remote.models.HistoricalDataRemote;
import com.thesis.smile.domain.mapper.base.BaseMapper;
import com.thesis.smile.domain.models.HistoricalData;
import com.thesis.smile.domain.models.HistoricalDataPoint;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface HistoricalDataPointMapper extends BaseMapper<HistoricalDataPoint, HistoricalDataPointRemote> {

    HistoricalDataPointMapper INSTANCE = Mappers.getMapper(HistoricalDataPointMapper.class);

    HistoricalDataPointRemote domainToRemote(HistoricalDataPoint domain);

    HistoricalDataPoint remoteToDomain(HistoricalDataPointRemote remote);

    List<HistoricalDataPoint> remoteToDomain(List<HistoricalDataPointRemote> remote);



}
