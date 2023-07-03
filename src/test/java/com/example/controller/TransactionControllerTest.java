package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.model.Transaction;
import com.example.service.TransactionService;

public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Captor
    private ArgumentCaptor<Transaction> transactionCaptor;

    @Test
    public void testAddLog() {
        Transaction transaction = new Transaction();

        transactionController.addLog(transaction);

        verify(transactionService).addLog(transactionCaptor.capture());
        Transaction capturedTransaction = transactionCaptor.getValue();
        assertEquals(LocalDate.now(), capturedTransaction.getTransaction_date());
        assertEquals(LocalTime.now(), capturedTransaction.getTransaction_time());
    }

    @Test
    public void testShowLog() {
        int accountId = 123;
        Transaction expectedTransaction = new Transaction();

        when(transactionService.getTransactionsByAccountID(accountId)).thenReturn(Optional.of(expectedTransaction));

        Optional<Transaction> result = transactionController.showLog(accountId);

        assertEquals(Optional.of(expectedTransaction), result);
    }

    @Test
    public void testDeleteLog() {
        int accountId = 123;

        transactionController.deleteLog(accountId);

        verify(transactionService).deleteLog(accountId);
    }
}
