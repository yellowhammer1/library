package library.service.mapper;

import java.util.List;

public interface EntityMapper <Entity, Dto>{
    public Entity convertToEntity(Dto dto);

    public Dto convertToDto(Entity entity);

    public List<Dto> convertManyToDto(List<Entity> entities);
}
