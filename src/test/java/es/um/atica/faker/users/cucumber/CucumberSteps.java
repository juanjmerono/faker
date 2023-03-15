package es.um.atica.faker.users.cucumber;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
public class CucumberSteps extends CucumberSpringConfiguration {

    private static final String GET_LISTADO_PATH = "/users";
    private static final String VALID_SCOPE = "SCOPE_test";

    private MvcResult mvcResult;
    private RequestPostProcessor jwt;

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
        mvcResult = mvc.perform(MockMvcRequestBuilders.get(GET_LISTADO_PATH)
            .with(jwt)
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    }

    @Entonces("obtiene un error de autenticación")
    public void obtieneSaludoCordial() throws Exception {
        assertEquals(401,mvcResult.getResponse().getStatus());
    }

}