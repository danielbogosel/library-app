package com.devtiro.database.v2.mappers.impl;

import com.devtiro.database.v2.domain.dto.AuthorDto;
import com.devtiro.database.v2.domain.entities.AuthorEntity;
import com.devtiro.database.v2.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto> {
    private ModelMapper mapperModel;

    public AuthorMapperImpl(ModelMapper mapperModel) {
        this.mapperModel = mapperModel;
    }

    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        return mapperModel.map(authorEntity, AuthorDto.class);
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        return mapperModel.map(authorDto, AuthorEntity.class);
    }
}
