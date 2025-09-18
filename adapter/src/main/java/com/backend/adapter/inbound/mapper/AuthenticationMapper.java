package com.backend.adapter.inbound.mapper;

import com.backend.adapter.inbound.dto.request.LoginRequestDto;
import com.backend.adapter.inbound.dto.request.RegistrationRequestDto;
import com.backend.adapter.inbound.dto.response.LoginResponseDto;
import com.backend.adapter.inbound.dto.response.RegistrationResponseDto;
import com.backend.port.inbound.commands.auth.LoginCommand;
import com.backend.port.inbound.commands.auth.LoginFeedback;
import com.backend.port.inbound.commands.auth.RegistrationCommand;
import com.backend.port.inbound.commands.auth.RegistrationFeedback;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for converting between authentication DTOs
 * received in controller requests and domain command/feedback objects.
 *
 * Used to translate client-provided authentication data into domain objects
 * for use within the application layer, and convert domain results back to DTOs.
 */
@Mapper(componentModel = "spring")
public interface AuthenticationMapper {

  /**
   * Maps a {@link LoginRequestDto} from the client into a domain {@link LoginCommand}.
   * Converts login credentials from the API layer to the application layer format.
   *
   * @param loginRequestDto the client-provided login request DTO
   * @return the mapped domain command object
   */
  LoginCommand toLoginCommand(LoginRequestDto loginRequestDto);

  /**
   * Maps a domain {@link LoginFeedback} to a {@link LoginResponseDto},
   * typically for returning authentication results to the client.
   *
   * @param loginFeedback the domain login processing result
   * @return a login response DTO for the client
   */
  LoginResponseDto toLoginResponseDto(LoginFeedback loginFeedback);

  /**
   * Maps a {@link RegistrationRequestDto} from the client into a domain {@link RegistrationCommand}.
   * Converts registration data from the API layer to the application layer format.
   *
   * @param registrationRequestDto the client-provided registration request DTO
   * @return the mapped domain command object
   */
  RegistrationCommand toRegisterCommand(RegistrationRequestDto registrationRequestDto);

  /**
   * Maps a domain {@link RegistrationFeedback} to a {@link RegistrationResponseDto},
   * typically for returning registration results to the client.
   *
   * @param registrationFeedback the domain registration processing result
   * @return a registration response DTO for the client
   */
  RegistrationResponseDto toRegistrationResponseDto(RegistrationFeedback registrationFeedback);
}
