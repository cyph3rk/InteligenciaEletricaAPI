package com.InteligenciaEletricaAPI.equipamento;

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
class ValidaCamposEquipamento {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void cadastrandoEquipamentoCampoNomeBrancoTest() {
        String randomWord = generaPalavraRandomica(8);
        String id = cadastrandoEnderecoSucesso(randomWord);

        String url = "http://localhost:" + port + "/equipamento/" + id;

        String requestBody = "{\"nome\":\"\"," +
                "\"modelo\":\"Antigo\"," +
                "\"potencia\":\"50W\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("\"Campo NOME é obrigatorio\""));
    }

    @Test
    public void cadastrandoEquipamentoCampoNomeNuloTest() {
        String randomWord = generaPalavraRandomica(8);
        String id = cadastrandoEnderecoSucesso(randomWord);

        String url = "http://localhost:" + port + "/equipamento/" + id;

        String requestBody = "{\"modelo\":\"Antigo\"," +
                "\"potencia\":\"50W\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("\"Campo NOME é obrigatorio\""));
    }

    @Test
    public void cadastrandoEquipamentoCampoModeloBrancoTest() {
        String randomWord = generaPalavraRandomica(8);
        String id = cadastrandoEnderecoSucesso(randomWord);

        String url = "http://localhost:" + port + "/equipamento/" + id;

        String requestBody = "{\"nome\":\"" + randomWord + "\"," +
                "\"modelo\":\"\"," +
                "\"potencia\":\"50W\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("\"Campo MODELO é obrigatorio\""));
    }

    @Test
    public void cadastrandoEquipamentoCampoModeloNuloTest() {
        String randomWord = generaPalavraRandomica(8);
        String id = cadastrandoEnderecoSucesso(randomWord);

        String url = "http://localhost:" + port + "/equipamento/" + id;

        String requestBody = "{\"nome\":\"" + randomWord + "\"," +
                "\"potencia\":\"50W\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("\"Campo MODELO é obrigatorio\""));
    }

    @Test
    public void cadastrandoEquipamentoCampoPotenciaBrancoTest() {
        String randomWord = generaPalavraRandomica(8);
        String id = cadastrandoEnderecoSucesso(randomWord);

        String url = "http://localhost:" + port + "/equipamento/" + id;

        String requestBody = "{\"nome\":\"" + randomWord + "\"," +
                "\"modelo\":\"Antigo\"," +
                "\"potencia\":\"\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("\"Campo POTENCIA é obrigatorio\""));
    }

    @Test
    public void cadastrandoEquipamentoCampoPotenciaNuloTest() {
        String randomWord = generaPalavraRandomica(8);
        String id = cadastrandoEnderecoSucesso(randomWord);

        String url = "http://localhost:" + port + "/equipamento/" + id;

        String requestBody = "{\"nome\":\"" + randomWord + "\"," +
                "\"modelo\":\"Antigo\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("\"Campo POTENCIA é obrigatorio\""));
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

    private String cadastrandoEnderecoSucesso(String randomWord) {
        String url = "http://localhost:" + port + "/endereco";

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

            return id;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return  "Falha";
        }
    }


}
