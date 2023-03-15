package es.um.atica.faker.cucumber;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
public class CucumberSteps extends CucumberSpringConfiguration {

    @Dado("un usuario no autenticado")
    void usuarioNoAutenticado() {}

    @Cuando("trata de recibir un saludo")
    void trataRecibirSaludo() {}

    @Entonces("obtiene un saludo cordial")
    void obtieneSaludoCordial() {}

}