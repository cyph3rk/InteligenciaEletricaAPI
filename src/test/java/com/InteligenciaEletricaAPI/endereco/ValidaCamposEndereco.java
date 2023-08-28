package com.InteligenciaEletricaAPI.endereco;

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
class ValidaCamposEndereco {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void cadastrandoEnderecoCampoRuaBrancoTest() {
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
    public void cadastrandoEnderecoCampoRuaNuloTest() {
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
    public void cadastrandoEnderecoCampoNumeroBrancoTest() {
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
    public void cadastrandoEnderecoCampoNumeroNuloTest() {
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
    public void cadastrandoEnderecoCampoBairroBrancoTest() {
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
    public void cadastrandoEnderecoCampoBairroNuloTest() {
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
    public void cadastrandoEnderecoCampoCidadeBrancoTest() {
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
    public void cadastrandoEnderecoCampoCidadeNuloTest() {
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
    public void cadastrandoEnderecoCampoEstadoBrancoTest() {
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
    public void cadastrandoEnderecoCampoEstadoNuloTest() {
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

}
