package com.thesis.smile.domain.mapper;

import com.thesis.smile.data.remote.models.CurrentEnergyDataRemote;
import com.thesis.smile.data.remote.models.TransactionRemote;
import com.thesis.smile.data.remote.models.TransactionsRemote;
import com.thesis.smile.domain.mapper.base.BaseMapper;
import com.thesis.smile.domain.models.CurrentEnergy;
import com.thesis.smile.domain.models.Transaction;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransactionMapper extends BaseMapper<Transaction, TransactionRemote> {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionRemote domainToRemote(Transaction domain);

    Transaction remoteToDomain(TransactionRemote remote);

    List<Transaction> remoteToDomain(List<TransactionRemote> remote);


}
