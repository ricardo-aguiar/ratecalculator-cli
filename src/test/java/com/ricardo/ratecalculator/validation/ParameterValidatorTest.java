package com.ricardo.ratecalculator.validation;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.ricardo.ratecalculator.RateCalculatorApp;
import java.io.File;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ParameterValidatorTest {

    @ParameterizedTest
    @MethodSource("loanAmountAndErrorMessages")
    public void shouldValidateFileExists(File inputFile, int loanAmount, List<String> expectedViolations) {

        ParameterValidator underTest = new ParameterValidator(new RateCalculatorApp(inputFile,
                                                                                    loanAmount, 36, null));

        List<String> results = underTest.validate();
        assertThat(results).containsOnlyElementsOf(expectedViolations);
    }

    static Stream<Arguments> loanAmountAndErrorMessages() {
        String validFile = ParameterValidatorTest.class.getResource("/market_data.csv").getFile();
        return Stream.of(
            arguments(new File(validFile), 100, asList("Loan amount must be between £1000 and £15000")),
            arguments(new File(validFile), 16000, asList("Loan amount must be between £1000 and £15000")),
            arguments(new File(validFile), 1222, asList("The loan amount '1222' must be in increment interval of 100")),
            arguments(new File("some_file.csv"), 1500, asList("File 'some_file.csv' does not exists")),
            arguments(new File("some_file.csv"), 1111, asList("File 'some_file.csv' does not exists", "The loan amount '1111' must be in increment interval of 100")),
            arguments(new File("some_file.csv"), 100, asList("File 'some_file.csv' does not exists", "Loan amount must be between £1000 and £15000")));

    }


}
//
//    @Test
//    @ParameterizedTest
//    public void loanAm
//
//    static Stream<Arguments> loanAmountAndErrorMessages() {
//        return Stream.of(
//            arguments("apple", 1, Arrays.asList("a", "b")),
//            arguments("lemon", 2, Arrays.asList("x", "y"))
//                        );

//}