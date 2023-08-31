package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class NameLoadTest {
    @Test
    void checkEmpty() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::getMap)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("no data");
    }

    @Test
    void whenParseEmptyNames() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::parse)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("array is empty");
    }

    @Test
    void whenParseThenNotContainEqualSign() {
        NameLoad nameLoad = new NameLoad();
        String names = "";
        assertThatThrownBy(() -> nameLoad.parse(names))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(names)
                .hasMessageContaining("not contain the symbol '='");
    }

    @Test
    void whenParseThenNotContainValue() {
        NameLoad nameLoad = new NameLoad();
        String names = "key=";
        assertThatThrownBy(() -> nameLoad.parse(names))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(names)
                .hasMessageContaining("not contain a value");
    }

    @Test
    void whenParseThenNotContainKey() {
        NameLoad nameLoad = new NameLoad();
        String names = "=value";
        assertThatThrownBy(() -> nameLoad.parse(names))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(names)
                .hasMessageContaining("not contain a key");
    }
}