package com.thesis.smile.domain.mapper;

import com.thesis.smile.data.remote.models.ConfigsRemote;
import com.thesis.smile.data.remote.models.UserRemote;
import com.thesis.smile.domain.mapper.base.BaseMapper;
import com.thesis.smile.domain.models.Configs;
import com.thesis.smile.domain.models.User;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConfigsMapper extends BaseMapper<Configs, ConfigsRemote> {

    ConfigsMapper INSTANCE = Mappers.getMapper(ConfigsMapper.class);

    ConfigsRemote domainToRemote(Configs domain);

    Configs remoteToDomain(ConfigsRemote remote);

}
