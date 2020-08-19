package me.majeek.hybrid.checks;

import me.majeek.hybrid.checks.combat.aura.AuraA;
import me.majeek.hybrid.checks.combat.reach.ReachA;
import me.majeek.hybrid.checks.movement.fly.FlyA;
import me.majeek.hybrid.checks.movement.speed.SpeedA;
import me.majeek.hybrid.checks.movement.speed.SpeedB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckManager {

    private static final Class[] checks = new Class[]{
            AuraA.class,
            FlyA.class,
            ReachA.class,
            SpeedA.class,
            SpeedB.class,
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
