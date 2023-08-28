package com.InteligenciaEletricaAPI.pessoa;

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
class ValidaCamposPessoa {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void campoNomeBrancoTest() {

        String randomWord = generaPalavraRandomica(8);

        String id_familia = cadastrandoFamiliao(randomWord);
        Assert.assertNotEquals("Falha", id_familia);

        String url = "http://localhost:" + port + "/pessoa/" + id_familia;

        String requestBody = "{\"nome\":\"\"," +
                "\"data_nascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"codigo_cliente\":\"12345\"," +
                "\"relacionamento\":\"Filho\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("\"Campo NOME é obrigatorio\""));
    }

    @Test
    public void campoNomeNuloTest() {

        String randomWord = generaPalavraRandomica(8);

        String id_familia = cadastrandoFamiliao(randomWord);
        Assert.assertNotEquals("Falha", id_familia);

        String url = "http://localhost:" + port + "/pessoa/" + id_familia;

        String requestBody = "{\"data_nascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"codigo_cliente\":\"12345\"," +
                "\"relacionamento\":\"Filho\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Assert.assertTrue(response.getBody() != null && response.getBody().contains("\"Campo NOME é obrigatorio\""));
    }

    @Test
    public void campoDataNascimentoBrancoTest() {

        String randomWord = generaPalavraRandomica(8);

        String id_familia = cadastrandoFamiliao(randomWord);
        Assert.assertNotEquals("Falha", id_familia);

        String url = "http://localhost:" + port + "/pessoa/" + id_familia;

        String requestBody = "{\"nome\":\"Diego Teste Duplicado\"," +
                "\"data_nascimento\":\"\"," +
                "\"sexo\":\"Masculino\"," +
                "\"codigo_cliente\":\"12345\"," +
                "\"relacionamento\":\"Filho\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("\"Campo DATA NASCIMENTO é obrigatorio\""));
    }

    @Test
    public void campoDataNascimentoNuloTest() {

        String randomWord = generaPalavraRandomica(8);

        String id_familia = cadastrandoFamiliao(randomWord);
        Assert.assertNotEquals("Falha", id_familia);

        String url = "http://localhost:" + port + "/pessoa/" + id_familia;

        String requestBody = "{\"nome\":\"Diego Teste Duplicado\"," +
                "\"sexo\":\"Masculino\"," +
                "\"codigo_cliente\":\"12345\"," +
                "\"relacionamento\":\"Filho\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Assert.assertTrue(response.getBody() != null && response.getBody().contains("\"Campo DATA NASCIMENTO é obrigatorio\""));
    }

    @Test
    public void campoSexoBrancoTest() {

        String randomWord = generaPalavraRandomica(8);

        String id_familia = cadastrandoFamiliao(randomWord);
        Assert.assertNotEquals("Falha", id_familia);

        String url = "http://localhost:" + port + "/pessoa/" + id_familia;

        String requestBody = "{\"nome\":\"Diego Teste Duplicado\"," +
                "\"data_nascimento\":\"02/09/1977\"," +
                "\"sexo\":\"\"," +
                "\"codigo_cliente\":\"12345\"," +
                "\"relacionamento\":\"Filho\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("\"Campo SEXO é obrigatorio\""));
    }

    @Test
    public void campoSexoNuloTest() {

        String randomWord = generaPalavraRandomica(8);

        String id_familia = cadastrandoFamiliao(randomWord);
        Assert.assertNotEquals("Falha", id_familia);

        String url = "http://localhost:" + port + "/pessoa/" + id_familia;

        String requestBody = "{\"nome\":\"Diego Teste Duplicado\"," +
                "\"data_nascimento\":\"02/09/1977\"," +
                "\"codigo_cliente\":\"12345\"," +
                "\"relacionamento\":\"Filho\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Assert.assertTrue(response.getBody() != null && response.getBody().contains("\"Campo SEXO é obrigatorio\""));
    }

    @Test
    public void campoRelacionamentoBrancoTest() {

        String randomWord = generaPalavraRandomica(8);

        String id_familia = cadastrandoFamiliao(randomWord);
        Assert.assertNotEquals("Falha", id_familia);

        String url = "http://localhost:" + port + "/pessoa/" + id_familia;

        String requestBody = "{\"nome\":\"Diego Teste Duplicado\"," +
                "\"data_nascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"codigo_cliente\":\"12345\"," +
                "\"relacionamento\":\"\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("\"Campo RELACIONAMENTO é obrigatorio\""));
    }

    @Test
    public void campoRelacionamentoNuloTest() {

        String randomWord = generaPalavraRandomica(8);

        String id_familia = cadastrandoFamiliao(randomWord);
        Assert.assertNotEquals("Falha", id_familia);

        String url = "http://localhost:" + port + "/pessoa/" + id_familia;

        String requestBody = "{\"nome\":\"Diego Teste Duplicado\"," +
                "\"data_nascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"codigo_cliente\":\"12345\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Assert.assertTrue(response.getBody() != null && response.getBody().contains("\"Campo RELACIONAMENTO é obrigatorio\""));
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

    private String cadastrandoFamiliao(String randomWord) {

        String url = "http://localhost:" + port + "/familia/" + randomWord;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            String mensagem = jsonNode.get("Messagem").asText();
            String id = jsonNode.get("id").asText();

            Assert.assertEquals(mensagem, "Familia CADASTRADA com sucesso.");

            return id;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Falha";
        }
    }

}
