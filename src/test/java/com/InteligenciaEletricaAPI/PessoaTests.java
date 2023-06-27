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
class PessoaTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testeCadastrandoPessoaSucesso() {
        String url = "http://localhost:" + port + "/pessoa";

        String requestBody = "{\"nome\":\"Diego Vargas\"," +
                "\"dataNascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"parentesco\":\"Filho\"}";

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

        String requestBody = "{\"nome\":\"Diego Teste Duplicado\"," +
                "\"dataNascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"parentesco\":\"Filho\"}";

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

        response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Erro\": \"Pessoa JÁ cadastrado.\"}"));
    }

    @Test
    public void testeCadastrandoPessoaCampoNomeBranco() {
        String url = "http://localhost:" + port + "/pessoa";

        String requestBody = "{\"nome\":\"\"," +
                "\"dataNascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"parentesco\":\"Filho\"}";

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

        String requestBody = "{\"dataNascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"parentesco\":\"Filho\"}";

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
                "\"dataNascimento\":\"\"," +
                "\"sexo\":\"Masculino\"," +
                "\"parentesco\":\"Filho\"}";

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
                "\"parentesco\":\"Filho\"}";

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
                "\"dataNascimento\":\"02/09/1977\"," +
                "\"sexo\":\"\"," +
                "\"parentesco\":\"Filho\"}";

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
                "\"dataNascimento\":\"02/09/1977\"," +
                "\"parentesco\":\"Filho\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Assert.assertTrue(response.getBody() != null && response.getBody().contains("\"Campo SEXO é obrigatorio\""));
    }

    @Test
    public void testeCadastrandoPessoaCampoParentescoBranco() {
        String url = "http://localhost:" + port + "/pessoa";

        String requestBody = "{\"nome\":\"Diego Teste Duplicado\"," +
                "\"dataNascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"parentesco\":\"\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("\"Campo PARENTESCO é obrigatorio\""));
    }

    @Test
    public void testeCadastrandoPessoaCampoParentescoNulo() {
        String url = "http://localhost:" + port + "/pessoa";

        String requestBody = "{\"nome\":\"Diego Teste Duplicado\"," +
                "\"dataNascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Assert.assertTrue(response.getBody() != null && response.getBody().contains("\"Campo PARENTESCO é obrigatorio\""));
    }

    @Test
    public void testeAlterandoCamposPessoaSucesso() {
        String url = "http://localhost:" + port + "/pessoa";

        String requestBody = "{\"nome\":\"Diego Altera Campos Sucesso\"," +
                "\"dataNascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"parentesco\":\"Filho\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        url = "http://localhost:" + port + "/pessoa/1";

        requestBody = "{\"nome\":\"Diego Alterado Ok\"," +
                "\"dataNascimento\":\"03/12/2013\"," +
                "\"sexo\":\"Feminino\"," +
                "\"parentesco\":\"Irmão\"}";

        requestEntity = new HttpEntity<>(requestBody, headers);
        response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

        String resp = "{\"id\":\"1\"," +
                "\"nome\":\"Diego Alterado Ok\"," +
                "\"dataNascimento\":\"03/12/2013\"," +
                "\"sexo\":\"Feminino\"," +
                "\"parentesco\":\"Irmão\"}";

        Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));
    }

    @Test
    public void testeAlterandoCamposPessoaFalha() {
        String url = "http://localhost:" + port + "/pessoa/99";

        String requestBody = "{\"nome\":\"Diego Altera Campos falha\"," +
                "\"dataNascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"parentesco\":\"Filho\"}";

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

        String requestBody = "{\"nome\":\"Diego Deleta Sucesso\"," +
                "\"dataNascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"parentesco\":\"Filho\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        url = "http://localhost:" + port + "/pessoa/1";
        requestEntity = new HttpEntity<>(headers);
        response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Mensagem\": \"Pessoa DELETADO com sucesso.\"}"));
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

        String requestBody = "{\"nome\":\"Diego Pesquisa por Nome\"," +
                "\"dataNascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"parentesco\":\"Filho\"}";

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

            url = "http://localhost:" + port + "/pessoa/nome/Diego Pesquisa por Nome";
            requestEntity = new HttpEntity<>(headers);
            response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

            String resp = "{\"id\":\"" + id + "\"," +
                          "\"nome\":\"Diego Pesquisa por Nome\"," +
                          "\"dataNascimento\":\"02/09/1977\"," +
                          "\"sexo\":\"Masculino\"," +
                          "\"parentesco\":\"Filho\"}";

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

        String requestBody = "{\"nome\":\"Diego Pesquisa por Id Sucesso\"," +
                "\"dataNascimento\":\"02/09/1977\"," +
                "\"sexo\":\"Masculino\"," +
                "\"parentesco\":\"Filho\"}";

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

            String resp = "{\"id\":\"" + id + "\"," +
                    "\"nome\":\"Diego Pesquisa por Id Sucesso\"," +
                    "\"dataNascimento\":\"02/09/1977\"," +
                    "\"sexo\":\"Masculino\"," +
                    "\"parentesco\":\"Filho\"}";

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





}
