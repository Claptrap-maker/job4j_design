package ru.job4j.question;

import java.util.*;

public class Analize {

    public static Info diff(Set<User> previous, Set<User> current) {
        Map<Integer, User> previousMap = new HashMap<>();
        Map<Integer, User> currentMap = new HashMap<>();
        Set<Integer> ids = new HashSet<>();

        for (User user : previous) {
            previousMap.put(user.getId(), user);
            ids.add(user.getId());
        }

        for (User user : current) {
            currentMap.put(user.getId(), user);
            ids.add(user.getId());
        }

        Info info = new Info(0, 0, 0);

        for (int id : ids) {
            if (previousMap.get(id) != null && currentMap.get(id) == null) {
                info.setDeleted(info.getDeleted() + 1);
            } else if (previousMap.get(id) == null && currentMap.get(id) != null) {
                info.setAdded(info.getAdded() + 1);
            } else if (!previousMap.get(id).equals(currentMap.get(id))) {
                info.setChanged(info.getChanged() + 1);
            }
        }
        return info;
    }
}
