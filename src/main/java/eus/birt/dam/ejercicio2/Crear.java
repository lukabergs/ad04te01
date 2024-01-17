package eus.birt.dam.ejercicio2;

import java.time.LocalDate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import eus.birt.dam.ejercicio2.entidades.Student;
import eus.birt.dam.ejercicio2.entidades.Address;
import eus.birt.dam.ejercicio2.entidades.University;

public class Crear {

	/**
	 * 1. OneToMany bidireccional entre entidades Student y University
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

			// crea un objeto Student y University
			System.out.println("Creando nuevos objetos Student y University");
		
			Student student = createStudent();
			University university = createUniversity();
					
			university.getStudents().add(student);
			student.setUniversity(university); // bidireccional
			
			// guarda el objeto University
			System.out.println("Guardando la universidad y en cascada el estudiante");
			entityManager.persist(university);
			
			// Commit de la transacción
			transaction.commit();
			
			// prueba para acceder a la entidad source desde de la entidad target
			transaction.begin();
			Student dbStudent = (Student) entityManager.find(Student.class, university.getStudents().get(0).getId());
			System.out.println(dbStudent.getUniversity().getName());
			
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
	
	private static University createUniversity() {
		University tempUniversity = new University();
		Address uniAddress = new Address();
		
		tempUniversity.setName("EHU");
		uniAddress.setAddressLine1("Araba Kalea");
		uniAddress.setAddressLine2("5");
		uniAddress.setCity("Gasteiz");
		uniAddress.setZipCode("01155");
		tempUniversity.setAddress(uniAddress);
		return tempUniversity;		
	}
}