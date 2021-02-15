package fr.univtln.bruno.samples.jpa.todolist.daos;

import fr.univtln.bruno.samples.jpa.todolist.entities.Tabular;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.List;
import java.util.UUID;

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TabularDAO extends DAO<Tabular> {

    public static TabularDAO of(EntityManager entityManager) {
        return new TabularDAO(entityManager);
    }

    public TabularDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public Tabular findByUUID(UUID uuid) {
        return entityManager.createNamedQuery("tabular.findbyUUID", Tabular.class).setParameter("uuid", uuid).getSingleResult();
    }

    @Override
    List<Tabular> findAll() {
        return entityManager.createNamedQuery("tabular.findAll", Tabular.class).getResultList();
    }

}
