package pe.edu.utp.proyectofinal.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class Validador {
    public static boolean validarConductor(String nombre, String dni){
        boolean valido = false;

        //Validar nombre
        // No se permiten nombres menores a 2 letras y mayores a 20 o que contengan números
        if (nombre.length() < 2 || nombre.length() > 20) throw new IllegalArgumentException("El nombre debe tener entre 2 y 20 caracteres.");
        Pattern patternNombre = Pattern.compile("^[a-zA-Z]+$");
        boolean nombreValido = patternNombre.matcher(nombre).matches();
        if (!nombreValido) throw new IllegalArgumentException("El nombre no puede contener numeros.");

        //Validad dni
        //No debe contener letras y debe tener exactamente 8 caracteres
        if (dni.length() != 8 || !dni.matches("\\d+")) throw new IllegalArgumentException("El DNI contiene 8 digitos numericos.");

        valido = true;
        return valido;
    }

    public static boolean validarServicio(String fechaHoraIngreso, String fechaHoraSalida, String montoCobro, String comentario, String lavado){
        boolean valido = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //Validar fechaHoraIngreso
        // No se permiten fechas pasadas
        try {
            LocalDateTime fechaIngreso = LocalDateTime.parse(fechaHoraIngreso, formatter);
            if (fechaIngreso.isBefore(LocalDateTime.now())) throw new IllegalArgumentException("La fecha y hora de ingreso no pueden ser anteriores a la actual.");
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha y hora de ingreso inválido.");
        }

        //Validad fechaHoraSalida
        // No se permiten fechas pasadas
        try {
            LocalDateTime fechaSalida = LocalDateTime.parse(fechaHoraSalida, formatter);
            if (fechaSalida.isBefore(LocalDateTime.now())) throw new IllegalArgumentException("La fecha y hora de salida no pueden ser anteriores a la actual.");
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha y hora de salida inválido.");
        }


        //Validad montoCobro
        //No se permiten montos negativos
        try {
            float monto = Float.parseFloat(montoCobro);
            if (monto < 0) throw new IllegalArgumentException("El costo de servicio no puede ser negativo.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El costo de servicio debe ser un valor numérico.");
        }


        valido = true;
        return valido;

    }

    public static boolean validarVehiculo(String placa){
        boolean valido = false;

        //Validar placa
        // La placa solo contiene 7 caracteres entre letras y numeros
        if (placa.length() != 7) throw new IllegalArgumentException("La placa solo tiene 7 caracteres");

        valido = true;
        return valido;
    }

    public static boolean validarUsuario(String email){
        boolean valido = false;

        //Validar email
        //Despues del nombre de usuario debe contener "@gmail.com"
        String regex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        if (!email.matches(regex)) throw new IllegalArgumentException("El correo debe terminar con '@gmail.com'");

        valido = true;
        return valido;
    }
}
