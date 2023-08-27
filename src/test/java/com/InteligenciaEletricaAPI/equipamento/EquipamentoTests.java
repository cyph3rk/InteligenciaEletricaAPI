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
class EquipamentoTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testeCadastrandoEquipamentoSucesso() {

        String randomWord = generaPalavraRandomica(8);
        String id = cadastrandoEnderecoSucesso(randomWord);

        String url = "http://localhost:" + port + "/equipamento/" + id;

        String requestBody = "{\"nome\":\"" + randomWord + "\"," +
                "\"modelo\":\"Antigo\"," +
                "\"potencia\":\"50W\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            String mensagem = jsonNode.get("Messagem").asText();

            Assert.assertEquals(mensagem, "Equipamento CADASTRADO com sucesso.");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testeTentativaCadastrandoEquipamentoDuplicado() {

        String randomWord = generaPalavraRandomica(8);
        String id = cadastrandoEnderecoSucesso(randomWord);

        String url = "http://localhost:" + port + "/equipamento/" + id;

        String requestBody = "{\"nome\":\"" + randomWord + "\"," +
                "\"modelo\":\"Antigo\"," +
                "\"potencia\":\"50W\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Erro\": \"Equipamento JÁ cadastrado.\"}"));
    }

    @Test
    public void testeCadastrandoEquipamentoCampoNomeBranco() {
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
    public void testeCadastrandoEquipamentoCampoNomeNulo() {
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
    public void testeCadastrandoEquipamentoCampoModeloBranco() {
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
    public void testeCadastrandoEquipamentoCampoModeloNulo() {
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
    public void testeCadastrandoEquipamentoCampoPotenciaBranco() {
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
    public void testeCadastrandoEquipamentoCampoPotenciaNulo() {
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

    @Test
    public void testeAlterandoCamposEquipamentoSucesso() {

        String randomWord = generaPalavraRandomica(8);
        String idEndereco = cadastrandoEnderecoSucesso(randomWord);

        String url = "http://localhost:" + port + "/equipamento/" + idEndereco;

        String requestBody = "{\"nome\":\"" + randomWord + "\"," +
                "\"modelo\":\"Antigo\"," +
                "\"potencia\":\"50W\"}";

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

            Assert.assertEquals(mensagem, "Equipamento CADASTRADO com sucesso.");

            url = "http://localhost:" + port + "/equipamento/" + id;

            requestBody = "{\"nome\":\"Novo Ferro Passar Roupa 002\"," +
                    "\"modelo\":\"Novo Antigo\"," +
                    "\"potencia\":\"150W\"}";

            requestEntity = new HttpEntity<>(requestBody, headers);
            response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

            jsonNode = objectMapper.readTree(response.getBody());
            mensagem = jsonNode.get("Messagem").asText();

            Assert.assertEquals(mensagem, "Equipamento ALTERADO com sucesso.");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testeAlterandoCamposEquipamentoFalha() {
        String url = "http://localhost:" + port + "/equipamento/99";

        String requestBody = "{\"nome\":\"Novo Ferro Passar Roupa\"," +
                "\"modelo\":\"Novo Antigo\"," +
                "\"potencia\":\"150W\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Erro\": \"Equipamento NÃO cadastrado.\"}"));
    }

    @Test
    public void testeDeletaEquipamentoSucesso() {

        String randomWord = generaPalavraRandomica(8);
        String idEndereco = cadastrandoEnderecoSucesso(randomWord);

        String url = "http://localhost:" + port + "/equipamento/" + idEndereco;

        String requestBody = "{\"nome\":\"" + randomWord + "\"," +
                "\"modelo\":\"Antigo\"," +
                "\"potencia\":\"50W\"}";

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

            Assert.assertEquals(mensagem, "Equipamento CADASTRADO com sucesso.");

            url = "http://localhost:" + port + "/equipamento/" + id;
            requestEntity = new HttpEntity<>(headers);
            response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
            Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Mensagem\": \"Equipamento DELETADO com sucesso.\"}"));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testeDeletaEquipamentoFalha() {
        String url = "http://localhost:" + port + "/equipamento/99";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Erro\": \"Equipamento NÃO cadastrado.\"}"));
    }

    @Test
    public void testePesquisaEquipamentoPorNomeSucesso() {

        String randomWord = generaPalavraRandomica(8);
        String idEndereco = cadastrandoEnderecoSucesso(randomWord);

        String url = "http://localhost:" + port + "/equipamento/" + idEndereco;

        String requestBody = "{\"nome\":\"" + randomWord + "\"," +
                "\"modelo\":\"Antigo\"," +
                "\"potencia\":\"50W\"}";

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

            Assert.assertEquals(mensagem, "Equipamento CADASTRADO com sucesso.");

            url = "http://localhost:" + port + "/equipamento/nome/" + randomWord;
            requestEntity = new HttpEntity<>(headers);
            response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

            String resp = "{\"id\":" + id + "," +
                    "\"nome\":\"" + randomWord + "\"," +
                    "\"modelo\":\"Antigo\"," +
                    "\"potencia\":\"50W\"," +
                      "\"enderecoDto\":{" +
                        "\"id\":" + idEndereco + "," +
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
    public void testePesquisaEquipamentoPorNomeFalha() {
        String url = "http://localhost:" + port + "/equipamento/nome/qualquercoisa";
                HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Erro\": \"Equipamento NÃO cadastrado.\"}"));
    }

    @Test
    public void testePesquisaEquipamentoPorIdSucesso() {

        String randomWord = generaPalavraRandomica(8);
        String idEndereco = cadastrandoEnderecoSucesso(randomWord);

        String url = "http://localhost:" + port + "/equipamento/" + idEndereco;

        String requestBody = "{\"nome\":\"" + randomWord + "\"," +
                "\"modelo\":\"Antigo\"," +
                "\"potencia\":\"50W\"}";

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

            Assert.assertEquals(mensagem, "Equipamento CADASTRADO com sucesso.");

            url = "http://localhost:" + port + "/equipamento/" + id;
            requestEntity = new HttpEntity<>(headers);
            response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

            String resp = "{\"id\":" + id + "," +
                    "\"nome\":\"" + randomWord + "\"," +
                    "\"modelo\":\"Antigo\"," +
                    "\"potencia\":\"50W\"," +
                    "\"enderecoDto\":{" +
                        "\"id\":" + idEndereco + "," +
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
    public void testePesquisaEquipamentoPorIdFalha() {
        String url = "http://localhost:" + port + "/equipamento/99";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Erro\": \"Equipamento NÃO cadastrado.\"}"));
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
