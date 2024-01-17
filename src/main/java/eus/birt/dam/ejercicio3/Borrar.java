package eus.birt.dam.ejercicio3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import eus.birt.dam.ejercicio3.entidades.Student;


public class Borrar {

	/**
	 * 4. ManyToMany bidireccional entre entidades Student y Course
	 * Borra una Student pero no el curso
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

			// Borra un objeto Student
			System.out.println("Borrando un objeto Student ");
			
			int student_id = 7;
			
			Student tempStudent = entityManager.find(Student.class, student_id);
		
			// borra el objecto Student pero sin CascadeType.REMOVE no elimina el curso
			entityManager.remove(tempStudent);
			
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