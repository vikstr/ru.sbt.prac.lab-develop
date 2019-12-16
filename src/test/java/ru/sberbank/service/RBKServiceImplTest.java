package ru.sberbank.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = RBKService.class)
public class RBKServiceImplTest {

    @Mock
    private RestTemplate restTemplateMock;
    @Mock
    private ResponseEntity<String> responseEntityMock;

    @InjectMocks
    private RBKServiceImpl service = new RBKServiceImpl();

    private String responseBody = "USD000000TOD,2019-09-10,65.44,65.55,65.3125,65.48,558467000,65.4491\n" +
            "USD000000TOD,2019-09-11,65.4025,65.4875,65.2825,65.4025,508890000,65.3835\n" +
            "USD000000TOD,2019-09-12,65.37,65.37,64.74,64.8,834418000,65.0667";



    @Test
    public void parseResponse() {
        Double[] expected = {65.4491, 65.3835, 65.0667};
        Double[] actual = service.parseResponse(responseBody).toArray(new Double[0]);
        assert(Arrays.equals(expected, actual));
    }

    @Test
    public void getMaxFromArray() {
        ArrayList<Double> testArray = new ArrayList<>(Arrays.asList(0.5, 1.5, 2.5));
        assertEquals(2.5, service.getMaxFromArray(testArray), 0.1);
    }
}//yuiyiusgsab