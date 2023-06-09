package es.um.atica.faker.users.cucumber;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.fasterxml.jackson.core.type.TypeReference;

import es.um.atica.faker.users.adapters.rest.dto.ErrorDTO;
import es.um.atica.faker.users.adapters.rest.dto.UserDTO;
import es.um.atica.faker.users.domain.event.UserCreated;
import es.um.atica.faker.users.domain.event.UserDeleted;
import es.um.atica.faker.users.domain.event.UserUpdated;
import es.um.atica.shared.domain.events.Event;
import es.um.atica.shared.domain.events.EventBus;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
public class CucumberSteps extends CucumberSpringConfiguration {

    private static final String VALID_SCOPE = "SCOPE_test";

    private MvcResult mvcResult;
    private RequestPostProcessor jwt;
    private String apiPath;

    @SpyBean
    private EventBus eventBus;

    @Before
    public void setup() {
        objectMapper.registerModule(new Jackson2HalModule());
    }

    @Dado("una API ubicada en {string}")
    public void existingAPIPath(String path) {
        apiPath = path;
    }

    @After
    public void waitSeconds() throws InterruptedException {
        Thread.sleep(30);
    }

    @Dado("un usuario no autenticado")
    public void usuarioNoAutenticado() {
        jwt = new RequestPostProcessor(){
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                // Nothing to to
                return request;
            }  
        };
    }

    @Dado("el usuario autenticado {string}")
    public void usuarioAutenticado(String authUser) {
        jwt = SecurityMockMvcRequestPostProcessors.jwt()
                .jwt(u->u.subject(authUser))
                .authorities(new SimpleGrantedAuthority(VALID_SCOPE));
    }

    @Cuando("trata de obtener un listado de usuarios")
    public void listadoUsuariosGET() throws Exception {
        mvcResult = mvc.perform(MockMvcRequestBuilders.get(apiPath)
            .with(jwt)
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    }

    @Cuando("trata de obtener un listado de usuarios mayores de {int} años")
    public void listadoUsuariosFiltradoGET(int edad) throws Exception {
        mvcResult = mvc.perform(MockMvcRequestBuilders.get(apiPath+"/search")
            .with(jwt)
            .param("search", String.format("age>%d",edad))
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    }

    @Cuando("trata de obtener un listado de usuarios mayores de {int} años cuyo nombre empieza por {string}")
    public void listadoUsuariosFiltradoGET(int edad, String nombre) throws Exception {
        mvcResult = mvc.perform(MockMvcRequestBuilders.get(apiPath+"/search")
            .with(jwt)
            .param("search", String.format("age>%d,name~%s",edad,nombre))
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    }

    @Cuando("trata de obtener el detalle del usuario {string}")
    public void detalleUsuariosGET(String id) throws Exception {
        mvcResult = mvc.perform(MockMvcRequestBuilders.get(apiPath+"/"+id)
            .with(jwt)
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    }

    @Cuando("trata de crear el usuario {string} con nombre {string} y {int} años")
    public void createUsuarioPOST(String id, String name, int age) throws Exception {
        UserDTO usr = UserDTO.builder().id(id).name(name).age(age).build();
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(apiPath+"/"+id)
            .with(jwt)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(usr))
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    }

    @Cuando("trata de actualizar el usuario {string} con nombre {string}")
    public void updateUsuarioPUT(String id, String name) throws Exception {
        UserDTO usr = UserDTO.builder().id(id).name(name).build();
        mvcResult = mvc.perform(MockMvcRequestBuilders.put(apiPath+"/"+id)
            .with(jwt)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(usr))
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    }

    @Cuando("trata de eliminar el usuario {string}")
    public void deleteUsuarioPUT(String id) throws Exception {
        mvcResult = mvc.perform(MockMvcRequestBuilders.delete(apiPath+"/"+id)
            .with(jwt)
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    }

    @Entonces("obtiene un error de autenticación")
    public void obtieneUnauthorized() throws Exception {
        assertEquals(401,mvcResult.getResponse().getStatus());
    }

    @Entonces("obtiene un error de autorización")
    public void obtieneForbidden() throws Exception {
        assertEquals(403,mvcResult.getResponse().getStatus());
    }

    @Entonces("obtiene una respuesta correcta")
    public void obtieneOk() throws Exception {
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Entonces("obtiene una respuesta de elemento no encontrado")
    public void obtieneNotFound() throws Exception {
        assertEquals(404,mvcResult.getResponse().getStatus());
        ErrorDTO error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<ErrorDTO>() {});
        assertEquals(404, error.getStatus());
        assertEquals("java.util.NoSuchElementException", error.getException());
        assertTrue(error.getError().startsWith("User not found "));
    }

    @Entonces("obtiene una respuesta de elemento existente")
    public void obtieneConflict() throws Exception {
        assertEquals(409,mvcResult.getResponse().getStatus());
    }

    @Entonces("obtiene una respuesta de argumento ilegal {string}")
    public void obtieneIllegal(String message) throws Exception {
        assertEquals(400,mvcResult.getResponse().getStatus());
        ErrorDTO error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<ErrorDTO>() {});
        assertEquals(400, error.getStatus());
        assertEquals("java.lang.IllegalArgumentException", error.getException());
        assertEquals(message, error.getError());
    }

    @Y("la lista de usuarios no está vacía")
    public void notEmptyList() throws Exception {
        CollectionModel<EntityModel<UserDTO>> userList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<CollectionModel<EntityModel<UserDTO>>>() {});
        assertTrue(userList.getContent().size()>0);
    }

    @Y("el usuario con id {string}")
    public void userWithId(String id) throws Exception {
        EntityModel<UserDTO> userList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<EntityModel<UserDTO>>() {});
        assertEquals(id,userList.getContent().getId());
    }

    private void userEventNotLaunched(String id, ArgumentCaptor<? extends Event> ev) throws Exception {
        Mockito.verify(eventBus,never()).publish(ev.capture());
        assertEquals(0, ev.getAllValues().size());
    }

    private void userEventLaunched(String id, ArgumentCaptor<? extends Event> ev) throws Exception {
        Mockito.verify(eventBus).publish(ev.capture());
        assertEquals(id, ev.getValue().getAggregateId());
    }

    @Y("el usuario con id {string} es creado")
    public void userIsCreated(String id) throws Exception {
        userWithId(id);
        userEventLaunched(id,ArgumentCaptor.forClass(UserCreated.class));
    }

    @Y("el usuario con id {string} no es creado")
    public void userIsNotCreated(String id) throws Exception {
        userEventNotLaunched(id,ArgumentCaptor.forClass(UserCreated.class));
    }

    @Y("el usuario con id {string} es actualizado")
    public void userIsUpdated(String id) throws Exception {
        userWithId(id);
        userEventLaunched(id,ArgumentCaptor.forClass(UserUpdated.class));
    }
    @Y("el usuario con id {string} no es actualizado")
    public void userIsNotUpdated(String id) throws Exception {
        userEventNotLaunched(id,ArgumentCaptor.forClass(UserUpdated.class));
    }

    @Y("el usuario con id {string} es eliminado")
    public void userIsDeleted(String id) throws Exception {
        userWithId(id);
        userEventLaunched(id,ArgumentCaptor.forClass(UserDeleted.class));
    }

    @Y("el usuario con id {string} no es eliminado")
    public void userIsNotDeleted(String id) throws Exception {
        userWithId(id);
        userEventNotLaunched(id,ArgumentCaptor.forClass(UserDeleted.class));
    }

}