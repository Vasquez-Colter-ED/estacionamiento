-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.6.16-MariaDB - FreeBSD Ports
-- SO del servidor:              FreeBSD13.2
-- HeidiSQL Versión:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para estacionamiento
CREATE DATABASE IF NOT EXISTS `estacionamiento` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci */;
USE `estacionamiento`;

-- Volcando estructura para procedimiento estacionamiento.AddUsuario
DELIMITER //
CREATE PROCEDURE `AddUsuario`(
    IN p_login VARCHAR(20),
    IN p_fullname VARCHAR(150),
    IN p_email VARCHAR(150),
    IN p_pwd CHAR(32)
)
BEGIN
    REPLACE INTO Usuario (login, fullname, email, pwd)
    VALUES (p_login, p_fullname, p_email, p_pwd);
END//
DELIMITER ;

-- Volcando estructura para tabla estacionamiento.Asistencia
CREATE TABLE IF NOT EXISTS `Asistencia` (
  `idAsistencia` int(11) NOT NULL AUTO_INCREMENT,
  `idTrabajador` int(11) NOT NULL,
  `turno` varchar(10) NOT NULL DEFAULT 'Asistio',
  `horario` datetime NOT NULL,
  `estado` varchar(8) NOT NULL,
  PRIMARY KEY (`idAsistencia`),
  KEY `Asistencia_Trabajador` (`idTrabajador`),
  CONSTRAINT `Asistencia_Trabajador` FOREIGN KEY (`idTrabajador`) REFERENCES `Trabajador` (`idTrabajador`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla estacionamiento.Asistencia: ~2 rows (aproximadamente)
INSERT INTO `Asistencia` (`idAsistencia`, `idTrabajador`, `turno`, `horario`, `estado`) VALUES
	(1, 2, 'Mañana', '2024-12-06 10:46:10', 'trabajo'),
	(2, 1, 'mañana', '2024-12-06 10:47:57', 'trabajo');

-- Volcando estructura para tabla estacionamiento.Conductor
CREATE TABLE IF NOT EXISTS `Conductor` (
  `idConductor` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) NOT NULL,
  `dni` char(8) NOT NULL,
  PRIMARY KEY (`idConductor`),
  CONSTRAINT `chkLongitudNombre` CHECK (octet_length(`nombre`) between 2 and 20)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla estacionamiento.Conductor: ~25 rows (aproximadamente)
INSERT INTO `Conductor` (`idConductor`, `nombre`, `dni`) VALUES
	(1, 'Michael', '12345678'),
	(2, 'Segundo', '60827127'),
	(3, 'Juan', '92835921'),
	(4, 'Manuel', '82174971'),
	(5, 'Luis', '73984724'),
	(6, 'juan', '41424344'),
	(7, 'Auto', '12345678'),
	(8, 'Distinto', '12345678'),
	(9, 'Distinto', '12345678'),
	(10, 'Distinto', '12345678'),
	(11, 'Distinto', '73947136'),
	(12, 'Antonio', '72483234'),
	(13, 'NuevoConductor', '98384735'),
	(14, 'Fernando', '40414243'),
	(15, 'Miguel', '82394132'),
	(16, 'Maria', '82345924'),
	(17, 'NuevoDiez', '12344321'),
	(18, 'NuevoOnce', '98766789'),
	(19, 'NuevoDoce', '39483948'),
	(20, 'JUANVEINTE', '82394719'),
	(21, 'Lolo', '92833840'),
	(22, 'NUEVOCATORCE', '18283453'),
	(23, 'NUEVOQUINCE', '74247249'),
	(24, 'NuevoSeis', '29425293'),
	(25, 'ksalfnlaf', '13241241'),
	(26, 'AIJSOFJ', '39898223'),
	(27, 'SLAF', '42902352'),
	(28, 'Edgar', '83942451'),
	(29, 'QR', '82712355');

-- Volcando estructura para procedimiento estacionamiento.editarTrabajador
DELIMITER //
CREATE PROCEDURE `editarTrabajador`(
    IN p_oldNombres VARCHAR(50),
    IN p_oldApellidos VARCHAR(50),
    IN p_newNombres VARCHAR(50),
    IN p_newApellidos VARCHAR(50),
    IN p_edad INT,
    IN p_dni CHAR(8),
    IN p_telefono CHAR(9),
    IN p_email VARCHAR(100),
    IN p_turno VARCHAR(6)
)
BEGIN
UPDATE Trabajador
SET
    nombres = p_newNombres,
    apellidos = p_newApellidos,
    edad = p_edad,
    dni = p_dni,
    telefono = p_telefono,
    email = p_email,
    turno = p_turno
WHERE dni = p_dni;
END//
DELIMITER ;

-- Volcando estructura para procedimiento estacionamiento.eliminarTrabajador
DELIMITER //
CREATE PROCEDURE `eliminarTrabajador`(
    IN p_nombres VARCHAR(50),
    IN p_apellidos VARCHAR(50)
)
BEGIN
    DECLARE v_idTrabajador INT;

    -- Obtener el id del trabajador
    SELECT idTrabajador INTO v_idTrabajador
    FROM Trabajador
    WHERE nombres = p_nombres AND apellidos = p_apellidos
    LIMIT 1;

    -- Eliminar el trabajador (las asistencias se eliminarán en cascada)
    DELETE FROM Trabajador
    WHERE idTrabajador = v_idTrabajador;
END//
DELIMITER ;

-- Volcando estructura para procedimiento estacionamiento.GuardarInformacionDashboard
DELIMITER //
CREATE PROCEDURE `GuardarInformacionDashboard`(
    IN ic_nombre varchar(20),
    IN ic_dni char(8),
    IN iv_placa varchar(10),
    IN iv_tipo varchar(15),
    IN is_fechaHoraIngreso datetime,
    IN is_fechaHoraSalida datetime,
    IN is_montoCobro decimal(6, 2),
    IN is_comentario TEXT,
    IN is_lavado BOOLEAN
)
BEGIN
    DECLARE idConductor INT;
    DECLARE idVehiculo INT;

    -- Insertar o actualizar conductor
    INSERT INTO Conductor (nombre, dni) VALUES (ic_nombre, ic_dni)
    ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);
    SELECT idConductor INTO idConductor FROM Conductor WHERE dni = ic_dni LIMIT 1;

    -- Insertar o actualizar vehiculo
    INSERT INTO Vehiculo (placa, tipo, idConductor) VALUES (iv_placa, iv_tipo, idConductor)
    ON DUPLICATE KEY UPDATE tipo = VALUES(tipo), idConductor = VALUES(idConductor);
    SELECT idVehiculo INTO idVehiculo FROM Vehiculo WHERE placa = iv_placa LIMIT 1;

    -- Insertar servicio
    INSERT INTO Servicio (fechaHoraIngreso, fechaHoraSalida, montoCobro, comentario, idVehiculo, lavado)
    VALUES (is_fechaHoraIngreso, is_fechaHoraSalida, is_montoCobro, is_comentario, idVehiculo, is_lavado)
    ON DUPLICATE KEY UPDATE fechaHoraIngreso = VALUES(fechaHoraIngreso), fechaHoraSalida = VALUES(fechaHoraSalida), montoCobro = VALUES(montoCobro), comentario = VALUES(comentario), lavado = VALUES(lavado);
END//
DELIMITER ;

-- Volcando estructura para procedimiento estacionamiento.InsertarConductor
DELIMITER //
CREATE PROCEDURE `InsertarConductor`(
    IN ic_nombre varchar(20),
    IN ic_dni char(8)
)
BEGIN
    REPLACE INTO Conductor (nombre, dni) VALUES (ic_nombre, ic_dni);
END//
DELIMITER ;

-- Volcando estructura para procedimiento estacionamiento.InsertarServicio
DELIMITER //
CREATE PROCEDURE `InsertarServicio`(
    IN is_fechaHoraIngreso datetime,
    IN is_fechaHoraSalida datetime,
    IN is_montoCobro decimal(6, 2),
    IN is_comentario TEXT,
    IN is_idVehiculo int,
    IN is_lavado BOOLEAN
)
BEGIN
    REPLACE INTO Servicio (fechaHoraIngreso, fechaHoraSalida, montoCobro, comentario, idVehiculo, lavado)
    VALUES (is_fechaHoraIngreso, is_fechaHoraSalida, is_montoCobro, is_comentario, is_idVehiculo, is_lavado);
END//
DELIMITER ;

-- Volcando estructura para procedimiento estacionamiento.InsertarVehiculo
DELIMITER //
CREATE PROCEDURE `InsertarVehiculo`(
    IN iv_placa varchar(10),
    IN iv_tipo varchar(15),
    IN iv_idConductor int
)
BEGIN
    REPLACE INTO Vehiculo (placa, tipo, idConductor) VALUES (iv_placa, iv_tipo, iv_idConductor);
END//
DELIMITER ;

-- Volcando estructura para procedimiento estacionamiento.mostrarTrabajador
DELIMITER //
CREATE PROCEDURE `mostrarTrabajador`()
BEGIN
    SELECT nombres, apellidos, edad, dni, telefono, email, turno FROM Trabajador;
END//
DELIMITER ;

-- Volcando estructura para procedimiento estacionamiento.obtenerDatosServicios
DELIMITER //
CREATE PROCEDURE `obtenerDatosServicios`(
    IN p_nombreConductor VARCHAR(50)
)
BEGIN
    DECLARE v_idConductor INT;
    DECLARE p_cantidadServicios INT;
    DECLARE p_mesesRegistrando INT;

    -- Obtener el id del conductor
    SELECT idConductor INTO v_idConductor
    FROM Conductor
    WHERE nombre = p_nombreConductor
    LIMIT 1;

    -- Obtener la cantidad de servicios registrados
    SELECT COUNT(*) INTO p_cantidadServicios
    FROM Servicio
    WHERE idVehiculo IN (
        SELECT idVehiculo
        FROM Vehiculo
        WHERE idConductor = v_idConductor
    );

    -- Obtener la cantidad de meses durante los cuales se han registrado servicios
    SELECT TIMESTAMPDIFF(MONTH, MIN(fechaHoraIngreso), MAX(fechaHoraIngreso)) + 1 INTO p_mesesRegistrando
    FROM Servicio
    WHERE idVehiculo IN (
        SELECT idVehiculo
        FROM Vehiculo
        WHERE idConductor = v_idConductor
    );

    -- Mostrar los resultados
    SELECT p_cantidadServicios AS cantidadServicios, p_mesesRegistrando AS mesesRegistrando;
END//
DELIMITER ;

-- Volcando estructura para procedimiento estacionamiento.ObtenerIdConductor
DELIMITER //
CREATE PROCEDURE `ObtenerIdConductor`(
    IN dni CHAR(8),
    OUT idConductor INT
)
BEGIN
    SELECT c.idConductor INTO idConductor
    FROM Conductor c
    WHERE c.dni = dni
    LIMIT 1;
END//
DELIMITER ;

-- Volcando estructura para procedimiento estacionamiento.ObtenerIdVehiculo
DELIMITER //
CREATE PROCEDURE `ObtenerIdVehiculo`(
    IN placa CHAR(7),
    OUT idVehiculo INT
)
BEGIN
    SELECT v.idVehiculo INTO idVehiculo
    FROM Vehiculo v
    WHERE v.placa = placa
    LIMIT 1;
END//
DELIMITER ;

-- Volcando estructura para procedimiento estacionamiento.ObtenerInformacionCompleta
DELIMITER //
CREATE PROCEDURE `ObtenerInformacionCompleta`(
    IN oic_tipoVehiculo varchar(15)
)
BEGIN
    SELECT c.nombre, c.dni, v.tipo, v.placa, s.fechaHoraIngreso, s.fechaHoraSalida, s.montoCobro, s.comentario, s.lavado
    FROM Conductor c
    JOIN Vehiculo v ON c.idConductor = v.idConductor
    JOIN Servicio s ON v.idVehiculo = s.idVehiculo
    WHERE v.tipo = oic_tipoVehiculo;
END//
DELIMITER ;

-- Volcando estructura para procedimiento estacionamiento.pr_checkUser
DELIMITER //
CREATE PROCEDURE `pr_checkUser`(
    IN pEmail char(150),
    IN pClave char(32)
)
BEGIN
    SELECT login, email, fullname FROM Usuario WHERE email = pEmail AND pwd = pClave LIMIT 1;
END//
DELIMITER ;

-- Volcando estructura para procedimiento estacionamiento.registrarAsistencia
DELIMITER //
CREATE PROCEDURE `registrarAsistencia`(
    IN p_nombres VARCHAR(50),
    IN p_apellidos VARCHAR(50),
    IN p_estado VARCHAR(8)
)
BEGIN
    DECLARE v_idTrabajador INT;
    DECLARE v_turno VARCHAR(6);

    SELECT idTrabajador, turno INTO v_idTrabajador, v_turno
    FROM Trabajador
    WHERE nombres = p_nombres AND apellidos = p_apellidos;

    INSERT INTO Asistencia (idTrabajador, horario, estado, turno)
    VALUES (v_idTrabajador, NOW(), p_estado, v_turno);
END//
DELIMITER ;

-- Volcando estructura para procedimiento estacionamiento.SeleccionarConductor
DELIMITER //
CREATE PROCEDURE `SeleccionarConductor`(
    IN sc_idConductor int
)
BEGIN
    SELECT * FROM Conductor WHERE idConductor = sc_idConductor;
END//
DELIMITER ;

-- Volcando estructura para procedimiento estacionamiento.SeleccionarServicio
DELIMITER //
CREATE PROCEDURE `SeleccionarServicio`(
    IN ss_idServicio int
)
BEGIN
    SELECT * FROM Servicio WHERE idServicio = ss_idServicio;
END//
DELIMITER ;

-- Volcando estructura para procedimiento estacionamiento.SeleccionarVehiculo
DELIMITER //
CREATE PROCEDURE `SeleccionarVehiculo`(
    IN sv_idVehiculo int
)
BEGIN
    SELECT * FROM Vehiculo WHERE idVehiculo = sv_idVehiculo;
END//
DELIMITER ;

-- Volcando estructura para tabla estacionamiento.Servicio
CREATE TABLE IF NOT EXISTS `Servicio` (
  `idServicio` int(11) NOT NULL AUTO_INCREMENT,
  `fechaHoraIngreso` datetime NOT NULL,
  `fechaHoraSalida` datetime NOT NULL,
  `montoCobro` decimal(6,2) NOT NULL,
  `comentario` text NOT NULL DEFAULT 'No hay comentarios',
  `idVehiculo` int(11) NOT NULL,
  `lavado` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`idServicio`),
  KEY `Servicio_Vehiculo` (`idVehiculo`),
  CONSTRAINT `Servicio_Vehiculo` FOREIGN KEY (`idVehiculo`) REFERENCES `Vehiculo` (`idVehiculo`),
  CONSTRAINT `chkMontoCobro` CHECK (`montoCobro` >= 0)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla estacionamiento.Servicio: ~22 rows (aproximadamente)
INSERT INTO `Servicio` (`idServicio`, `fechaHoraIngreso`, `fechaHoraSalida`, `montoCobro`, `comentario`, `idVehiculo`, `lavado`) VALUES
	(1, '2024-10-18 09:47:00', '2024-10-18 15:41:00', 45.00, 'Ninguna', 1, 0),
	(2, '2024-10-18 09:48:00', '2024-10-18 15:42:00', 65.00, 'Ninguna', 1, 0),
	(3, '2024-10-18 09:51:00', '2024-10-18 15:45:00', 83.00, 'Ninguna', 3, 0),
	(4, '2024-10-18 09:52:00', '2024-10-18 15:46:00', 54.00, 'Ninguna', 4, 0),
	(5, '2024-10-18 10:20:00', '2024-10-18 16:14:00', 74.00, 'Ninguna', 5, 0),
	(6, '2024-10-18 10:44:00', '2024-10-18 11:39:00', 10.00, 'ninguna', 6, 0),
	(7, '2024-12-06 10:20:20', '2024-12-06 13:50:20', 10.90, 'Ninguna', 7, 0),
	(8, '2024-12-06 10:20:20', '2024-12-06 13:50:20', 10.90, 'Ninguna', 8, 0),
	(9, '2024-12-06 11:20:20', '2024-12-06 13:50:20', 192.34, 'ñññññ', 9, 0),
	(10, '2024-12-06 11:20:20', '2024-12-06 13:50:20', 10.20, 'Ninguna', 10, 0),
	(11, '2024-12-08 00:02:56', '2024-12-08 04:02:56', 10.90, 'Ninguna', 11, 1),
	(12, '2024-12-08 00:43:42', '2024-12-08 04:43:42', 40.00, 'Por ahora ninguna', 12, 1),
	(13, '2024-12-08 10:00:12', '2024-12-08 13:00:12', 6.00, 'Ninguna', 13, 1),
	(14, '2024-12-08 10:02:33', '2024-12-08 12:02:33', 6.00, 'NingunaOnce', 14, 1),
	(15, '2024-12-08 10:12:14', '2024-12-08 18:12:14', 80.00, 'NingunaDoce', 15, 1),
	(16, '2024-12-08 10:12:38', '2024-12-08 18:12:38', 80.00, 'NingunaDoce', 15, 1),
	(17, '2024-12-08 10:32:00', '2024-12-08 12:32:00', 10.00, 'Ninguna1', 17, 1),
	(18, '2024-12-08 10:47:02', '2024-12-08 15:47:02', 10.00, 'NINGUNA', 18, 1),
	(19, '2024-12-08 10:55:59', '2024-12-08 13:55:59', 6.00, 'NingunaCatorce', 19, 1),
	(20, '2024-12-08 11:01:23', '2024-12-08 16:01:23', 15.00, 'NINGUNAQUINCE', 20, 1),
	(21, '2024-12-08 11:04:23', '2024-12-08 16:04:23', 50.00, 'ningunaSeis', 21, 0),
	(22, '2024-12-08 11:04:29', '2024-12-08 16:04:29', 50.00, 'ningunaSeis', 21, 1),
	(23, '2024-12-08 12:17:07', '2024-12-08 16:17:07', 20.00, 'psafjaf', 23, 1),
	(24, '2024-12-08 12:22:39', '2024-12-08 14:22:39', 10.00, 'OJLAOK', 24, 0),
	(25, '2024-12-08 12:24:01', '2024-12-08 14:24:01', 10.00, 'OJLAOK', 24, 1),
	(26, '2024-12-08 16:52:20', '2024-12-08 20:52:20', 12.00, 'LKASFLKMA', 26, 1),
	(27, '2024-12-08 16:52:30', '2024-12-08 20:52:30', 12.00, 'LKASFLKMA', 26, 0),
	(28, '2024-12-08 17:07:06', '2024-12-08 21:07:06', 20.00, 'lqknflkwnfl', 28, 0),
	(29, '2024-12-10 00:28:01', '2024-12-10 03:28:01', 6.00, 'QR', 29, 1),
	(30, '2024-12-10 00:36:53', '2024-12-10 04:36:53', 20.00, 'QR', 7, 1);

-- Volcando estructura para tabla estacionamiento.Trabajador
CREATE TABLE IF NOT EXISTS `Trabajador` (
  `idTrabajador` int(11) NOT NULL AUTO_INCREMENT,
  `nombres` varchar(50) NOT NULL,
  `apellidos` varchar(50) NOT NULL,
  `edad` int(11) NOT NULL,
  `dni` char(8) NOT NULL,
  `telefono` char(9) NOT NULL,
  `email` varchar(100) NOT NULL,
  `turno` varchar(6) NOT NULL,
  PRIMARY KEY (`idTrabajador`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla estacionamiento.Trabajador: ~2 rows (aproximadamente)
INSERT INTO `Trabajador` (`idTrabajador`, `nombres`, `apellidos`, `edad`, `dni`, `telefono`, `email`, `turno`) VALUES
	(1, 'Nombre1', 'Apellido1', 33, '12345678', '987654324', 'correo@ejemplo.com', 'Tarde'),
	(2, 'Nombre2', 'Apellido2', 19, '29304812', '938471103', 'nombre2@gmail.com', 'tarde');

-- Volcando estructura para tabla estacionamiento.Usuario
CREATE TABLE IF NOT EXISTS `Usuario` (
  `login` varchar(20) NOT NULL COMMENT 'Login o cuenta del usuario',
  `fullname` varchar(150) NOT NULL COMMENT 'Nombre completo del usuario',
  `email` varchar(150) NOT NULL COMMENT 'Email del usuario',
  `pwd` char(32) NOT NULL DEFAULT '0' COMMENT 'Clave almacenada en MD5',
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla estacionamiento.Usuario: ~0 rows (aproximadamente)
INSERT INTO `Usuario` (`login`, `fullname`, `email`, `pwd`) VALUES
	('Jose', 'Jose Manuel', 'jose@gmail.com', 'e10adc3949ba59abbe56e057f20f883e'),
	('luis', 'luis velez', 'luve@gmail.com', 'e10adc3949ba59abbe56e057f20f883e'),
	('Manuel', 'Manuel', 'manuel@gmail.com', 'e10adc3949ba59abbe56e057f20f883e');

-- Volcando estructura para tabla estacionamiento.Vehiculo
CREATE TABLE IF NOT EXISTS `Vehiculo` (
  `idVehiculo` int(11) NOT NULL AUTO_INCREMENT,
  `placa` varchar(50) NOT NULL DEFAULT '',
  `tipo` varchar(15) NOT NULL,
  `idConductor` int(11) NOT NULL,
  PRIMARY KEY (`idVehiculo`),
  KEY `Vehiculo_Conductor` (`idConductor`),
  CONSTRAINT `Vehiculo_Conductor` FOREIGN KEY (`idConductor`) REFERENCES `Conductor` (`idConductor`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla estacionamiento.Vehiculo: ~22 rows (aproximadamente)
INSERT INTO `Vehiculo` (`idVehiculo`, `placa`, `tipo`, `idConductor`) VALUES
	(1, 'ALW-234', 'Moto', 1),
	(2, 'ALW-234', 'Moto', 1),
	(3, 'NoTiene', 'Bicicleta', 3),
	(4, 'LEF-358', 'Auto', 4),
	(5, 'AID-12J', 'Bicicleta', 5),
	(6, 'XYZ-123', 'Auto', 6),
	(7, 'JFW-132', 'Moto', 11),
	(8, 'JSJ-132', 'Camión', 12),
	(9, 'UIE-138', 'Bicicleta', 13),
	(10, 'ABC-123', 'Auto', 14),
	(11, 'JJE-234', 'Moto', 15),
	(12, 'JDI-345', 'Camión', 16),
	(13, 'JSW-23J', 'Bicicleta', 17),
	(14, 'KW3-3J3', 'Moto', 18),
	(15, 'ORL-32M', 'Camión', 19),
	(16, 'ORL-32M', 'Camión', 19),
	(17, 'KWI-34I', 'Auto', 20),
	(18, 'LWE-31K', 'Bicicleta', 21),
	(19, 'IWM-23J', 'Bicicleta', 22),
	(20, 'IWW-2K3', 'Moto', 23),
	(21, 'kjk-323', 'Camión', 24),
	(22, 'kjk-323', 'Camión', 24),
	(23, 'IEM/343', 'Auto', 25),
	(24, 'JFW-134', 'Auto', 26),
	(25, 'JFW-134', 'Auto', 26),
	(26, 'KDW-424', 'Moto', 27),
	(27, 'KDW-424', 'Moto', 27),
	(28, '824-RKE', 'Auto', 28),
	(29, 'JDW-35W', 'Bicicleta', 29),
	(30, 'JFW-132', 'Moto', 11);

-- Volcando estructura para disparador estacionamiento.trgFechaServicio
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER trgFechaServicio BEFORE INSERT ON Servicio
FOR EACH ROW
BEGIN
    IF NEW.fechaHoraIngreso < NOW() - INTERVAL 2 MINUTE THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La fecha de ingreso no puede ser del pasado.';
    END IF;
    
    IF NEW.fechaHoraSalida IS NOT NULL AND NEW.fechaHoraSalida < NEW.fechaHoraIngreso THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La fecha de salida no puede ser anterior a la fecha de ingreso.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para disparador estacionamiento.trgNombreSinNumeros
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER trgNombreSinNumeros BEFORE INSERT ON Conductor
FOR EACH ROW
BEGIN
    IF NEW.nombre REGEXP '[0-9]' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El nombre no puede contener números.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
