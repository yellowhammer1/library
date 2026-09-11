package library.service;

import java.util.List;

public interface CrudService<EntityDto, ID> {
    EntityDto create(EntityDto e);
    EntityDto readById(ID id);
    List<EntityDto> readAll();
    EntityDto update(ID id, EntityDto e);
    void deleteById(ID id);
}
