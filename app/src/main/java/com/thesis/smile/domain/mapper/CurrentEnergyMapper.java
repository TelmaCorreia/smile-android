package com.thesis.smile.domain.mapper;

import com.thesis.smile.data.remote.models.ConfigsRemote;
import com.thesis.smile.data.remote.models.CurrentEnergyDataRemote;
import com.thesis.smile.domain.mapper.base.BaseMapper;
import com.thesis.smile.domain.models.Configs;
import com.thesis.smile.domain.models.CurrentEnergy;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CurrentEnergyMapper extends BaseMapper<CurrentEnergy, CurrentEnergyDataRemote> {

    CurrentEnergyMapper INSTANCE = Mappers.getMapper(CurrentEnergyMapper.class);

    CurrentEnergyDataRemote domainToRemote(CurrentEnergy domain);

    CurrentEnergy remoteToDomain(CurrentEnergyDataRemote remote);

}
