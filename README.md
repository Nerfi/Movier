Descripción:

Esta aplicación Android Studio utiliza Java, Room ORM y una base de datos SQLite para gestionar películas ("Movies") y usuarios ("Users"). La aplicación implementa un sistema básico de CRUD (Crear, Leer, Actualizar, Eliminar) con las siguientes características:

Autenticación: Los usuarios pueden crear cuentas y registrarse para acceder a la aplicación.
Autorización: Solo el usuario que creó una película puede editarla o eliminarla.
Relación entre tablas: La tabla "Movies" tiene una relación uno a muchos con la tabla "Users", lo que significa que cada película está asociada con un único usuario.
Tecnologías utilizadas:

Android Studio
Java
Room ORM
SQLite
Estructura del proyecto:

app/src/main/java:
com.example.tfg: Paquete principal de la aplicación.
entities: Contiene las clases de entidad para las tablas "Movies" y "Users".
dao: Contiene la interfaz Data Access Object (DAO) para cada tabla.
database: Contiene la clase de base de datos que define la base de datos y las relaciones entre las tablas.
repositories: Contiene los repositorios que encapsulan la lógica de acceso a datos.
ui/activities: Contiene las actividades de la interfaz de usuario (UI) de la aplicación.
viewmodels: Contiene los ViewModels que gestionan los datos y la lógica de la UI.
Funcionalidades:

Crear cuenta: Los usuarios pueden crear una nueva cuenta con nombre de usuario y contraseña.
Iniciar sesión: Los usuarios pueden iniciar sesión con su nombre de usuario y contraseña.
Ver películas: La aplicación muestra una lista de todas las películas disponibles.
Ver detalles de la película: Al seleccionar una película, se muestran sus detalles, como título, descripción, fecha de lanzamiento y nombre del usuario que la creó.
Añadir película: Los usuarios pueden añadir nuevas películas, especificando el título, la descripción y la fecha de lanzamiento. Solo el usuario que creó la película puede editarla o eliminarla.
Editar película: Los usuarios pueden editar los detalles de una película que hayan creado.
Eliminar película: Los usuarios pueden eliminar una película que hayan creado.
Configuración:

Room: La base de datos SQLite se configura utilizando Room ORM, que simplifica el acceso a la base de datos y reduce el código boilerplate.
Autenticación: La aplicación utiliza un sistema de autenticación simple para asegurar que solo los usuarios registrados puedan acceder a las funcionalidades de la aplicación.
Autorización: La aplicación implementa un sistema de autorización básico que permite a los usuarios editar o eliminar solo las películas que ellos mismos han creado.
