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

import java.util.HashMap;
import java.util.Map;
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
        String randomWord = generaPalavraRandomica(8);

        Map<String, String> ids = cadastrandoPessoa(randomWord);
        Assert.assertNotEquals("Falha", ids.get("erro"));
    }

    @Test
    public void testeTentativaCadastrandoPessoaDuplicado() {

        String randomWord = generaPalavraRandomica(8);

        Map<String, String> ids = cadastrandoPessoa(randomWord);
        Assert.assertNotEquals("Falha", ids.get("erro"));

        String url = "http://localhost:" + port + "/pessoa/" + ids.get("familia");

        String requestBody = "{\"nome\":\"" + randomWord + "\"," +
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
    public void testeTentativaCadastrandoPessoaFalhaFamiliaNaoCadastrada() {

        String randomWord = generaPalavraRandomica(8);

        Map<String, String> ids = cadastrandoPessoa(randomWord);
        Assert.assertNotEquals("Falha", ids.get("erro"));

        String url = "http://localhost:" + port + "/pessoa/996";

        String requestBody = "{\"nome\":\"" + randomWord+"Novo" + "\"," +
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

            Assert.assertEquals(mensagem, "Familia NÃO cadastrada.");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testeAlterandoCamposPessoaSucesso() {

        String randomWord = generaPalavraRandomica(8);

        String id_familia = cadastrandoFamiliao(randomWord);
        Assert.assertNotEquals("Falha", id_familia);

        String url = "http://localhost:" + port + "/pessoa/" + id_familia;

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

        String randomWord = generaPalavraRandomica(8);

        Map<String, String> ids = cadastrandoPessoa(randomWord);
        Assert.assertNotEquals("Falha", ids.get("erro"));

        String url = "http://localhost:" + port + "/pessoa/" + ids.get("pessoa");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        Assert.assertTrue(response.getBody() != null && response.getBody()
                .contains("{\"Mensagem\": \"Pessoa DELETADO com sucesso.\"}"));

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

        String randomWord = generaPalavraRandomica(8);

        Map<String, String> ids = cadastrandoPessoa(randomWord);
        Assert.assertNotEquals("Falha", ids.get("erro"));

        String url = "http://localhost:" + port + "/pessoa/nome/" + randomWord;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        String resp = "{\"id\":" + ids.get("pessoa") + "," +
                      "\"nome\":\"" + randomWord + "\"," +
                      "\"data_nascimento\":\"02/09/1977\"," +
                      "\"sexo\":\"Masculino\"," +
                      "\"codigo_cliente\":\"12345\"," +
                      "\"relacionamento\":\"Filho\"," +
                      "\"familiaDto\":{" +
                            "\"id\":" + ids.get("familia") + "," +
                            "\"nome\":\"" + randomWord + "\"}}";

        Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));
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

        String randomWord = generaPalavraRandomica(8);

        Map<String, String> ids = cadastrandoPessoa(randomWord);
        Assert.assertNotEquals("Falha", ids.get("erro"));

        String url = "http://localhost:" + port + "/pessoa/" + ids.get("pessoa");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        String resp = "{\"id\":" + ids.get("pessoa") + "," +
                "\"nome\":\"" + randomWord + "\"," +
                "\"data_nascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"codigo_cliente\":\"12345\"," +
                "\"relacionamento\":\"Filho\"," +
                "\"familiaDto\":{" +
                    "\"id\":" + ids.get("familia") + "," +
                    "\"nome\":\"" + randomWord + "\"}}";

        System.out.println("response: " + response);
        System.out.println("resp: "+ resp);

        Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));
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

    @Test
    public void testeAssociacaoPessoaEnderecos() {

        String randomWord = generaPalavraRandomica(8);

        Map<String, String> ids = cadastrandoPessoa(randomWord);
        Assert.assertNotEquals("Falha", ids.get("erro"));

        String randomWord_id1 = generaPalavraRandomica(8);
        String endereco_id1 = cadastrandoEnderecoSucesso(randomWord_id1);

        String randomWord_id2 = generaPalavraRandomica(8);
        String endereco_id2 = cadastrandoEnderecoSucesso(randomWord_id2);

        String randomWord_id3 = generaPalavraRandomica(8);
        String endereco_id3 = cadastrandoEnderecoSucesso(randomWord_id3);

        String url = "http://localhost:" + port + "/pessoa/" + ids.get("pessoa") + "/cadastraEnderecos";

        String requestBody = "[" + endereco_id1 + "," + endereco_id2 + "," + endereco_id3 + "]";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            String mensagem = jsonNode.get("Messagem").asText();
            String resp = jsonNode.get("id").asText();

            Assert.assertEquals(mensagem, "Pessoa ASSOCIADA a endereços com sucesso.");
            String idsREsp = "[" + endereco_id1 + ", " + endereco_id2 + ", " + endereco_id3 + "]";
            Assert.assertEquals(resp, idsREsp);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
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

    public Map<String, String> cadastrandoPessoa(String randomWord) {

        Map<String, String> ids = new HashMap<>();

        String id_Familia = cadastrandoFamiliao(randomWord);
        Assert.assertNotEquals("Falha", id_Familia);

        ids.put("familia", id_Familia);

        String url = "http://localhost:" + port + "/pessoa/" + id_Familia;

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

            ids.put("pessoa", id);

            Assert.assertEquals(mensagem, "Pessoa CADASTRADO com sucesso.");

            return ids;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            ids.put("erro", "Falha");
            return ids;
        }
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
