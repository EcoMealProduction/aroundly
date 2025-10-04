package com.backend.port.inbound;

import com.backend.domain.media.Media;
import java.util.Set;

public interface MediaUseCase {
  Set<Media> upload(Set<Media> uploadedMedia) throws Exception;
  void deleteByKeys(Set<String> keys) throws Exception;
}
