package com.springanything.util;

import java.io.File;
import java.io.IOException;

import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.webp.WebpWriter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileConversionUtils {

  public static File convertToWebp(String fileName, File originalFile) {
    try {
      return ImmutableImage.loader()
        .fromFile(originalFile)
        .output(WebpWriter.DEFAULT.withMultiThread(), new File("uploads/" + fileName + ".webp"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static File convertToWebpWithLossless(String fileName, File originalFile) {
    try {
      return ImmutableImage.loader()
        .fromFile(originalFile)
        .output(WebpWriter.DEFAULT.withLossless().withMultiThread(), new File("uploads/" + fileName + ".webp"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
