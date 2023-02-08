package com.example.springcucumberdemo.model;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public record Guitar(@Id Long id, String manufacturer, String model, String finish, String serialNumber, BigDecimal amount) {
}
