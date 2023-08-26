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
class PessoaTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testeCadastrandoPessoaSucesso() {
        String url = "http://localhost:" + port + "/pessoa";

        String randomWord = generaPalavraRandomica(8);

        String requestBody = "{\"nome\":\"" + randomWord + "\"," +
                "\"data_nascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"codigo_cliente\":\"12345\"," +
                "\"relacionamento\":\"Filho\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            String mensagem = jsonNode.get("Messagem").asText();

            Assert.assertEquals(mensagem, "Pessoa CADASTRADO com sucesso.");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testeTentativaCadastrandoPessoaDuplicado() {
        String url = "http://localhost:" + port + "/pessoa";

        String requestBody = "{\"nome\":\"Diego Vargas\"," +
                "\"data_nascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"codigo_cliente\":\"12345\"," +
                "\"relacionamento\":\"Filho\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            String mensagem = jsonNode.get("Erro").asText();

            Assert.assertEquals(mensagem, "Pessoa JÁ cadastrado.");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Erro\": \"Pessoa JÁ cadastrado.\"}"));
    }

    @Test
    public void testeCadastrandoPessoaCampoNomeBranco() {
        String url = "http://localhost:" + port + "/pessoa";

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
    public void testeCadastrandoPessoaCampoNomeNulo() {
        String url = "http://localhost:" + port + "/pessoa";

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
    public void testeCadastrandoPessoaCampoDataNascimentoBranco() {
        String url = "http://localhost:" + port + "/pessoa";

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
    public void testeCadastrandoPessoaCampoDataNascimentoNulo() {
        String url = "http://localhost:" + port + "/pessoa";

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
    public void testeCadastrandoPessoaCampoSexoBranco() {
        String url = "http://localhost:" + port + "/pessoa";

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
    public void testeCadastrandoPessoaCampoSexoNulo() {
        String url = "http://localhost:" + port + "/pessoa";

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
    public void testeCadastrandoPessoaCampoRelacionamentoBranco() {
        String url = "http://localhost:" + port + "/pessoa";

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
    public void testeCadastrandoPessoaCampoRelacionamentoNulo() {
        String url = "http://localhost:" + port + "/pessoa";

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

    @Test
    public void testeAlterandoCamposPessoaSucesso() {
        String url = "http://localhost:" + port + "/pessoa";

        String randomWord = generaPalavraRandomica(8);

        String requestBody = "{\"nome\":\"" + randomWord + "\"," +
                "\"data_nascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"codigo_cliente\":\"12345\"," +
                "\"relacionamento\":\"Filho\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            String id = jsonNode.get("id").asText();

            url = "http://localhost:" + port + "/pessoa/" + id;

            requestBody = "{\"nome\":\"" + randomWord + " 99\"," +
                    "\"data_nascimento\":\"03/12/2013\"," +
                    "\"sexo\":\"Feminino\"," +
                    "\"codigo_cliente\":\"12345\"," +
                    "\"relacionamento\":\"Irmão\"}";

            requestEntity = new HttpEntity<>(requestBody, headers);
            response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

            String resp = "{\"nome\":\"" + randomWord + " 99\"," +
                    "\"data_nascimento\":\"03/12/2013\"," +
                    "\"sexo\":\"Feminino\"," +
                    "\"codigo_cliente\":\"12345\"," +
                    "\"relacionamento\":\"Irmão\"}";

            Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }






    }

    @Test
    public void testeAlterandoCamposPessoaFalha() {
        String url = "http://localhost:" + port + "/pessoa/99";

        String requestBody = "{\"nome\":\"Diego Altera Campos falha\"," +
                "\"data_nascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"codigo_cliente\":\"12345\"," +
                "\"relacionamento\":\"Filho\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Erro\": \"Pessoa NÃO cadastrado.\"}"));
    }

    @Test
    public void testeDeletaPessoaSucesso() {
        String url = "http://localhost:" + port + "/pessoa";

        String randomWord = generaPalavraRandomica(8);

        String requestBody = "{\"nome\":\"" + randomWord + "\"," +
                "\"data_nascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"codigo_cliente\":\"12345\"," +
                "\"relacionamento\":\"Filho\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            String id = jsonNode.get("id").asText();

            url = "http://localhost:" + port + "/pessoa/" + id;
            requestEntity = new HttpEntity<>(headers);
            response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
            Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Mensagem\": \"Pessoa DELETADO com sucesso.\"}"));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testeDeletaPessoaFalha() {
        String url = "http://localhost:" + port + "/pessoa/99";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Erro\": \"Pessoa NÃO cadastrado.\"}"));
    }

    @Test
    public void testePesquisaPessoaPorNomeSucesso() {
        String url = "http://localhost:" + port + "/pessoa";

        String randomWord = generaPalavraRandomica(8);

        String requestBody = "{\"nome\":\"" + randomWord + "\"," +
                "\"data_nascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"codigo_cliente\":\"12345\"," +
                "\"relacionamento\":\"Filho\"}";

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

            Assert.assertEquals(mensagem, "Pessoa CADASTRADO com sucesso.");

            url = "http://localhost:" + port + "/pessoa/nome/" + randomWord;
            requestEntity = new HttpEntity<>(headers);
            response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

            String resp = "{\"id\":" + id + "," +
                          "\"nome\":\"" + randomWord + "\"," +
                          "\"data_nascimento\":\"02/09/1977\"," +
                          "\"sexo\":\"Masculino\"," +
                          "\"codigo_cliente\":\"12345\"," +
                          "\"relacionamento\":\"Filho\"}";

            Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testePesquisaPessoaPorNomeFalha() {
        String url = "http://localhost:" + port + "/pessoa/nome/qualquercoisa";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Erro\": \"Pessoa NÃO cadastrado.\"}"));
    }

    @Test
    public void testePesquisaPessoaPorIdSucesso() {
        String url = "http://localhost:" + port + "/pessoa";

        String randomWord = generaPalavraRandomica(8);

        String requestBody = "{\"nome\":\"" + randomWord + "\"," +
                "\"data_nascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"codigo_cliente\":\"12345\"," +
                "\"relacionamento\":\"Filho\"}";

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

            Assert.assertEquals(mensagem, "Pessoa CADASTRADO com sucesso.");

            url = "http://localhost:" + port + "/pessoa/" + id;
            requestEntity = new HttpEntity<>(headers);
            response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

            System.out.println("resp: " + response);

            String resp = "{\"id\":" + id + "," +
                    "\"nome\":\"" + randomWord + "\"," +
                    "\"data_nascimento\":\"02/09/1977\"," +
                    "\"sexo\":\"Masculino\"," +
                    "\"codigo_cliente\":\"12345\"," +
                    "\"relacionamento\":\"Filho\"}";

            Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testePesquisaPessoaPorIdFalha() {
        String url = "http://localhost:" + port + "/pessoa/99";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Erro\": \"Pessoa NÃO cadastrado.\"}"));
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
