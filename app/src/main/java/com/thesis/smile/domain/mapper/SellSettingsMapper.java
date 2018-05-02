package com.thesis.smile.domain.mapper;

import com.thesis.smile.data.remote.models.SellSettingsRemote;
import com.thesis.smile.domain.mapper.base.BaseMapper;
import com.thesis.smile.domain.models.SellSettings;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SellSettingsMapper extends BaseMapper<SellSettings,SellSettingsRemote> {

    SellSettingsMapper INSTANCE = Mappers.getMapper(SellSettingsMapper.class);

    SellSettingsRemote domainToRemote(SellSettings domain);

    SellSettings remoteToDomain(SellSettingsRemote remote);



}
