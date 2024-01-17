package eus.birt.dam.ejercicio3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import eus.birt.dam.ejercicio3.entidades.Student;
import eus.birt.dam.ejercicio3.entidades.Course;

public class Crear {

	/**
	 * 1. ManyToMany bidireccional entre entidades Student y Course
	 * Crea un nuevo curso y añade un alumno al curso 
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

			// crea un objeto Student y Course
			System.out.println("Creando un nuevo curso y añadiendo un alumno...");
			
			Student student = entityManager.find(Student.class, 7);
			Course course = createCourse();
						
			student.getCourses().add(course);
			course.getStudents().add(student); // Asociación bidireccional para mantener la coherencia en ambos lados
			
			// guarda el objeto Student y el curso
			System.out.println("Guardando el curso...");
						
			entityManager.persist(course);
			
			// Commit de la transacción
			transaction.commit();	
			
			// Inicia una nueva transacción y recupera el curso de la base de datos para verificar los estudiantes asociados.
			// Esta parte está comentada temporalmente para evitar operaciones adicionales de base de datos durante la demostración.
			// Si necesitas verificar que la relación ManyToMany se ha establecido correctamente, puedes descomentar estas líneas.
			// session.beginTransaction();
			// Course dbCourse= (Course) session.get(Course.class, course.getId());
			// System.out.println(dbCourse.getStudents().iterator().next().getLastName());
			
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
	
	private static Course createCourse() {
		Course tempCourse = new Course();
				
		tempCourse.setName("Bases de datos");
		tempCourse.setCredits(6);
		return tempCourse;		
	}
}