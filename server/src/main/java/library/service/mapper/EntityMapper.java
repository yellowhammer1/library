package library.service.mapper;

import java.util.List;

public interface EntityMapper <Entity, Dto>{
    Entity convertToEntity(Dto dto);

    Dto convertToDto(Entity entity);

    List<Dto> convertManyToDto(List<Entity> entities);
}
