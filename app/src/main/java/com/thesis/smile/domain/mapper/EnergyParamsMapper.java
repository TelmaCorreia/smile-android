package com.thesis.smile.domain.mapper;

import com.thesis.smile.data.remote.models.EnergyParamsRemote;
import com.thesis.smile.domain.mapper.base.BaseMapper;
import com.thesis.smile.domain.models.EnergyParams;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EnergyParamsMapper extends BaseMapper<EnergyParams, EnergyParamsRemote> {

    EnergyParamsMapper INSTANCE = Mappers.getMapper(EnergyParamsMapper.class);

    EnergyParamsRemote domainToRemote(EnergyParams domain);

    EnergyParams remoteToDomain(EnergyParamsRemote remote);

}
