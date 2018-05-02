package com.thesis.smile.domain.mapper;

import com.thesis.smile.data.remote.models.BuySettingsRemote;
import com.thesis.smile.domain.mapper.base.BaseMapper;
import com.thesis.smile.domain.models.BuySettings;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BuySettingsMapper extends BaseMapper<BuySettings,BuySettingsRemote> {

    BuySettingsMapper INSTANCE = Mappers.getMapper(BuySettingsMapper.class);

    BuySettingsRemote domainToRemote(BuySettings domain);

    BuySettings remoteToDomain(BuySettingsRemote remote);



}
