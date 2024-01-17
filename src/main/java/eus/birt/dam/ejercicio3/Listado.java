package eus.birt.dam.ejercicio3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;

public class Listado {

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("ud4");
        EntityManager entityManager = factory.createEntityManager();

        EntityTransaction transaction = null;

        try {

            // comienza la transacción
            transaction = entityManager.getTransaction();
            transaction.begin();

            String hql = "SELECT CONCAT(s.firstName, ' ', s.lastName), u.name FROM Student s JOIN s.university u";
            Query query = entityManager.createQuery(hql);

            List<Object[]> resultList = query.getResultList();

            for (Object[] result : resultList) {
                String nombreCompleto = (String) result[0];
                String universidad = (String) result[1];
                System.out.println("Estudiante: " + nombreCompleto + ", Universidad: " + universidad);
            }

            transaction.commit();

        } catch (Exception e) {
			// Rollback en caso de excepción
            if (transaction != null && transaction.isActive()) {
                System.out.println("Realizando Rollback");
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
            if (factory != null) {
                factory.close();
            }
        }
    }
}