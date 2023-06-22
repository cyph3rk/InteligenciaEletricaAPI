package com.InteligenciaEletricaAPI;

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

        String requestBody = "{\"nome\":\"Ferro Passar Roupa\"," +
                "\"modelo\":\"Antigo\"," +
                "\"potencia\":\"50W\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        String resp = "{\"id\":\"1\"," +
                "\"nome\":\"Ferro Passar Roupa\"," +
                "\"modelo\":\"Antigo\"," +
                "\"potencia\":\"50W\"}";

        Assert.assertTrue(response.getBody().contains(resp));
    }

    @Test
    public void testeCadastrandoEquipamentoCampoBranco() {
        String url = "http://localhost:" + port + "/equipamento";

        String requestBody = "{\"nome\":\"\"," +
                "\"modelo\":\"Antigo\"," +
                "\"potencia\":\"50W\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertTrue(response.getBody().contains("\"Campo NOME é obrigatorio\""));
    }

}
