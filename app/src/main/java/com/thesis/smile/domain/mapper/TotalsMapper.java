package com.thesis.smile.domain.mapper;

import com.thesis.smile.data.remote.models.TotalsRemote;
import com.thesis.smile.domain.mapper.base.BaseMapper;
import com.thesis.smile.domain.models.Totals;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TotalsMapper extends BaseMapper<Totals,TotalsRemote > {

    TotalsMapper INSTANCE = Mappers.getMapper(TotalsMapper.class);

    TotalsRemote domainToRemote(Totals domain);

    Totals remoteToDomain(TotalsRemote remote);



}
