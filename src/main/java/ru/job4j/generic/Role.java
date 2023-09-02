package ru.job4j.generic;

public class Role extends Base {

    private final String rights;

    public Role(String id, String name) {
        super(id);
        this.rights = name;
    }

    public String getRights() {
        return rights;
    }
}
