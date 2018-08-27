package com.thesis.smile.domain.mapper;

import com.thesis.smile.data.remote.models.UsersRemote;
import com.thesis.smile.domain.mapper.base.BaseMapper;
import com.thesis.smile.domain.models.Users;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UsersMapper extends BaseMapper<Users, UsersRemote> {

    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    UsersRemote domainToRemote(Users domain);

    Users remoteToDomain(UsersRemote remote);

    List<Users> remoteToDomain(List<UsersRemote> remote);

}
