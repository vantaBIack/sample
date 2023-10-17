package com.adpcoinchange.adpcoinchange.api;

import com.adpcoinchange.adpcoinchange.CoinsDesiredAmount;
import com.adpcoinchange.adpcoinchange.service.ChangeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CoinChangeController.class)
class CoinChangeControllerTest {

    @MockBean
    private ChangeService changeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldChangeBillSuccessfully() throws Exception {

        when(changeService.changeBill(any(), any())).thenReturn(Map.of(BigDecimal.TEN, 3));
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/change-bill")
                        .content(asJsonString(new ChangeCoinsRequest("TEN", CoinsDesiredAmount.MOST_COINS)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}