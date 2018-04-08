package com.thesis.smile.domain.mapper.base;

public interface BaseMapper<Domain, Remote> {

    //map to domain
    Domain remoteToDomain(Remote remote);

    //map to remote
    Remote domainToRemote(Domain domain);

}