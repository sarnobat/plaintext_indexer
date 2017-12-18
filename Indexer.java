package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Indexer {
  public static void main(String[] args) throws IOException {

    try (Stream<String> stream = Files.lines(Paths.get("/files.txt"))) {

      stream.forEach(
          new Consumer<String>() {

            @Override
            public void accept(String line) {
              //System.out.println(line);
              Map<String, String> m = new HashMap<String, String>();
              Path path = Paths.get(line);
              Path fileName = path.getFileName();
              //System.err.println(fileName);
              if (fileName != null) {
                m.put(fileName.toString(), line);
              }
              if (path.getParent() != null) {
                if (path.getParent().getFileName() != null) {
                  m.put(path.getParent().getFileName().toString(), line);
                }
              }
              m.forEach(
                  new BiConsumer<String, String>() {

                    @Override
                    public void accept(String t, String u) {
                      System.out.println(t + "\t" + u);
                    }
                  });
            }
          });

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
