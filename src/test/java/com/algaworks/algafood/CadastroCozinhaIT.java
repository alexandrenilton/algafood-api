package com.algaworks.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest ( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource({"/application-test.properties"})
public class CadastroCozinhaIT {
	
	private static final int COZINHA_ID_INEXISTENTE = 100;
	
	private Cozinha cozinhaAmericana;
	private int quantidadeCozinhasCadastradas;
	private String jsonCorretoCozinhaChinesa;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository repository;
	
//	@Autowired
//	private Flyway flyway;
	
	@Before
	public void init(){
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		//flyway.migrate();
		databaseCleaner.clearTables();
		prepararDados();
		
		jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
				"/json/correto/cozinha-chinesa.json");
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveConter2Cozinhas_QuandoConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("nome", hasSize(2)) //vamos ter 2 "nomes"
			.body("nome", hasItems("Tailandesa", "Americana")); // tem q ter 2 itens com o nome Indiana e Tailandesa
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
	    given()
	        .body(jsonCorretoCozinhaChinesa)
	        .contentType(ContentType.JSON)
	        .accept(ContentType.JSON)
	    .when()
	        .post()
	    .then()
	        .statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
	    given()
	        .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
	        .accept(ContentType.JSON)
	    .when()
	        .get("/{cozinhaId}")
	    .then()
	        .statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
	    given()
	        .pathParam("cozinhaId", cozinhaAmericana.getId())
	        .accept(ContentType.JSON)
	    .when()
	        .get("/{cozinhaId}")
	    .then()
	        .statusCode(HttpStatus.OK.value())
	        .body("nome", equalTo(cozinhaAmericana.getNome()));
	}
	
	@Test
	public void deveRetornarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas() {
	    given()
	        .accept(ContentType.JSON)
	    .when()
	        .get()
	    .then()
	        .body("", hasSize(quantidadeCozinhasCadastradas));
	}
	
	private void prepararDados() {
	    Cozinha cozinhaTailandesa = new Cozinha();
	    cozinhaTailandesa.setNome("Tailandesa");
	    repository.save(cozinhaTailandesa);

	    cozinhaAmericana = new Cozinha();
	    cozinhaAmericana.setNome("Americana");
	    repository.save(cozinhaAmericana);
	    
	    quantidadeCozinhasCadastradas = (int) repository.count();
	}
}
