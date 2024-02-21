package ru.job4j.io;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class ConfigTest {

    @Test
    void whenPairWithoutComment() {
        String path = "./data/pair_without_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name")).isEqualTo("Petr Arsentev");
    }

    @Test
    void whenPairWithCommentAndSpaces() {
        String path = "./data/pair_with_comment_and_spaces.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("# PostgreSQL")).isNull();
        assertThat(config.value("")).isNull();
    }

    @Test
    void whenKeyAndEqualSignThenException() {
        String path = "./data/key_and_equal_sign.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenValueAndEqualSignThenException() {
        String path = "./data/value_and_equal_sign.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenNoEqualSignThenException() {
        String path = "./data/no_equal_sign.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenOnlyEqualSignThenException() {
        String path = "./data/only_equal_sign.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenTwoEqualSignsAndValueThenException() {
        String path = "./data/two_equal_signs_and_value.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("key")).isEqualTo("value=1");
    }

    @Test
    void whenTwoEqualsSignsException() {
        String path = "./data/two_equal_signs.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("key")).isEqualTo("value=");
    }
}