package com.InteligenciaEletricaAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EnderecoTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testeCadastrandoEnderecoSucesso() {
		String url = "http://localhost:" + port + "/endereco";

		String randomWord = generaPalavraRandomica(8);

		String requestBody = "{\"rua\":\"" + randomWord + "\"," +
				             "\"numero\":\"130\"," +
			                 "\"bairro\":\"Bela Vista I\"," +
				             "\"cidade\":\"São José\"," +
				             "\"estado\":\"Santa Catarina\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(response.getBody());

			String mensagem = jsonNode.get("Messagem").asText();
//            String id = jsonNode.get("id").asText();

			Assert.assertEquals(mensagem, "Endereco CADASTRADO com sucesso.");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testeTentativaCadastrandoEnderecoDuplicado() {
		String url = "http://localhost:" + port + "/endereco";

		String randomWord = generaPalavraRandomica(8);

		String requestBody = "{\"rua\":\"" + randomWord + "\"," +
				"\"numero\":\"130\"," +
				"\"bairro\":\"Bela Vista I\"," +
				"\"cidade\":\"São José\"," +
				"\"estado\":\"Santa Catarina\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(response.getBody());

			String mensagem = jsonNode.get("Erro").asText();

			Assert.assertEquals(mensagem, "Endereco JÁ cadastrado.");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Erro\": \"Endereco JÁ cadastrado.\"}"));
	}


	@Test
	public void testeCadastrandoEnderecoCampoRuaBranco() {
		String url = "http://localhost:" + port + "/endereco";

		String requestBody = "{\"rua\":\"\"," +
				"\"numero\":\"130\"," +
				"\"bairro\":\"Bela Vista I\"," +
				"\"cidade\":\"São José\"," +
				"\"estado\":\"Santa Catarina\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assert.assertTrue(response.getBody().contains("\"Campo RUA é obrigatorio\""));
	}

	@Test
	public void testeCadastrandoEnderecoCampoRuaNulo() {
		String url = "http://localhost:" + port + "/endereco";

		String requestBody = "{\"numero\":\"130\"," +
				"\"bairro\":\"Bela Vista I\"," +
				"\"cidade\":\"São José\"," +
				"\"estado\":\"Santa Catarina\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assert.assertTrue(response.getBody().contains("\"Campo RUA é obrigatorio\""));
	}

	@Test
	public void testeCadastrandoEnderecoCampoNumeroBranco() {
		String url = "http://localhost:" + port + "/endereco";

		String requestBody = "{\"rua\":\"Sao Jose\"," +
				"\"numero\":\"\"," +
				"\"bairro\":\"Bela Vista I\"," +
				"\"cidade\":\"São José\"," +
				"\"estado\":\"Santa Catarina\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assert.assertTrue(response.getBody().contains("\"Campo NUMERO é obrigatorio\""));
	}

	@Test
	public void testeCadastrandoEnderecoCampoNumeroNulo() {
		String url = "http://localhost:" + port + "/endereco";

		String requestBody = "{\"rua\":\"Sao Jose\"," +
				"\"bairro\":\"Bela Vista I\"," +
				"\"cidade\":\"São José\"," +
				"\"estado\":\"Santa Catarina\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assert.assertTrue(response.getBody().contains("\"Campo NUMERO é obrigatorio\""));
	}

	@Test
	public void testeCadastrandoEnderecoCampoBairroBranco() {
		String url = "http://localhost:" + port + "/endereco";

		String requestBody = "{\"rua\":\"Sao Jose\"," +
				"\"numero\":\"130\"," +
				"\"bairro\":\"\"," +
				"\"cidade\":\"São José\"," +
				"\"estado\":\"Santa Catarina\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assert.assertTrue(response.getBody().contains("\"Campo BAIRRO é obrigatorio\""));
	}

	@Test
	public void testeCadastrandoEnderecoCampoBairroNulo() {
		String url = "http://localhost:" + port + "/endereco";

		String requestBody = "{\"rua\":\"Sao Jose\"," +
				"\"numero\":\"130\"," +
				"\"cidade\":\"São José\"," +
				"\"estado\":\"Santa Catarina\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assert.assertTrue(response.getBody().contains("\"Campo BAIRRO é obrigatorio\""));
	}

	@Test
	public void testeCadastrandoEnderecoCampoCidadeBranco() {
		String url = "http://localhost:" + port + "/endereco";

		String requestBody = "{\"rua\":\"Sao Jose\"," +
				"\"numero\":\"130\"," +
				"\"bairro\":\"Bela Vista I\"," +
				"\"cidade\":\"\"," +
				"\"estado\":\"Santa Catarina\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assert.assertTrue(response.getBody().contains("\"Campo CIDADE é obrigatorio\""));
	}

	@Test
	public void testeCadastrandoEnderecoCampoCidadeNulo() {
		String url = "http://localhost:" + port + "/endereco";

		String requestBody = "{\"rua\":\"Sao Jose\"," +
				"\"numero\":\"130\"," +
				"\"bairro\":\"Bela Vista I\"," +
				"\"estado\":\"Santa Catarina\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assert.assertTrue(response.getBody().contains("\"Campo CIDADE é obrigatorio\""));
	}

	@Test
	public void testeCadastrandoEnderecoCampoEstadoBranco() {
		String url = "http://localhost:" + port + "/endereco";

		String requestBody = "{\"rua\":\"Sao Jose\"," +
				"\"numero\":\"130\"," +
				"\"bairro\":\"Bela Vista I\"," +
				"\"cidade\":\"São José\"," +
				"\"estado\":\"\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assert.assertTrue(response.getBody().contains("\"Campo ESTADO é obrigatorio\""));
	}

	@Test
	public void testeCadastrandoEnderecoCampoEstadoNulo() {
		String url = "http://localhost:" + port + "/endereco";

		String requestBody = "{\"rua\":\"Sao Jose\"," +
				"\"numero\":\"130\"," +
				"\"bairro\":\"Bela Vista I\"," +
				"\"cidade\":\"São José\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assert.assertTrue(response.getBody().contains("\"Campo ESTADO é obrigatorio\""));
	}

	@Test
	public void testeAlterandoCamposEnderecoSucesso() {
		String url = "http://localhost:" + port + "/endereco";

		String requestBody = "{\"rua\":\"Rua Altera Compor Endereco Sucesso\"," +
				"\"numero\":\"130\"," +
				"\"bairro\":\"Bela Vista I\"," +
				"\"cidade\":\"São José\"," +
				"\"estado\":\"Santa Catarina\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(response.getBody());

			String mensagem = jsonNode.get("Messagem").asText();
			String id = jsonNode.get("id").asText();

			Assert.assertEquals(mensagem, "Endereco CADASTRADO com sucesso.");

			url = "http://localhost:" + port + "/endereco/" + id;

			requestBody = "{\"rua\":\"Rua Altera Novo Compor Endereco Sucesso\"," +
					"\"numero\":\"130\"," +
					"\"bairro\":\"Bela Vista I\"," +
					"\"cidade\":\"São José\"," +
					"\"estado\":\"Santa Catarina\"}";


			requestEntity = new HttpEntity<>(requestBody, headers);
			response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

			String resp = "{\"id\":"+ id +"," +
						  "\"rua\":\"Rua Altera Novo Compor Endereco Sucesso\"," +
						  "\"numero\":\"130\"," +
						  "\"bairro\":\"Bela Vista I\"," +
						  "\"cidade\":\"São José\"," +
						  "\"estado\":\"Santa Catarina\"}";

			Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testeAlterandoCamposEnderecoFalha() {
		String url = "http://localhost:" + port + "/endereco/99";

		String requestBody = "{\"rua\":\"Rua Altera Endereco falha\"," +
				"\"numero\":\"130\"," +
				"\"bairro\":\"Bela Vista I\"," +
				"\"cidade\":\"São José\"," +
				"\"estado\":\"Santa Catarina\"}";


		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Erro\": \"Endereco NÃO cadastrado.\"}"));
	}

	@Test
	public void testeDeletaEnderecoSucesso() {
		String url = "http://localhost:" + port + "/endereco";

		String randomWord = generaPalavraRandomica(8);

		String requestBody = "{\"rua\":\"" + randomWord + "\"," +
				"\"numero\":\"130\"," +
				"\"bairro\":\"Bela Vista I\"," +
				"\"cidade\":\"São José\"," +
				"\"estado\":\"Santa Catarina\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(response.getBody());

			String mensagem = jsonNode.get("Messagem").asText();
			String id = jsonNode.get("id").asText();

			Assert.assertEquals(mensagem, "Endereco CADASTRADO com sucesso.");

			url = "http://localhost:" + port + "/endereco/" + id;
			requestEntity = new HttpEntity<>(headers);
			response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
			Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Mensagem\": \"Endereço DELETADO com sucesso.\"}"));

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testeDeletaEnderecoFalha() {
		String url = "http://localhost:" + port + "/endereco/99";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Erro\": \"Endereço NÃO cadastrado.\"}"));
	}

	@Test
	public void testePesquisaEnderecoPorRuaSucesso() {
		String url = "http://localhost:" + port + "/endereco";

		String randomWord = generaPalavraRandomica(8);

		String requestBody = "{\"rua\":\"" + randomWord + "\"," +
				"\"numero\":\"130\"," +
				"\"bairro\":\"Bela Vista I\"," +
				"\"cidade\":\"São José\"," +
				"\"estado\":\"Santa Catarina\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(response.getBody());

			String mensagem = jsonNode.get("Messagem").asText();
			String id = jsonNode.get("id").asText();

			Assert.assertEquals(mensagem, "Endereco CADASTRADO com sucesso.");

			url = "http://localhost:" + port + "/endereco/rua/" + randomWord;
			requestEntity = new HttpEntity<>(headers);
			response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

			String resp = "{\"id\":" + id + "," +
					      "\"rua\":\"" + randomWord + "\"," +
						  "\"numero\":\"130\"," +
						  "\"bairro\":\"Bela Vista I\"," +
						  "\"cidade\":\"São José\"," +
						  "\"estado\":\"Santa Catarina\"}";

			Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testePesquisaEnderecoPorRuaFalha() {
		String url = "http://localhost:" + port + "/endereco/rua/qualquercoisa";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Erro\": \"Endereco NÃO cadastrado.\"}"));
	}

	@Test
	public void testePesquisaEndrecoPorIdSucesso() {
		String url = "http://localhost:" + port + "/endereco";

		String randomWord = generaPalavraRandomica(8);

		String requestBody = "{\"rua\":\"" + randomWord + "\"," +
				"\"numero\":\"130\"," +
				"\"bairro\":\"Bela Vista I\"," +
				"\"cidade\":\"São José\"," +
				"\"estado\":\"Santa Catarina\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(response.getBody());

			String mensagem = jsonNode.get("Messagem").asText();
			String id = jsonNode.get("id").asText();

			Assert.assertEquals(mensagem, "Endereco CADASTRADO com sucesso.");

			url = "http://localhost:" + port + "/endereco/" + id;
			requestEntity = new HttpEntity<>(headers);
			response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

			String resp = "{\"id\":" + id + "," +
						  "\"rua\":\"" + randomWord + "\"," +
						  "\"numero\":\"130\"," +
						  "\"bairro\":\"Bela Vista I\"," +
						  "\"cidade\":\"São José\"," +
						  "\"estado\":\"Santa Catarina\"}";

			Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testePesquisaEnderecoPorIdFalha() {
		String url = "http://localhost:" + port + "/endereco/99";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Erro\": \"Endereco NÃO cadastrado.\"}"));
	}

	private static String generaPalavraRandomica(int length) {
		String allowedChars = "abcdefghijklmnopqrstuvwxyz"; // caracteres permitidos
		Random random = new Random();
		StringBuilder word = new StringBuilder();

		for (int i = 0; i < length; i++) {
			int randomIndex = random.nextInt(allowedChars.length());
			char randomChar = allowedChars.charAt(randomIndex);
			word.append(randomChar);
		}

		return word.toString();
	}

}
