package com.thesis.smile.domain.mapper;

import com.thesis.smile.data.remote.models.NeighbourRemote;
import com.thesis.smile.domain.mapper.base.BaseMapper;
import com.thesis.smile.domain.models.Neighbour;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface NeighbourMapper extends BaseMapper<Neighbour, NeighbourRemote> {

    NeighbourMapper INSTANCE = Mappers.getMapper(NeighbourMapper.class);

    NeighbourRemote domainToRemote(Neighbour domain);

    Neighbour remoteToDomain(NeighbourRemote remote);

    List<Neighbour> remoteToDomain(List<NeighbourRemote> remote);

}
