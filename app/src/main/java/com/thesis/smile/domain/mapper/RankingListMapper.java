package com.thesis.smile.domain.mapper;

import com.thesis.smile.data.remote.models.RankingRemoteList;
import com.thesis.smile.data.remote.models.RankingRenewableRemote;
import com.thesis.smile.domain.mapper.base.BaseMapper;
import com.thesis.smile.domain.models.RankingModel;
import com.thesis.smile.domain.models.RankingModelList;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RankingListMapper extends BaseMapper<RankingModelList, RankingRemoteList> {

    RankingListMapper INSTANCE = Mappers.getMapper(RankingListMapper.class);

    RankingRemoteList domainToRemote(RankingModelList domain);

    RankingModelList remoteToDomain(RankingRemoteList remote);

  List<RankingModelList> remoteToDomain(List<RankingRemoteList> remote);



}
