package pe.edu.utp.proyectofinal.service;

import pe.edu.utp.proyectofinal.util.DataAccessMariaDB;
import pe.edu.utp.proyectofinal.viewforms.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.naming.NamingException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

//@ManagedBean
//@RequestScoped
public class Auth {
    private static final Logger logger = Logger.getLogger("pe.edu.utp.ProyectoFinal.formview");

    public static boolean isValidUser(String email, String pwd)
            throws SQLException, NamingException, IOException {
        String strSQL = String.format("CALL pr_checkUser('%s','%s')",email, md5(pwd));
        logger.info(strSQL);
        Connection cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE,
                AppConfig.getDatasource());
        ResultSet rst = cnn.createStatement().executeQuery(strSQL);
        String res = (rst.next()) ? rst.getString("login") : "no_data";
        cnn.close();
        return !res.equals("no_data");
    }

    public static String md5(String data) throws IOException {
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            MessageDigest msg = (MessageDigest) md.clone();
            msg.update(data.getBytes());
            return byteArrayToHex(msg.digest());
        } catch (CloneNotSupportedException | NoSuchAlgorithmException e) {
            logger.info(e.getMessage());
            return data;
        }
    }

    /*
    * Link: https://stackoverflow.com/questions/9655181/java-convert-a-byte-array-to-a-hex-string
    * Nota: Metodo altetnativo para JDK17, pero se debe tener cuidado con tener este entorno activado
    * HexFormat hex = HexFormat.of();
    * hex.formatHex(someByteArray)
    * */
    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

//    //Utiliza el procedimiento almacenado GuardarInformacionDashboard para crear un procedimiento para agregar la informacio a la base de datos//
//    public static void saveDashboardInfo(String tipoVehiculo, String numeroPlaca, String fechaIngreso, String fechaSalida, String nombreConductor, String dniConductor, double montoCobrar, String observaciones, boolean lavado) throws SQLException, NamingException, IOException {
//        String strSQL = String.format("CALL GuardarInformacionDashboard('%s','%s','%s','%s','%s','%s',%f,'%s',%b)", tipoVehiculo, numeroPlaca, fechaIngreso, fechaSalida, nombreConductor, dniConductor, montoCobrar, observaciones, lavado);
//        logger.info(strSQL);
//        Connection cnn = null;
//        try {
//            cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, AppConfig.getDatasource());
//            cnn.createStatement().execute(strSQL);
//        } catch (SQLException e) {
//            logger.info("SQL Error: " + e.getMessage());
//            throw new SQLException("Error al ejecutar la consulta SQL: " + e.getMessage(), e);
//        } catch (NamingException e) {
//            logger.info("Naming Error: " + e.getMessage());
//            throw new NamingException("Error al obtener el recurso de nombre: " + e.getMessage());
//        } finally {
//            if (cnn != null) {
//                try {
//                    cnn.close();
//                } catch (SQLException e) {
//                    logger.info("Error al cerrar la conexión: " + e.getMessage());
//                }
//            }
//        }
//    }

    // Insertar Conductor
    public static void insertarConductor(String nombre, String dni) throws IOException, SQLException, NamingException {
        String strSQL = String.format("CALL InsertarConductor('%s', '%s')", nombre, dni);
        logger.info(strSQL);
        Connection cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, AppConfig.getDatasource());
        cnn.createStatement().executeUpdate(strSQL);
        cnn.close();
    }

//    // Seleccionar Conductor
//    public static Conductor seleccionarConductor(int idConductor) throws IOException, SQLException, NamingException {
//        String strSQL = String.format("CALL SeleccionarConductor(%d)", idConductor);
//        logger.info(strSQL);
//        Connection cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, AppConfig.getDatasource());
//        ResultSet rst = cnn.createStatement().executeQuery(strSQL);
//        Conductor conductor = null;
//        if (rst.next()) {
//            String nombre = rst.getString("nombre");
//            String dni = rst.getString("dni");
//            conductor = new Conductor(idConductor, nombre, dni);
//        }
//        cnn.close();
//        return conductor;
//    }

    // Insertar Vehículo
    public static void insertarVehiculo(String placa, String tipo, int idConductor) throws IOException, SQLException, NamingException {
        String strSQL = String.format("CALL InsertarVehiculo('%s', '%s', %d)", placa, tipo, idConductor);
        logger.info(strSQL);
        Connection cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, AppConfig.getDatasource());
        cnn.createStatement().executeUpdate(strSQL);
        cnn.close();
    }

    // Seleccionar Vehículo
    public static Vehiculo seleccionarVehiculo(int idVehiculo) throws IOException, SQLException, NamingException {
        String strSQL = String.format("CALL SeleccionarVehiculo(%d)", idVehiculo);
        logger.info(strSQL);
        Connection cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, AppConfig.getDatasource());
        ResultSet rst = cnn.createStatement().executeQuery(strSQL);
        Vehiculo vehiculo = null;
        if (rst.next()) {
            String placa = rst.getString("placa");
            String tipo = rst.getString("tipo");
            int idConductor = rst.getInt("idConductor");
            vehiculo = new Vehiculo(idVehiculo, placa, tipo, idConductor);
        }
        cnn.close();
        return vehiculo;
    }

    // Insertar Servicio
    public static void insertarServicio(Timestamp fechaIngreso, Timestamp fechaSalida, double montoCobro, String comentario, int idVehiculo, Boolean lavado) throws IOException, SQLException, NamingException {
        String strSQL = String.format("CALL InsertarServicio('%s', '%s', %.2f, '%s', %d, %b)",
                fechaIngreso.toString(), fechaSalida.toString(), montoCobro, comentario, idVehiculo, lavado);
        logger.info(strSQL);
        Connection cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, AppConfig.getDatasource());
        cnn.createStatement().executeUpdate(strSQL);
        cnn.close();
    }

//    // Seleccionar Servicio
//    public static Servicio seleccionarServicio(int idServicio) throws IOException, SQLException, NamingException {
//        String strSQL = String.format("CALL SeleccionarServicio(%d)", idServicio);
//        logger.info(strSQL);
//        Connection cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, AppConfig.getDatasource());
//        ResultSet rst = cnn.createStatement().executeQuery(strSQL);
//        Servicio servicio = null;
//        if (rst.next()) {
//            Timestamp fechaIngreso = rst.getTimestamp("fechaHoraIngreso");
//            Timestamp fechaSalida = rst.getTimestamp("fechaHoraSalida");
//            double montoCobro = rst.getDouble("montoCobro");
//            String comentario = rst.getString("comentario");
//
//            servicio = new Servicio.Builder(fechaIngreso, fechaSalida, montoCobro, comentario)
//                    .build();
//        }
//        cnn.close();
//        return servicio;
//    }

    // Obtener reporte de servicios según el tipo de vehículo
    public static List<Map<String, Object>> obtenerReporteServicios(String tipoVehiculo) throws IOException, SQLException, NamingException {
        String strSQL = String.format("CALL ObtenerInformacionCompleta('%s')", tipoVehiculo);
        logger.info(strSQL);
        Connection cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, AppConfig.getDatasource());
        ResultSet rst = cnn.createStatement().executeQuery(strSQL);
        List<Map<String, Object>> reporteList = new ArrayList<>();

        while (rst.next()) {
            // Instancias de Conductor y Vehiculo
            Conductor conductor = new Conductor();
            Vehiculo vehiculo = new Vehiculo();

            conductor.setNombre(rst.getString("nombre"));
            conductor.setDni(rst.getString("dni"));

            vehiculo.setTipo(rst.getString("tipo"));
            vehiculo.setPlaca(rst.getString("placa"));

            // Obtener el valor de lavado
            boolean lavado = rst.getBoolean("lavado");

            // Construcción del objeto Servicio utilizando el patrón Builder
            Servicio.Builder servicioBuilder = new Servicio.Builder(
                    rst.getTimestamp("fechaHoraIngreso"),
                    rst.getTimestamp("fechaHoraSalida"),
                    rst.getDouble("montoCobro"),
                    rst.getString("comentario")
            );

            // Si el servicio incluye lavado, lo configuramos
            if (lavado) {
                servicioBuilder.withLavado();
            }

            // Crear el objeto Servicio
            Servicio servicio = servicioBuilder.build();

            // Crear el mapa de resultado y agregarlo a la lista
            Map<String, Object> registro = new HashMap<>();
            registro.put("conductor", conductor);
            registro.put("vehiculo", vehiculo);
            registro.put("servicio", servicio);

            reporteList.add(registro);
        }

        cnn.close();
        return reporteList;
    }


    public static int obtenerIdConductor(String dni) throws IOException, SQLException, NamingException {
        String strSQL = "{CALL ObtenerIdConductor(?, ?)}";
        logger.info(strSQL);

        Connection cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, AppConfig.getDatasource());

        try (CallableStatement stmt = cnn.prepareCall(strSQL)) {
            // Establecer el parámetro de entrada (dniConductor)
            stmt.setString(1, dni);
            // Registrar el parámetro de salida (idConductor)
            stmt.registerOutParameter(2, Types.INTEGER);
            // Ejecutar el procedimiento almacenado
            stmt.execute();
            return stmt.getInt(2);
        }
    }

    public static int obtenerIdVehiculo(String placa) throws IOException, SQLException, NamingException {
        String strSQL = "{CALL ObtenerIdVehiculo(?, ?)}";
        logger.info(strSQL);
        Connection cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, AppConfig.getDatasource());
        //ResultSet rst = cnn.createStatement().executeQuery(strSQL);
        try (CallableStatement stmt = cnn.prepareCall(strSQL)) {
            stmt.setString(1, placa);
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.execute();
            return stmt.getInt(2);
        }
    }

    public static List<Trabajador> mostrarTrabajador() throws IOException, SQLException, NamingException {
        String strSQL = "CALL mostrarTrabajador()";
        logger.info(strSQL);
        Connection cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, AppConfig.getDatasource());
        ResultSet rst = cnn.createStatement().executeQuery(strSQL);
        List<Trabajador> trabajadores = new ArrayList<>();

        while (rst.next()) {
            Trabajador trabajador = new Trabajador();
            trabajador.setNombres(rst.getString("nombres"));
            trabajador.setApellidos(rst.getString("apellidos"));
            trabajador.setEdad(rst.getInt("edad"));
            trabajador.setDni(rst.getString("dni"));
            trabajador.setTelefono(rst.getString("telefono"));
            trabajador.setEmail(rst.getString("email"));
            trabajador.setTurno(rst.getString("turno"));
            trabajadores.add(trabajador);
        }

        cnn.close();
        return trabajadores;
    }


    public static void registrarAsistencia(String nombres, String apellidos, String estado) throws SQLException, NamingException {
        if (nombres == null || nombres.isEmpty() || apellidos == null || apellidos.isEmpty() || estado == null || estado.isEmpty()) {
            logger.log(Level.SEVERE, "Invalid input parameters: nombres={0}, apellidos={1}, estado={2}", new Object[]{nombres, apellidos, estado});
            return;
        }

        Connection cnn = null;
        CallableStatement callableStatement = null;

        try {
            cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, AppConfig.getDatasource());
            String sql = "{CALL registrarAsistencia(?, ?, ?)}";
            callableStatement = cnn.prepareCall(sql);
            callableStatement.setString(1, nombres);
            callableStatement.setString(2, apellidos);
            callableStatement.setString(3, estado);

            callableStatement.executeUpdate();
            logger.log(Level.INFO, "Attendance registered successfully for {0} {1} with estado={2}", new Object[]{nombres, apellidos, estado});
        } catch (SQLException | NamingException e) {
            logger.log(Level.SEVERE, "Error registering attendance for {0} {1} with estado={2}. Exception: {3}", new Object[]{nombres, apellidos, estado, e.getMessage()});
            throw e;
        } finally {
            if (callableStatement != null) {
                callableStatement.close();
            }
            if (cnn != null) {
                cnn.close();
            }
        }
    }

    public static DatosServicios obtenerDatosServicios(String nombreConductor, String tipoVehiculo) throws SQLException, NamingException {
        if (nombreConductor == null || nombreConductor.isEmpty()) {
            logger.log(Level.SEVERE, "Invalid input parameter: nombreConductor={0}", nombreConductor);
            return null;
        }

        Connection cnn = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, AppConfig.getDatasource());
            String sql = "{CALL obtenerDatosServicios(?)}";
            callableStatement = cnn.prepareCall(sql);
            callableStatement.setString(1, nombreConductor);

            resultSet = callableStatement.executeQuery();

            if (resultSet.next()) {
                int cantidadServicios = resultSet.getInt("cantidadServicios");
                int mesesRegistrando = resultSet.getInt("mesesRegistrando");

                // Lógica del descuento
                double descuento = calcularDescuento(cantidadServicios, mesesRegistrando, tipoVehiculo);

                logger.log(Level.INFO, "Conductor: {0}, Cantidad de Servicios: {1}, Meses Registrando: {2}, Descuento: {3}",
                        new Object[]{nombreConductor, cantidadServicios, mesesRegistrando, descuento});

                return new DatosServicios(cantidadServicios, mesesRegistrando, descuento);
            }
        } catch (SQLException | NamingException e) {
            logger.log(Level.SEVERE, "Error retrieving service data for conductor {0}. Exception: {1}",
                    new Object[]{nombreConductor, e.getMessage()});
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (callableStatement != null) {
                callableStatement.close();
            }
            if (cnn != null) {
                cnn.close();
            }
        }
        return null;
    }

    private static double calcularDescuento(int cantidadServicios, int mesesRegistrando, String tipoVehiculo) {
        double descuentoBase = cantidadServicios * 0.5 + mesesRegistrando * 0.2; // Ejemplo simple
        switch (tipoVehiculo.toLowerCase()) {
            case "auto":
                return Math.min(descuentoBase, 20);
            case "moto":
                return Math.min(descuentoBase, 15);
            case "camion":
                return Math.min(descuentoBase, 25);
            default:
                return 0; // Sin descuento para tipos desconocidos
        }
    }


        // Método para editar un trabajador utilizando el procedimiento almacenado
        public static void editarTrabajador(String oldNombres, String oldApellidos, String newNombres, String newApellidos, int edad, String dni, String telefono, String email, String turno) throws SQLException, NamingException {
            Connection cnn = null;
            CallableStatement callableStatement = null;

            try {
                cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, AppConfig.getDatasource());
                String sql = "{CALL editarTrabajador(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
                callableStatement = cnn.prepareCall(sql);
                callableStatement.setString(1, oldNombres);
                callableStatement.setString(2, oldApellidos);
                callableStatement.setString(3, newNombres);
                callableStatement.setString(4, newApellidos);
                callableStatement.setInt(5, edad);
                callableStatement.setString(6, dni);
                callableStatement.setString(7, telefono);
                callableStatement.setString(8, email);
                callableStatement.setString(9, turno);

                callableStatement.executeUpdate();
            } finally {
                if (callableStatement != null) {
                    callableStatement.close();
                }
                if (cnn != null) {
                    cnn.close();
                }
            }
        }

    public static void eliminarTrabajador(String nombres, String apellidos) throws SQLException, NamingException {
        if (nombres == null || nombres.isEmpty() || apellidos == null || apellidos.isEmpty()) {
            logger.log(Level.SEVERE, "Invalid input parameters: nombres={0}, apellidos={1}", new Object[]{nombres, apellidos});
            return;
        }

        Connection cnn = null;
        CallableStatement callableStatement = null;

        try {
            cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, AppConfig.getDatasource());
            String sql = "{CALL eliminarTrabajador(?, ?)}";
            callableStatement = cnn.prepareCall(sql);
            callableStatement.setString(1, nombres);
            callableStatement.setString(2, apellidos);

            callableStatement.executeUpdate();
            logger.log(Level.INFO, "Worker {0} {1} deleted successfully", new Object[]{nombres, apellidos});
        } catch (SQLException | NamingException e) {
            logger.log(Level.SEVERE, "Error deleting worker {0} {1}. Exception: {2}", new Object[]{nombres, apellidos, e.getMessage()});
            throw e;
        } finally {
            if (callableStatement != null) {
                callableStatement.close();
            }
            if (cnn != null) {
                cnn.close();
            }
        }
    }
}
