package com.thesis.smile.domain.mapper;

import com.thesis.smile.data.remote.models.UserRemote;
import com.thesis.smile.domain.mapper.base.BaseMapper;
import com.thesis.smile.domain.models.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper extends BaseMapper<User, UserRemote> {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserRemote domainToRemote(User domain);

    User remoteToDomain(UserRemote remote);

}
