package eus.birt.dam.ejercicio1;

import java.time.LocalDate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import eus.birt.dam.ejercicio1.entidades.Student;
import eus.birt.dam.ejercicio1.entidades.Address;
import eus.birt.dam.ejercicio1.entidades.Tuition;

public class Crear {

	/**
	 * 1. OneToOne unidireccional entre entidades Student y Tuition (matrícula)
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
			System.out.println("Creando un nuevo objeto Student con su dirección y matrícula (tuition)");
			Student student = createStudent();
			Tuition tuition = new Tuition();
			tuition.setFee(4000.00);
			student.setTuition(tuition);
			
			// guarda el objeto Student
			System.out.println("Guardando el estudiante...");
		
			//guarda el Student y con CascadeType.ALL guarda también el Tuition
			entityManager.persist(student);
			
			// Commit de la transaccion		
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

	private static Student createStudent() {
		Student tempStudent = new Student();
		Address tempAddress = new Address();
		
		tempStudent.setFirstName("Iñaki");
		tempStudent.setLastName("Laspiur");
		tempStudent.setEmail("ilaspiur@birt.eus");
		tempStudent.getPhones().add("687123456");
		tempStudent.getPhones().add("699212345");
		tempStudent.setBirthdate(LocalDate.parse("1985-04-04"));
		tempAddress.setAddressLine1("Burdin kale 8");
		tempAddress.setAddressLine2("1A");
		tempAddress.setCity("Zarautz");
		tempAddress.setZipCode("20080");
		tempStudent.setAddress(tempAddress);
		return tempStudent;		
	}
}