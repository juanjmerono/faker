# language: es
Característica: Usuarios
# CONSULTAS
  @hello
  Escenario: Obtener un listado inicial de usuarios
    Dado un usuario no autenticado
    Cuando trata de obtener un listado de usuarios
    Entonces obtiene un error de autenticación
