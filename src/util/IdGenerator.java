package util;

import java.util.UUID;

/** Creates readable unique ids such as STU-3FA9C2. */
public final class IdGenerator {

    private IdGenerator() { }

    public static String next(String prefix) {
        return prefix + "-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
