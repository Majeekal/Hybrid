package me.majeek.hybrid.checks;

import me.majeek.hybrid.checks.movement.FlyA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckManager {
    private static final Class[] checks = new Class[]{
            FlyA.class,
    };

    public static List<Check> loadChecks() {
        List<Check> checklist = new ArrayList<>();
        Arrays.asList(checks).forEach(check -> {
            try {
                checklist.add((Check) check.getConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return checklist;
    }
}
