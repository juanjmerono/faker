# language: es
Característica: Usuarios
# CONSULTAS
  @users
  Escenario: Obtener un listado inicial de usuarios sin autenticar
    Dado un usuario no autenticado
    Cuando trata de obtener un listado de usuarios
    Entonces obtiene un error de autenticación

  @users
  Escenario: Obtener un listado inicial de usuarios autenticado sin autorización
    Dado el usuario autenticado user@acme.es
    Cuando trata de obtener un listado de usuarios
    Entonces obtiene un error de autorización

  @users
  Escenario: Obtener un listado inicial de usuarios autenticado con autorización
    Dado el usuario autenticado admin@acme.es
    Cuando trata de obtener un listado de usuarios
    Entonces obtiene una respuesta correcta
