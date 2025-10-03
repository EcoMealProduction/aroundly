package com.backend.adapter.inbound.mapper.assembler;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.backend.port.inbound.commands.UploadMediaCommand;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class UploadMediaMapperAssemblerTest {

  private final UploadMediaMapperAssembler assembler = new UploadMediaMapperAssembler();

  @Test
  void testToUpload() throws IOException {
    byte[] data = "hello".getBytes(StandardCharsets.UTF_8);
    MockMultipartFile mockMultipartFile = new MockMultipartFile(
        "files",
        "ro ad(1).png",
        "image/png",
        data);

    UploadMediaCommand command = assembler.toUpload(mockMultipartFile);

    assertEquals("ro_ad_1_.png", command.filename());
    assertEquals(mockMultipartFile.getContentType(), command.contentType());
    assertEquals(mockMultipartFile.getSize(), command.size());

    try (InputStream in = command.stream()) {
      byte[] read = in.readAllBytes();
      assertArrayEquals(data, read);
    }
  }

  @Test
  void testToUploads() {
    MockMultipartFile f1 = new MockMultipartFile(
        "files", "a.jpg", "image/jpeg", new byte[]{1,2,3});
    MockMultipartFile f2 = new MockMultipartFile(
        "files", "b.mp4", "video/mp4", new byte[]{4,5});

    Set<UploadMediaCommand> commands = assembler.toUploads(Set.of(f1, f2));

    assertEquals(2, commands.size());
    assertTrue(commands.stream().anyMatch(c ->
        c.filename().equals("a.jpg") && c.contentType().equals("image/jpeg")));
    assertTrue(commands.stream().anyMatch(c ->
        c.filename().equals("b.mp4") && c.contentType().equals("video/mp4")));
  }
}
