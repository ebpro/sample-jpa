package fr.univtln.bruno.samples.jpa.todolist.daos;


import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public abstract class DAO<E> {
    @SuppressWarnings("unchecked")
    protected final Class<E> entityClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    protected EntityManager entityManager;

    public void persist(E entity) {
        entityManager.persist(entity);
    }

    public void remove(long id) {
        entityManager.remove(findById(id));
    }

    public void remove(UUID uuid) {
        entityManager.remove(findByUUID(uuid));
    }

    public void remove(E entity) {
        entityManager.remove(entity);
    }

    public E findById(long id) {
        return entityManager.find(entityClass, id);
    }

    abstract List<E> findAll();

    abstract E findByUUID(UUID uuid);
}
