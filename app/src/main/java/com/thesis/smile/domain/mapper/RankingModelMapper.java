package com.thesis.smile.domain.mapper;

import com.thesis.smile.data.remote.models.RankingRenewableRemote;
import com.thesis.smile.domain.mapper.base.BaseMapper;
import com.thesis.smile.domain.models.RankingModel;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RankingModelMapper extends BaseMapper<RankingModel, RankingRenewableRemote> {

    RankingModelMapper INSTANCE = Mappers.getMapper(RankingModelMapper.class);

    RankingRenewableRemote domainToRemote(RankingModel domain);

    RankingModel remoteToDomain(RankingRenewableRemote remote);



}
