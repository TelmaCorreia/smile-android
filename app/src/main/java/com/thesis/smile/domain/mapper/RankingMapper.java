package com.thesis.smile.domain.mapper;

import com.thesis.smile.data.remote.models.RankingRemote;
import com.thesis.smile.domain.mapper.base.BaseMapper;
import com.thesis.smile.domain.models.Ranking;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RankingMapper extends BaseMapper<Ranking, RankingRemote> {

    RankingMapper INSTANCE = Mappers.getMapper(RankingMapper.class);

    RankingRemote domainToRemote(Ranking domain);

    Ranking remoteToDomain(RankingRemote remote);


}
