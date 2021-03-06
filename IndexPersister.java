package test;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class IndexPersister {

  public static void main(String[] args) throws IOException {

    String string = System.getProperty("user.home") + "/trash/index.txt";

    if (args.length > 0) {
      string = args[0];
    }
    try (Stream<String> stream = Files.lines(Paths.get(string))) {

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
                  Files.write(path, "\n".getBytes(), APPEND, CREATE);
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
