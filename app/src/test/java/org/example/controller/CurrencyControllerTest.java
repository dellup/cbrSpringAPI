package org.example.controller;

import org.example.model.RequestLog;
import org.example.service.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class CurrencyControllerTest {

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private CurrencyController currencyController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(currencyController)
                .setControllerAdvice(new org.example.handler.GlobalExceptionHandler())
                .build();
    }

    @Test
    public void testGetCurrencyRates_success() throws Exception {
        LocalDate date = LocalDate.now();
        RequestLog mockRequestLog = new RequestLog();
        mockRequestLog.setRequestDate(date);

        when(currencyService.getCurrencyRates(date)).thenReturn(mockRequestLog);

        mockMvc.perform(get("/api").param("date", date.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requestDate").value(date.toString()));

        verify(currencyService, times(1)).getCurrencyRates(date);
    }

    @Test
    public void testGetCurrencyRates_invalidDate() throws Exception {
        LocalDate date = LocalDate.now().plusDays(1); // Дата в будущем

        mockMvc.perform(get("/api").param("date", date.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The date cannot be in the future"));

        verify(currencyService, never()).getCurrencyRates(any(LocalDate.class));
    }
}