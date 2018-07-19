package com.thesis.smile.domain.mapper;

import com.thesis.smile.data.remote.models.AddressRemote;
import com.thesis.smile.data.remote.models.ConfigsRemote;
import com.thesis.smile.domain.mapper.base.BaseMapper;
import com.thesis.smile.domain.models.Address;
import com.thesis.smile.domain.models.Configs;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper extends BaseMapper<Address, AddressRemote> {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressRemote domainToRemote(Address domain);

    Address remoteToDomain(AddressRemote remote);

}
