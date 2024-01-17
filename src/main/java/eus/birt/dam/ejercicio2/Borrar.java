package eus.birt.dam.ejercicio2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import eus.birt.dam.ejercicio2.entidades.University;

public class Borrar {

	/**
	 * 4. OneToMany bidireccional entre entidades Student y University
	 * Borra una Universidad y sus estudiantes.
	 */
	public static void main(String[] args) {

		// crea factory y entityManager
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("ud4");
        EntityManager entityManager = factory.createEntityManager();

        EntityTransaction transaction = null;
		
        try {

            // comienza la transacción
            transaction = entityManager.getTransaction();
            transaction.begin();

			// crea un objeto Student
			System.out.println("Borrando una universidad sin borrar sus estudiantes");
			
			int university_id = 2;
			
			University tempUniversity = entityManager.find(University.class, university_id);
		
			// borra la universidad pero no el estudiante. "on delete set null" en BD
			entityManager.remove(tempUniversity);
			
			// Commit de la transacción
			transaction.commit();

			System.out.println("Hecho!");

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