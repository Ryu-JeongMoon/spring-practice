package org.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ParameterTest {

  @DisplayName("ints 받아서 돌리기")
  @ParameterizedTest
  @ValueSource(ints = {10, 20, 40})
  void valueParameterizedTest(int param) {
    System.out.println("param = " + param);
  }

  @DisplayName("csv 받아서 돌리기")
  @ParameterizedTest
  @CsvSource({"11", "20"})
  void csvParameterizedTest(@ConvertWith(StudyConverter.class) Study study) {
    System.out.println("study = " + study);
  }

  @DisplayName("csv 받아서 돌리기")
  @ParameterizedTest
  @CsvSource({"11, java", "20, spring"})
  void csvParameterizedTestWithAggregator(@AggregateWith(StudyAggregator.class) Study study) {
    System.out.println("study = " + study);
  }

  static class StudyConverter extends SimpleArgumentConverter {

    @Override
    protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
      assertEquals(Study.class, targetType);
      return Study.builder()
        .limitOfStudents(Integer.parseInt(source.toString()))
        .build();
    }
  }

  static class StudyAggregator implements ArgumentsAggregator {

    @Override
    public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
      return Study.builder()
        .name(accessor.getString(0))
        .limitOfStudents(accessor.getInteger(0))
        .build();
    }
  }
}
