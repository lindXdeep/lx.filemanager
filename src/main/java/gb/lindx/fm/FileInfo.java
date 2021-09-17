package gb.lindx.fm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileInfo {

  public enum FileType {

    FILE("F"), DIRECTORY("D");

    private String name;

    FileType(String name) {
      this.name = name;
    }

    public String getTypeName() {
      return name;
    }
  }

  private FileType type;

  private String fileName;

  private long size;

  private LocalDateTime lasModified;

  public FileInfo(Path path) {
    try {
      this.fileName = path.getFileName().toString();
      this.size = Files.size(path);
      this.type = Files.isDirectory(path) ? FileType.DIRECTORY : FileType.FILE;

      if (this.type == FileType.DIRECTORY) {
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
