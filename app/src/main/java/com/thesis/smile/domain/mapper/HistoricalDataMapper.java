package com.thesis.smile.domain.mapper;

import com.thesis.smile.data.remote.models.HistoricalDataRemote;
import com.thesis.smile.data.remote.models.RankingRemoteList;
import com.thesis.smile.domain.mapper.base.BaseMapper;
import com.thesis.smile.domain.models.HistoricalData;
import com.thesis.smile.domain.models.RankingModelList;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface HistoricalDataMapper extends BaseMapper<HistoricalData, HistoricalDataRemote> {

    HistoricalDataMapper INSTANCE = Mappers.getMapper(HistoricalDataMapper.class);

    HistoricalDataRemote domainToRemote(HistoricalData domain);

    HistoricalData remoteToDomain(HistoricalDataRemote remote);

  List<HistoricalData> remoteToDomain(List<HistoricalDataRemote> remote);



}
