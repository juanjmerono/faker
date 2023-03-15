package es.um.atica.faker.users.cucumber;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import es.um.atica.faker.users.adapters.rest.dto.UserDTO;
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

    @Before
    public void setup() {
        objectMapper.registerModule(new Jackson2HalModule());
    }

    @Dado("^una API ubicada en (.+)$")
    public void existingAPIPath(String path) {
        apiPath = path;
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

    @Dado("^el usuario autenticado (.+)$")
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

    @Cuando("^trata de obtener el detalle del usuario (.+)$")
    public void detalleUsuariosGET(String id) throws Exception {
        mvcResult = mvc.perform(MockMvcRequestBuilders.get(apiPath+"/"+id)
            .with(jwt)
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    }

    @Cuando("^trata de crear el usuario (.+)$")
    public void createUsuarioPOST(String id) throws Exception {
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(apiPath+"/"+id)
            .with(jwt)
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    }

    @Cuando("^trata de actualizar el usuario (.+)$")
    public void updateUsuarioPUT(String id) throws Exception {
        mvcResult = mvc.perform(MockMvcRequestBuilders.put(apiPath+"/"+id)
            .with(jwt)
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    }

    @Cuando("^trata de eliminar el usuario (.+)$")
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
    public void obtieneListadoUsuarios() throws Exception {
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Y("la lista de usuarios no está vacía")
    public void notEmptyList() throws Exception {
        CollectionModel<EntityModel<UserDTO>> userList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<CollectionModel<EntityModel<UserDTO>>>() {});
        assertTrue(userList.getContent().size()>0);
    }

    @Y("^el usuario con id (.+)$")
    public void userWithId(String id) throws Exception {
        EntityModel<UserDTO> userList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<EntityModel<UserDTO>>() {});
        assertEquals(id,userList.getContent().getId());
    }

}