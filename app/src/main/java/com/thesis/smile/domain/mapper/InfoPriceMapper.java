package com.thesis.smile.domain.mapper;

import com.thesis.smile.data.remote.models.InfoPriceRemote;
import com.thesis.smile.domain.mapper.base.BaseMapper;
import com.thesis.smile.domain.models.InfoPrice;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InfoPriceMapper extends BaseMapper<InfoPrice,InfoPriceRemote> {

    InfoPriceMapper INSTANCE = Mappers.getMapper(InfoPriceMapper.class);

    InfoPriceRemote domainToRemote(InfoPrice domain);

    InfoPrice remoteToDomain(InfoPriceRemote remote);



}
