package gb.lindx.fm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileInfo {

  private enum FIleType {
    FILE("F"), DIRECTORY("D");

    private String name;

    FIleType(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }

  private String name;
  private FIleType type;
  private long size;
  private LocalDateTime lasModified;

  public FileInfo(Path path) {
    try {
      this.name = path.getFileName().toString();
      this.size = Files.size(path);
      this.type = Files.isDirectory(path) ? FIleType.DIRECTORY : FIleType.FILE;

      if (this.type == FIleType.DIRECTORY) {
        this.size = -1L;
      }

      this.lasModified = LocalDateTime.ofInstant(

          Files.getLastModifiedTime(path).toInstant(),

          ZoneOffset.ofHours(0)

      );

    } catch (IOException e) {
      throw new RuntimeException("Unable to create file info from path");
    }

  }

}
