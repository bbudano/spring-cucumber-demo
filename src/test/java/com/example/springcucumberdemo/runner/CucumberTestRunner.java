package com.example.springcucumberdemo.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/", glue = { "com.example.springcucumberdemo.glue" }, plugin = "pretty")
public class CucumberTestRunner {
}
