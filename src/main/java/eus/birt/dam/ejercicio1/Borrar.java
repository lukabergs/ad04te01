package eus.birt.dam.ejercicio1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import eus.birt.dam.ejercicio1.entidades.Student;

public class Borrar {

	/**
	 * 4. OneToOne unidireccional entre entidades Student y Tuition (matrícula).
	 * Borra un Tuition y su Student asociado
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
		
			System.out.println("Borrando un nuevo Student y en cascada su Tuition asociado");
			
			int student_id = 6;
			
			Student tempStudent = entityManager.find(Student.class, student_id);
		
			// borra el Student y con CascadeType.ALL termina borrando su Tuition
			entityManager.remove(tempStudent);
			
			// hace commit de la transaccion
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