package com.backend.adapter.inbound.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.backend.adapter.inbound.dto.request.IncidentRequestDto;
import com.backend.adapter.inbound.dto.request.RadiusRequestDto;
import com.backend.adapter.inbound.dto.response.incident.IncidentDetailedResponseDto;
import com.backend.adapter.inbound.dto.response.incident.IncidentPreviewResponseDto;
import com.backend.adapter.inbound.mapper.IncidentMapper;
import com.backend.adapter.inbound.mapper.LocationMapper;
import com.backend.adapter.inbound.mapper.assembler.IncidentDtoAssembler;
import com.backend.adapter.inbound.rest.exception.incident.IncidentNotExpiredException;
import com.backend.adapter.inbound.rest.exception.incident.IncidentNotFoundException;
import com.backend.domain.actor.ActorId;
import com.backend.domain.happening.Happening;
import com.backend.domain.happening.Incident;
import com.backend.domain.location.LocationId;
import com.backend.domain.media.Media;
import com.backend.domain.reactions.EngagementStats;
import com.backend.port.inbound.IncidentUseCase;
import com.backend.port.inbound.commands.CreateIncidentCommand;
import com.backend.port.inbound.commands.RadiusCommand;
import com.backend.port.inbound.commands.UploadMediaCommand;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(SpringExtension.class)
class IncidentControllerTest {

  private static final long HAPPENING_ID = 1L;
  private static final long INCIDENT_ID = 2L;
  private static final long NON_EXISTING_INCIDENT_ID = 7L;
  private static final long ACTIVE_INCIDENT_ID = 8L;
  private static final String ACTOR_ID = "abc-123";

  @Mock private IncidentUseCase incidentUseCase;
  @Mock private IncidentDtoAssembler incidentDtoAssembler;
  @Mock private IncidentMapper incidentMapper;
  @Mock private LocationMapper locationMapper;
  @InjectMocks private IncidentController controller;

  @Test
  void testCreateIncident() throws URISyntaxException, IOException {
    final Set<UploadMediaCommand> mediaCommands = Set.of(
        new UploadMediaCommand(
            mock(InputStream.class),
            "name",
            2L, "type"));
    final CreateIncidentCommand command = new CreateIncidentCommand(
        "title", "description", mediaCommands, 31.31, 41.23);
    final Incident incident = createIncident();
    final IncidentRequestDto incidentRequestDto = createIncidentRequestDto();
    final IncidentDetailedResponseDto incidentDetailedResponseDto = createIncidentDetailedResponseDto();

    when(incidentMapper.toCreateIncidentCommand(incidentRequestDto)).thenReturn(command);
    when(incidentUseCase.create(command)).thenReturn(incident);
    when(incidentDtoAssembler.toDetailedDto(incident)).thenReturn(incidentDetailedResponseDto);

    final ResponseEntity<IncidentDetailedResponseDto> response = controller.create(incidentRequestDto);

    final IncidentDetailedResponseDto body = response.getBody();

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(body);
    assertEquals(incidentDetailedResponseDto, body);
  }

  @Test
  void testUpdateIncident() throws URISyntaxException, IOException {
    final Set<UploadMediaCommand> mediaCommands = Set.of(
        new UploadMediaCommand(
            mock(InputStream.class),
            "name",
            2L, "type"));
    final CreateIncidentCommand command = new CreateIncidentCommand(
        "new title", "new description", mediaCommands, 31.31, 41.23);

    final IncidentRequestDto updatedIncidentRequestDto = updateIncidentRequestDto();
    final IncidentDetailedResponseDto updatedIncidentDetailedResponseDto = updateIncidentDetailedResponseDto();
    final Incident updatedIncident = updateIncident();

    when(incidentMapper.toCreateIncidentCommand(updatedIncidentRequestDto)).thenReturn(command);
    when(incidentUseCase.update(HAPPENING_ID, command)).thenReturn(updatedIncident);
    when(incidentDtoAssembler.toDetailedDto(updatedIncident))
        .thenReturn(updatedIncidentDetailedResponseDto);

    final ResponseEntity<IncidentDetailedResponseDto> response =
        controller.update(HAPPENING_ID, updatedIncidentRequestDto);

    final IncidentDetailedResponseDto body = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(body);
    assertEquals(updatedIncidentDetailedResponseDto, body);
  }

  @Test
  void testGetIncidentInPreview() throws IOException {
    when(incidentUseCase.findById(HAPPENING_ID)).thenReturn(createIncident());
    when(incidentMapper.toIncidentPreviewResponseDto(createIncident()))
        .thenReturn(createIncidentPreviewResponseDto());

    ResponseEntity<IncidentPreviewResponseDto> response =
        controller.getIncidentInPreview(HAPPENING_ID);
    IncidentPreviewResponseDto body = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(body);
    assertEquals(createIncidentPreviewResponseDto(), body);
  }

  @Test
  void testGetIncidentInDetails() throws IOException {
    when(incidentUseCase.findById(HAPPENING_ID)).thenReturn(createIncident());
    when(incidentDtoAssembler.toDetailedDto(createIncident()))
        .thenReturn(createIncidentDetailedResponseDto());

    ResponseEntity<IncidentDetailedResponseDto> response =
        controller.getIncidentInDetails(HAPPENING_ID);

    IncidentDetailedResponseDto body = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(body);
    assertEquals(createIncidentDetailedResponseDto(), body);
  }

  @Test
  void testFindActorIncidentsInPreview() throws IOException {
    final List<Happening> happenings = List.of(createIncident());
    when(incidentUseCase.findByActorId(ACTOR_ID)).thenReturn(happenings);
    when(incidentMapper.toIncidentPreviewResponseDto(createIncident()))
        .thenReturn(createIncidentPreviewResponseDto());

    ResponseEntity<List<IncidentPreviewResponseDto>> response =
        controller.findActorIncidentsInPreview(ACTOR_ID);

    List<IncidentPreviewResponseDto> body = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(body);
    assertEquals(List.of(createIncidentPreviewResponseDto()), body);
  }

  @Test
  void testFindNearbyIncidents() throws IOException {
    when(locationMapper.toRadiusCommand(createRadiusRequestDto())).thenReturn(createRadiusCommand());
    when(incidentUseCase.findAllInGivenRange(createRadiusCommand()))
        .thenReturn(List.of(createIncident()));
    when(incidentMapper.toIncidentPreviewResponseDto(createIncident()))
        .thenReturn(createIncidentPreviewResponseDto());

    ResponseEntity<List<IncidentPreviewResponseDto>> response =
        controller.findNearbyIncidents(createRadiusRequestDto());

    List<IncidentPreviewResponseDto> body = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(body);
    assertEquals(List.of(createIncidentPreviewResponseDto()), body);
  }

  @Test
  void testConfirmIncidentPresence() throws IOException {
    when(incidentUseCase.confirm(INCIDENT_ID)).thenReturn(createConfirmedIncident());
    when(incidentDtoAssembler.toDetailedDto(createConfirmedIncident()))
        .thenReturn(createConfirmedIncidentDetailedResponseDto());

    ResponseEntity<IncidentDetailedResponseDto> response =
        controller.confirmIncidentPresence(INCIDENT_ID);

    IncidentDetailedResponseDto body = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(body);
    assertEquals(createConfirmedIncidentDetailedResponseDto(), body);
  }

  @Test
  void testDenyIncidentPresence() throws IOException {
    when(incidentUseCase.deny(INCIDENT_ID)).thenReturn(createDeniedIncident());
    when(incidentDtoAssembler.toDetailedDto(createDeniedIncident()))
        .thenReturn(createDeniedIncidentDetailedResponseDto());

    ResponseEntity<IncidentDetailedResponseDto> response =
        controller.denyIncidentPresence(INCIDENT_ID);

    IncidentDetailedResponseDto body = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(body);
    assertEquals(createDeniedIncidentDetailedResponseDto(), body);
  }

  @Test
  void testDeleteExpiredIncident() {
    ResponseEntity<Void> response = controller.deleteExpiredIncident(INCIDENT_ID);
    verify(incidentUseCase).deleteIfExpired(INCIDENT_ID);
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    assertNull(response.getBody());

    doThrow(new IncidentNotFoundException("nf")).when(incidentUseCase)
        .deleteIfExpired(NON_EXISTING_INCIDENT_ID);

    ResponseEntity<Void> failedResponse = controller.deleteExpiredIncident(NON_EXISTING_INCIDENT_ID);

    verify(incidentUseCase).deleteIfExpired(NON_EXISTING_INCIDENT_ID);
    assertEquals(HttpStatus.NOT_FOUND, failedResponse.getStatusCode());
    assertNull(failedResponse.getBody());

    doThrow(new IncidentNotExpiredException("active")).when(incidentUseCase).deleteIfExpired(8L);

    ResponseEntity<Void> notExpiredResponse = controller.deleteExpiredIncident(ACTIVE_INCIDENT_ID);

    verify(incidentUseCase).deleteIfExpired(ACTIVE_INCIDENT_ID);
    assertEquals(HttpStatus.BAD_REQUEST, notExpiredResponse.getStatusCode());
    assertNull(notExpiredResponse.getBody());
  }

  @Test
  void testDeleteIncident() {
    ResponseEntity<Void> response = controller.delete(INCIDENT_ID);
    verify(incidentUseCase).deleteById(INCIDENT_ID);
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    assertNull(response.getBody());

    doThrow(new IncidentNotFoundException("missing")).when(incidentUseCase)
        .deleteById(NON_EXISTING_INCIDENT_ID);

    ResponseEntity<Void> failedResponse = controller.delete(NON_EXISTING_INCIDENT_ID);

    verify(incidentUseCase).deleteById(NON_EXISTING_INCIDENT_ID);
    assertEquals(HttpStatus.NOT_FOUND, failedResponse.getStatusCode());
    assertNull(failedResponse.getBody());
  }

  private RadiusCommand createRadiusCommand() {
    final double latitude = 12.12;
    final double longitude = 43.43;
    final double range = 1.1;

    return new RadiusCommand(latitude, longitude, range);
  }

  private RadiusRequestDto createRadiusRequestDto() {
    final double latitude = 12.12;
    final double longitude = 43.43;
    final double range = 1.1;

    return new RadiusRequestDto(latitude, longitude, range);
  }

  private IncidentRequestDto createIncidentRequestDto() {
    return IncidentRequestDto.builder()
      .title("title")
      .description("description")
      .files(createFiles())
      .lat(12.12)
      .lon(43.43)
      .build();
  }

  private IncidentRequestDto updateIncidentRequestDto() {
    return IncidentRequestDto.builder()
      .title("updated title")
      .description("description")
      .files(createFiles())
      .lat(12.12)
      .lon(43.43)
      .build();
  }

  private IncidentPreviewResponseDto createIncidentPreviewResponseDto() throws IOException {
    return IncidentPreviewResponseDto.builder()
      .title("title")
      .media(createMedia())
      .build();
  }

  private IncidentDetailedResponseDto createIncidentDetailedResponseDto() throws IOException {
    return IncidentDetailedResponseDto.builder()
      .title("title")
      .description("description")
      .actorUsername("vanea")
      .media(createMedia())
      .lat(12.12)
      .lon(43.43)
      .build();
  }

  private Set<MultipartFile> createFiles() {
    byte[] data = "hello".getBytes(StandardCharsets.UTF_8);
    MultipartFile mockMultipartFile = new MockMultipartFile(
        "files",
        "ro ad(1).png",
        "image/png",
        data);
    return Set.of(mockMultipartFile);
  }

  private Set<Media> createMedia() {
    return Set.of(new Media(3L, "file", "type"));
  }

  private IncidentDetailedResponseDto createConfirmedIncidentDetailedResponseDto()
      throws IOException {

    final int confirmations = 1;

    return createIncidentDetailedResponseDto().toBuilder()
      .confirm(confirmations)
      .build();
  }

  private IncidentDetailedResponseDto createDeniedIncidentDetailedResponseDto() throws IOException {
    final int denies = 1;

    return createConfirmedIncidentDetailedResponseDto().toBuilder()
      .deny(denies)
      .build();
  }

  private IncidentDetailedResponseDto updateIncidentDetailedResponseDto() {
    return IncidentDetailedResponseDto.builder()
      .title("updated title")
      .description("description")
      .actorUsername("vanea")
      .media(createMedia())
      .lat(12.12)
      .lon(43.43)
      .build();
  }

  private Incident createIncident() {
    return Incident.builder()
      .actorId(new ActorId("id"))
      .locationId(new LocationId(1L))
      .title("title")
      .description("description")
      .media(createMedia())
      .build();
  }

  private Incident createConfirmedIncident() {
    final int confirmations = 1;
    final int denies = 0;
    final int consecutiveDenies = 0;
    final EngagementStats engagementStats = new EngagementStats(
      confirmations, denies, consecutiveDenies);

    return createIncident().toBuilder()
      .engagementStats(engagementStats)
      .build();
  }

  private Incident createDeniedIncident() {
    final int confirmations = 0;
    final int denies = 1;
    final int consecutiveDenies = 0;
    final EngagementStats engagementStats = new EngagementStats(
        confirmations, denies, consecutiveDenies);

    return createIncident().toBuilder()
      .engagementStats(engagementStats)
      .build();
  }

  private Incident updateIncident() {
    return Incident.builder()
      .actorId(new ActorId("id"))
      .locationId(new LocationId(1L))
      .title("updated title")
      .description("description")
      .media(createMedia())
      .build();
  }
}
