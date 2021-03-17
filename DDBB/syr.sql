-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-03-2021 a las 06:45:08
-- Versión del servidor: 10.4.17-MariaDB
-- Versión de PHP: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `syr`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `idCategoria` int(11) NOT NULL,
  `nombreCategoria` varchar(40) NOT NULL,
  `descripcion` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`idCategoria`, `nombreCategoria`, `descripcion`) VALUES
(1, 'Tecnología', 'Producto vinculado con la tecnología'),
(2, 'Hogar', 'Productos para el Hogar'),
(3, 'Vehículos', 'Medio de locomoción (permite el traslado de un punto a otro). '),
(4, 'Juguetes', 'objeto para jugar, entretener, divertirse, aprender, generalmente destinado para  niños.'),
(5, 'Electrodomésticos', 'máquina o aparato que permite realizar y agilizar algunas tareas domésticas de rutina diaria.'),
(6, 'Belleza y Cuidado Personal', 'Producto de aseo e higiene que permite que el cuerpo y la mente se encuentren saludables.'),
(7, 'Herramienta y Construcción', 'Producto que sirve para permitir o facilitar una tarea mecánica que sin ella no se podría realizar, o sería muy difícil relacionado con la Construcción'),
(8, 'Inmuebles', 'Los edificios, las casas y las parcelas o terrenos son inmuebles.'),
(9, 'Moda Hombre', 'uso, costumbre, novedad, actualidad, usanza, manía, gusto, hábito, estilo para hombres.'),
(10, 'Moda Mujer', 'uso, costumbre, novedad, actualidad, usanza, manía, gusto, hábito, estilo para Mujeres.'),
(11, 'Consumibles', ' Que es susceptible de ser consumido o de consumirse.'),
(12, 'Antigüedades  y Coleccionables', 'objeto que existió en tiempo pasado y pertenecen a una época antigua.');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cesta`
--

CREATE TABLE `cesta` (
  `idCesta` int(11) NOT NULL,
  `IdProducto` int(11) NOT NULL,
  `precioPagado` double DEFAULT NULL,
  `idComprador` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `moneda`
--

CREATE TABLE `moneda` (
  `idMoneda` int(11) NOT NULL,
  `moneda` varchar(30) NOT NULL,
  `valorPeso` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `moneda`
--

INSERT INTO `moneda` (`idMoneda`, `moneda`, `valorPeso`) VALUES
(1, 'COP', 1),
(2, 'USD', 3553),
(3, 'EUR', 4305),
(4, 'JPY', 33.7),
(5, 'GBP', 4980.27);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `idProducto` int(11) NOT NULL,
  `imagen` varchar(50) NOT NULL,
  `nombreProducto` varchar(25) NOT NULL,
  `descripcion` varchar(250) DEFAULT NULL,
  `precioInicial` double DEFAULT NULL,
  `precioVenta` double DEFAULT NULL,
  `idSubastador` int(11) NOT NULL,
  `idCategoria` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `subasta`
--

CREATE TABLE `subasta` (
  `idSubasta` int(11) NOT NULL,
  `idProducto` int(11) NOT NULL,
  `tiempoInicio` datetime NOT NULL,
  `tiempoFin` datetime NOT NULL,
  `estado` int(1) NOT NULL,
  `idComprador` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `apellidoPaterno` varchar(25) NOT NULL,
  `apellidoMaterno` varchar(25) DEFAULT NULL,
  `correo` varchar(50) NOT NULL,
  `password` varchar(25) NOT NULL,
  `idMoneda` int(11) NOT NULL,
  `foto` varchar(50) DEFAULT NULL,
  `rol` int(1) NOT NULL DEFAULT 2
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `nombre`, `apellidoPaterno`, `apellidoMaterno`, `correo`, `password`, `idMoneda`, `foto`, `rol`) VALUES
(1, 'Bolkin', 'Bermudez', 'Camacho', 'otisalmado@gmail.com', '123456', 1, NULL, 1),
(2, 'Giovanny Alexander', 'Gutierrez', 'Rodríguez', 'gialguro@gmail.com', '123456', 4, NULL, 2),
(3, 'jose andres', 'Rodriguez', 'Zalamanca', 'gravemaker@gmail.com', '123456', 3, NULL, 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`idCategoria`);

--
-- Indices de la tabla `cesta`
--
ALTER TABLE `cesta`
  ADD PRIMARY KEY (`idCesta`),
  ADD KEY `idComprador` (`idComprador`),
  ADD KEY `IdProducto` (`IdProducto`);

--
-- Indices de la tabla `moneda`
--
ALTER TABLE `moneda`
  ADD PRIMARY KEY (`idMoneda`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`idProducto`),
  ADD KEY `idSubastador` (`idSubastador`),
  ADD KEY `idCategoria` (`idCategoria`);

--
-- Indices de la tabla `subasta`
--
ALTER TABLE `subasta`
  ADD PRIMARY KEY (`idSubasta`),
  ADD KEY `idComprador` (`idComprador`),
  ADD KEY `idProducto` (`idProducto`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idUsuario`),
  ADD KEY `idMoneda` (`idMoneda`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `idCategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `cesta`
--
ALTER TABLE `cesta`
  MODIFY `idCesta` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `moneda`
--
ALTER TABLE `moneda`
  MODIFY `idMoneda` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `idProducto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `subasta`
--
ALTER TABLE `subasta`
  MODIFY `idSubasta` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cesta`
--
ALTER TABLE `cesta`
  ADD CONSTRAINT `cesta_ibfk_1` FOREIGN KEY (`IdProducto`) REFERENCES `producto` (`idProducto`) ON UPDATE CASCADE,
  ADD CONSTRAINT `cesta_ibfk_2` FOREIGN KEY (`idComprador`) REFERENCES `usuario` (`idUsuario`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`idSubastador`) REFERENCES `usuario` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `producto_ibfk_2` FOREIGN KEY (`idCategoria`) REFERENCES `categoria` (`idCategoria`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `subasta`
--
ALTER TABLE `subasta`
  ADD CONSTRAINT `subasta_ibfk_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`) ON UPDATE CASCADE,
  ADD CONSTRAINT `subasta_ibfk_2` FOREIGN KEY (`idComprador`) REFERENCES `usuario` (`idUsuario`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`idMoneda`) REFERENCES `moneda` (`idMoneda`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
