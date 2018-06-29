package com.thesis.smile.domain.mapper;

import com.thesis.smile.data.remote.models.HistoricalDataObjectRemote;
import com.thesis.smile.data.remote.models.HistoricalDataRemote;
import com.thesis.smile.domain.mapper.base.BaseMapper;
import com.thesis.smile.domain.models.HistoricalData;
import com.thesis.smile.domain.models.HistoricalDataObject;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface HistoricalDataObjectMapper extends BaseMapper<HistoricalDataObject, HistoricalDataObjectRemote> {

    HistoricalDataObjectMapper INSTANCE = Mappers.getMapper(HistoricalDataObjectMapper.class);

    HistoricalDataObjectRemote domainToRemote(HistoricalDataObject domain);

    HistoricalDataObject remoteToDomain(HistoricalDataObjectRemote remote);




}
