package ru.job4j.assertj;

import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class SimpleConvertTest {
    @Test
    void checkArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] array = simpleConvert.toArray("first", "second", "three", "four", "five");
        assertThat(array).hasSize(5)
                .contains("second")
                .contains("first", Index.atIndex(0))
                .containsAnyOf("zero", "second", "six")
                .doesNotContain("first", Index.atIndex(1));
    }

    @Test
    void checkList() {
        SimpleConvert simpleConvert = new SimpleConvert();
        List<String> list = simpleConvert.toList("first", "second", "three", "four", "five");
        assertThat(list).hasSize(5)
                .contains("second")
                .containsAnyOf("zero", "second", "six")
                .filteredOnAssertions(e -> assertThat(e).startsWith("f"))
                .doesNotContain("second")
                .hasSize(3)
                .first().isEqualTo("first");

    }

    @Test
    void checkSet() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Set<String> set = simpleConvert.toSet("first", "second", "three", "four", "five");
        assertThat(set).hasSize(5)
                .containsExactlyInAnyOrder("first", "second", "three", "four", "five")
                .filteredOnAssertions(e -> assertThat(e).startsWith("f"))
                .containsSequence("four", "five")
                .containsOnly("five", "four", "first")
                .anyMatch(e -> e.startsWith("fo"));
    }

    @Test
    void checkMap() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Map<String, Integer> map = simpleConvert.toMap("first", "second", "three", "four", "five");
        assertThat(map).hasSize(5)
                .containsValue(3)
                .containsKeys("first", "three", "second")
                .doesNotContainKey("null")
                .doesNotContainValue(99)
                .containsEntry("three", 2);
    }
}