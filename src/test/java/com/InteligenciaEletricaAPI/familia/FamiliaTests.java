package com.InteligenciaEletricaAPI.familia;

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
class FamiliaTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testeCadastrandoFamiliaSucesso() {

        String randomWord = generaPalavraRandomica(8);

        String id = cadastrandoFamiliao(randomWord);
        Assert.assertNotEquals("Falha", id);

    }

    @Test
    public void testeTentativaCadastrandoFamiliaDuplicado() {

        String randomWord = generaPalavraRandomica(8);

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

            Assert.assertEquals(mensagem, "Familia CADASTRADA com sucesso.");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testeAlterandoFamiliaSucesso() {

        String randomWord = generaPalavraRandomica(8);

        String id = cadastrandoFamiliao(randomWord);
        Assert.assertNotEquals("Falha", id);

        randomWord = generaPalavraRandomica(8);
        String url = "http://localhost:" + port + "/familia/" + id + "/" + randomWord;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            String mensagem = jsonNode.get("Messagem").asText();

            Assert.assertEquals(mensagem, "Familia ALTERADA com sucesso.");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testeAlterandoFamiliaJaExiste() {

        String randomWord = generaPalavraRandomica(8);

        String id = cadastrandoFamiliao(randomWord);
        Assert.assertNotEquals("Falha", id);

        String url = "http://localhost:" + port + "/familia/" + id + "/" + randomWord;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody()
                .contains("{\"Erro\": \"Familia NÃO cadastrado. Já existe!\"}"));
    }

    @Test
    public void testePesquisaFamiliaPorNomeSucesso() {

        String randomWord = generaPalavraRandomica(8);

        String id = cadastrandoFamiliao(randomWord);
        Assert.assertNotEquals("Falha", id);

        String url = "http://localhost:" + port + "/familia/nome/" + randomWord;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        String resp = "{\"id\":" + id + "," +
                "\"nome\":\"" + randomWord + "\"}";
    }

    @Test
    public void testePesquisaFamiliaPorNomeFalha() {

        String url = "http://localhost:" + port + "/familia/nome/qualquerCoisa";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody()
                .contains("{\"Erro\": \"Familia NÃO cadastrada.\"}"));

    }

    @Test
    public void testePesquisaFamiliaPorIdSucesso() {

        String randomWord = generaPalavraRandomica(8);

        String id = cadastrandoFamiliao(randomWord);
        Assert.assertNotEquals("Falha", id);

        String url = "http://localhost:" + port + "/familia/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        String resp = "{\"id\":" + id + "," +
                "\"nome\":\"" + randomWord + "\"}";
    }

    @Test
    public void testePesquisaFamiliaPorIdFalha() {

        String url = "http://localhost:" + port + "/familia/9966";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody()
                .contains("{\"Erro\": \"Familia NÃO cadastrada.\"}"));

    }

    @Test
    public void testeDeletaFamiliaSucesso() {

        String randomWord = generaPalavraRandomica(8);

        String id = cadastrandoFamiliao(randomWord);
        Assert.assertNotEquals("Falha", id);

        String url = "http://localhost:" + port + "/familia/" + id;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody()
                .contains("{\"Mensagem\": \"Familia DELETADA com sucesso.\"}"));

    }

    @Test
    public void testeDeletaFamiliaFalha() {

        String url = "http://localhost:" + port + "/familia/999";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody()
                .contains("{\"Erro\": \"Familia NÃO cadastrada.\"}"));

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
