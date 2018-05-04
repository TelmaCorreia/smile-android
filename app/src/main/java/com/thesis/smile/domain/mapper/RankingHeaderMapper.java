package com.thesis.smile.domain.mapper;

import com.thesis.smile.data.remote.models.RankingRenewableRemote;
import com.thesis.smile.domain.mapper.base.BaseMapper;
import com.thesis.smile.domain.models.RankingHeader;
import com.thesis.smile.domain.models.RankingHeaderTest;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RankingHeaderMapper extends BaseMapper<RankingHeaderTest, RankingRenewableRemote> {

    RankingHeaderMapper INSTANCE = Mappers.getMapper(RankingHeaderMapper.class);

    RankingRenewableRemote domainToRemote(RankingHeaderTest domain);

    RankingHeaderTest remoteToDomain(RankingRenewableRemote remote);

    List<RankingHeaderTest> remoteToDomain(List<RankingRenewableRemote> remote);


}
