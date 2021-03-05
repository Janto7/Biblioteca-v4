package org.iesalandalus.programacion.biblioteca.mvc.vista.texto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.AudioLibro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.LibroEscrito;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	private Consola() {
	}

	public static void mostrarMenu() {
		mostrarCabecera("Gestión de la Biblioteca del IES Al-Ándalus");
		for (Opcion opcion : Opcion.values()) {
			System.out.println("\n" + opcion);
		}
	}

	public static void mostrarCabecera(String mensaje) {
		System.out.printf("%n%s%n", mensaje);
		String formatoStr = "%0" + mensaje.length() + "d%n";
		System.out.print(String.format(formatoStr, 0).replace("0", "-"));
	}

	public static int elegirOpcion() {
		int ordinalOpcion;
		do {
			System.out.print("\nElige una opción: ");
			ordinalOpcion = Entrada.entero();
		} while (!Opcion.esOrdinalValido(ordinalOpcion));
		return ordinalOpcion;
	}

	public static Alumno leerAlumno() {
		System.out.printf("Introduce el nombre del alumno: ");
		String nombre = Entrada.cadena();
		System.out.print("Introduce el correo del alumno: ");
		String correo = Entrada.cadena();
		int curso = 0;
		while (curso < 1 || curso > 4) {
			System.out.print("Introduce el curso del alumno: [1.- PRIMERO, 2.-SEGUNDO, 3.-TERCERO, 4.-CUARTO] ");
			curso = Entrada.entero();
		}
		return new Alumno(nombre, correo, Curso.values()[curso - 1]);

	}

	public static Alumno leerAlumnoFicticio() {
		System.out.print("Introduce el correo del alumno: ");
		return Alumno.getAlumnoFicticio(Entrada.cadena());
	}

	public static Libro leerLibro() {

		System.out.print("\nIntroduce el titulo del libro: ");
		String titulo = Entrada.cadena();
		System.out.print("Introduce el autor del libro: ");
		String autor = Entrada.cadena();
		int tipoLibro = 0;

		while (tipoLibro < 1 || tipoLibro > 2) {

			System.out.print("Introduce el tipo de libro: [1.- Libro Escrito, 2.- Audio Libro] ");
			tipoLibro = Entrada.entero();
		}
		Libro libro = null;
		if (tipoLibro == 1) {
			System.out.print("Introduce el número de páginas: ");
			int numPaginas = Entrada.entero();
			libro = new LibroEscrito(titulo, autor, numPaginas);
		} else {
			System.out.print("Introduce la duración: ");
			int duracion = Entrada.entero();
			libro = new AudioLibro(titulo, autor, duracion);
		}
		return libro;
	}

	public static Libro leerLibroFicticio() {
		System.out.print("\nIntroduce el titulo del libro: ");
		String titulo = Entrada.cadena();
		System.out.print("Introduce el autor del libro: ");
		String autor = Entrada.cadena();
		System.out.print("Introduce el número de páginas: ");
		int numPaginas = Entrada.entero();
		return new LibroEscrito(titulo, autor, numPaginas);
	}

	public static Prestamo leerPrestamo() {
		Alumno alumno = leerAlumno();
		Libro libro = leerLibro();
		LocalDate fechaPrestamo = leerFecha("Introduce la fecha de préstamo: ");
		return new Prestamo(alumno, libro, fechaPrestamo);

	}

	public static Prestamo leerPrestamoFicticio() {
		Alumno alumno = leerAlumnoFicticio();
		Libro libro = leerLibroFicticio();
		return Prestamo.getPrestamoFicticio(alumno, libro);
	}

	public static LocalDate leerFecha(String mensaje) {
		LocalDate fecha = null;
		System.out.printf(mensaje);
		String fechaStr = Entrada.cadena();
		try {
			fecha = LocalDate.parse(fechaStr, Prestamo.FORMATO_FECHA);
		} catch (DateTimeParseException e) {
			System.out.println("ERROR: El formato de la fecha no es correcto.");
		}
		return fecha;
	}

}