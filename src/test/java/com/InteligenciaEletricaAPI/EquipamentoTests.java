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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EquipamentoTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testeCadastrandoEquipamentoSucesso() {
        String url = "http://localhost:" + port + "/equipamento";

        String requestBody = "{\"nome\":\"Batedeira Bolo\"," +
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
//            String id = jsonNode.get("id").asText();

            Assert.assertEquals(mensagem, "Equipamento CADASTRADO com sucesso.");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testeTentativaCadastrandoEquipamentoDuplicado() {
        String url = "http://localhost:" + port + "/equipamento";

        String requestBody = "{\"nome\":\"Batedeira Bolo Branca\"," +
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

        response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Erro\": \"Equipamento JÁ cadastrado.\"}"));
    }


    @Test
    public void testeCadastrandoEquipamentoCampoNomeBranco() {
        String url = "http://localhost:" + port + "/equipamento";

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
        String url = "http://localhost:" + port + "/equipamento";

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
        String url = "http://localhost:" + port + "/equipamento";

        String requestBody = "{\"nome\":\"Ferro Passar Roupa\"," +
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
        String url = "http://localhost:" + port + "/equipamento";

        String requestBody = "{\"nome\":\"Ferro Passar Roupa\"," +
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
        String url = "http://localhost:" + port + "/equipamento";

        String requestBody = "{\"nome\":\"Ferro Passar Roupa\"," +
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
        String url = "http://localhost:" + port + "/equipamento";

        String requestBody = "{\"nome\":\"Ferro Passar Roupa\"," +
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
        String url = "http://localhost:" + port + "/equipamento";

        String requestBody = "{\"nome\":\"Ferro Passar Roupa\"," +
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

            String resp = "{\"id\":\""+ id +"\"," +
                    "\"nome\":\"Novo Ferro Passar Roupa 002\"," +
                    "\"modelo\":\"Novo Antigo\"," +
                    "\"potencia\":\"150W\"}";

            Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));

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
        String url = "http://localhost:" + port + "/equipamento";
        String requestBody = "{\"nome\":\"Ferro Passar Roupa Teste Delete\"," +
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
        String url = "http://localhost:" + port + "/equipamento";
        String requestBody = "{\"nome\":\"Ferro Passar Roupa Novinho\"," +
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

            url = "http://localhost:" + port + "/equipamento/nome/Ferro Passar Roupa Novinho";
            requestEntity = new HttpEntity<>(headers);
            response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

            String resp = "{\"id\":\"" + id + "\"," +
                    "\"nome\":\"Ferro Passar Roupa Novinho\"," +
                    "\"modelo\":\"Antigo\"," +
                    "\"potencia\":\"50W\"}";

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
        String url = "http://localhost:" + port + "/equipamento";
        String requestBody = "{\"nome\":\"Ferro Passar Roupa Id\"," +
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

            String resp = "{\"id\":\"" + id + "\"," +
                    "\"nome\":\"Ferro Passar Roupa Id\"," +
                    "\"modelo\":\"Antigo\"," +
                    "\"potencia\":\"50W\"}";

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

}
