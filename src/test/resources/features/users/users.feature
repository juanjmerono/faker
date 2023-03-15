# language: es
Característica: Usuarios

  Antecedentes: Disponemos de una API de usuarios
    Dado una API ubicada en /faker/v1/user

  @users @listado @unauthorized @error
  Escenario: Obtener un listado inicial de usuarios sin autenticar
    Dado un usuario no autenticado
    Cuando trata de obtener un listado de usuarios
    Entonces obtiene un error de autenticación

  @users @listado @forbidden @error
  Escenario: Obtener un listado inicial de usuarios autenticado sin autorización
    Dado el usuario autenticado user@acme.es
    Cuando trata de obtener un listado de usuarios
    Entonces obtiene un error de autorización

  @users @listado @success
  Escenario: Obtener un listado inicial de usuarios autenticado con autorización
    Dado el usuario autenticado admin@acme.es
    Cuando trata de obtener un listado de usuarios
    Entonces obtiene una respuesta correcta
