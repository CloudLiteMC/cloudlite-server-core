package com.burchard36.cloudlite.utils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class FileManipulator {

    public static void forcefullyCreate(final File file, Consumer<Void> consumer) {
        CompletableFuture.supplyAsync(() -> {
            if (file.exists()) {
                file.delete();
            }

            try {
                file.createNewFile();
                consumer.accept(null);
                return null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).join();
    }
}
