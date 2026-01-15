# ProyectoProg3_G3

Nuestro proyecto para PROG 3 (OIER MARTINEZ, ASIER URIBARREN, JAVIER ORTIZ)

Es una aplicacion de gestion/compra de entradas de un cine, al ejecutarlo verás que todos los botones estan bloqueados menos el de INICIO SESION 
Esto es porque deberas introducir un Nombre y Apellido para poder realizar reservas.
Una vez hecho esto, selecciona el boton que quieras, salas y reportes no tienen funcionalidad aun.
Si quieres entrar en el gestor de peliculas tienes que poner usuario: admin, contraseña: admin. Asi podras crear, editar y eliminar peliculas a mano. 

Si haces click en Venta de entradas podras seleccionar de una lista de dias que sale a la izquierda de la ventana, despues elegir una pleicula de la lista del centro de la ventana, y por ultimo añadir la cantidad de entradas de adulto y niño. Una vez hecho eso accedes a la seleccion de asiento, donde estan las butacas representadas como casillas y puedes seleccionar los asientos que quieras para la pelicula. A tener en cuenta que si seleccionas una sala XL habra muchas mas casillas/butacas disponibles para su seleccion. Estas reservas se iran añadiendo en el carrito, boton con imagen, donde al clickar odrás ver a modo de resumen la informacion y pagar/confirmar  la reserva final de todas las entradas y butacas seleccionadas.

Hilos implementados en la barra de carga inicial y rotacion de imagenes en la portada.

La base de datos esta incluida para Guardar todas las peliculas del cine, asi como poder eliminarlas y editarlas permanentemente y que estos cambios se vean reflejados. Tambien ha sido incluida en los inicios de sesion, donde haciendo click en el boton Reportes tienes un listado de los usuarios que han entrado en la app, con hora y fecha de entrada. (Para este boton tambien es necesaria la cuenta con nombre admin, y contraseña admin.).

Hemos incluido la recursividad en un boton dentro de Venta de Entradas. Al hacer click sobre el boton "Hacer Maratón de Peliculas", te pide que introduzcas una hora de entrada y una de salida, y con esto hace una busqueda recursiva y genera una combinacion de peliculas dentro de esos horarios.

La clase principal es: **/Main/CineManagerApp**

https://github.com/oiermartinezz/ProyectoProg3_G3 <-- **link al repositorio**

tiene 2 branches, main y master, es por eso que puede que los commits realizados no se vean bien a primera vista (posible problema)
