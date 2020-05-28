package com.example.demo.mapstruct;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapstructMapper {

    MapstructMapper MAPPER = Mappers.getMapper( MapstructMapper.class );

    TestEntity dataToEntity(TestStruct req);

    TestStruct entityToData(TestEntity req);

}
