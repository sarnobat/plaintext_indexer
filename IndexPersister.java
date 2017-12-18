package test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.function.Consumer;
import java.util.stream.Stream;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class IndexPersister {

  public static void main(String[] args) throws IOException {

    try (Stream<String> stream = Files.lines(Paths.get("/Users/ssarnobat/trash/index.txt"))) {

      stream.forEach(
          new Consumer<String>() {

            @Override
            public void accept(String line) {
              String[] tokens = line.split("\t");
              String term = tokens[0];
              String document = tokens[1];
              if (term.length() > 2) {
                try {
                  Path path =
                      Paths.get("/Users/ssarnobat/trash/index/" + term.substring(0, 2) + ".txt");
                  Files.createDirectories(path.getParent());
                  if (!path.toFile().exists()) {
                    Path f = Files.createFile(path);
                  }
                  Files.write(path, line.getBytes(), APPEND, CREATE);
                  System.err.println("[DEBUG] wrote: " + line);

                } catch (IOException e) {
                  e.printStackTrace();
                  System.exit(-1);
                }
              }
            }
          });
    }
  }
}
