# language: es
Característica: Saludo
# CONSULTAS
  @hello
  Escenario: Obtener un saludo inicial
    Dado un usuario no autenticado
    Cuando trata de recibir un saludo
    Entonces obtiene un saludo cordial
