package com.devcraft.tores.data.repositories.impl

abstract class BaseRepositoryMapper<RepositoryDto : Any, Entity : Any?> {
    open fun mapDtoToEntity(dto: RepositoryDto): Entity? =
        throw IllegalStateException("Unsupported operation!")

    open fun mapEntityToDto(entity: Entity): RepositoryDto =
        throw IllegalStateException("Unsupported operation!")

    open fun mapDtoToEntity(dto: List<RepositoryDto>): MutableList<Entity?> =
        dto.map(::mapDtoToEntity).toMutableList()

    open fun mapEntityToDto(entity: List<Entity>): MutableList<RepositoryDto> =
        entity.map(::mapEntityToDto).toMutableList()
}