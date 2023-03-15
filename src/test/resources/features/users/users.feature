# language: es
Característica: Usuarios

  Antecedentes: Disponemos de una API de usuarios
    Dado una API ubicada en /faker/v1/user

# LIST

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
    Y la lista de usuarios no está vacía

# DETAIL

  @users @detail @unauthorized @error
  Escenario: Obtener el detalle de un usuario sin autenticar
    Dado un usuario no autenticado
    Cuando trata de obtener el detalle del usuario 30497182-c376-11ed-afa1-0242ac120002
    Entonces obtiene un error de autenticación

  @users @detail @forbidden @error
  Escenario: Obtener el detalle de un usuario autenticado sin autorización
    Dado el usuario autenticado user@acme.es
    Cuando trata de obtener el detalle del usuario 30497182-c376-11ed-afa1-0242ac120002
    Entonces obtiene un error de autorización

  @users @detail @success
  Escenario: Obtener el detalle de un usuario autenticado con autorización
    Dado el usuario autenticado admin@acme.es
    Cuando trata de obtener el detalle del usuario 30497182-c376-11ed-afa1-0242ac120002
    Entonces obtiene una respuesta correcta
    Y el usuario con id 30497182-c376-11ed-afa1-0242ac120002

# CREATE

  @users @create @unauthorized @error
  Escenario: Crear un usuario sin autenticar
    Dado un usuario no autenticado
    Cuando trata de crear el usuario 30497182-c376-11ed-afa1-0242ac120002
    Entonces obtiene un error de autenticación

  @users @create @forbidden @error
  Escenario: Crear un usuario autenticado sin autorización
    Dado el usuario autenticado user@acme.es
    Cuando trata de crear el usuario 30497182-c376-11ed-afa1-0242ac120002
    Entonces obtiene un error de autorización

  @users @create @success
  Escenario: Crear un usuario autenticado con autorización
    Dado el usuario autenticado admin@acme.es
    Cuando trata de crear el usuario 30497182-c376-11ed-afa1-0242ac120002
    Entonces obtiene una respuesta correcta
    Y el usuario con id 30497182-c376-11ed-afa1-0242ac120002

# UPDATE

  @users @update @unauthorized @error
  Escenario: Actualizar un usuario sin autenticar
    Dado un usuario no autenticado
    Cuando trata de actualizar el usuario 30497182-c376-11ed-afa1-0242ac120002
    Entonces obtiene un error de autenticación

  @users @update @forbidden @error
  Escenario: Actualizar un usuario autenticado sin autorización
    Dado el usuario autenticado user@acme.es
    Cuando trata de actualizar el usuario 30497182-c376-11ed-afa1-0242ac120002
    Entonces obtiene un error de autorización

  @users @update @success
  Escenario: Actualizar un usuario autenticado con autorización
    Dado el usuario autenticado admin@acme.es
    Cuando trata de actualizar el usuario 30497182-c376-11ed-afa1-0242ac120002
    Entonces obtiene una respuesta correcta
    Y el usuario con id 30497182-c376-11ed-afa1-0242ac120002

# DELETE

  @users @delete @unauthorized @error
  Escenario: Eliminar un usuario sin autenticar
    Dado un usuario no autenticado
    Cuando trata de eliminar el usuario 30497182-c376-11ed-afa1-0242ac120002
    Entonces obtiene un error de autenticación

  @users @delete @forbidden @error
  Escenario: Eliminar un usuario autenticado sin autorización
    Dado el usuario autenticado user@acme.es
    Cuando trata de eliminar el usuario 30497182-c376-11ed-afa1-0242ac120002
    Entonces obtiene un error de autorización

  @users @delete @success
  Escenario: Eliminar un usuario autenticado con autorización
    Dado el usuario autenticado admin@acme.es
    Cuando trata de eliminar el usuario 30497182-c376-11ed-afa1-0242ac120002
    Entonces obtiene una respuesta correcta
    Y el usuario con id 30497182-c376-11ed-afa1-0242ac120002
