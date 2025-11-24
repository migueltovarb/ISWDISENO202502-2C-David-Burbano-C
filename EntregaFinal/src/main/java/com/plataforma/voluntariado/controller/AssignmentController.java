package com.plataforma.voluntariado.controller;

import com.plataforma.voluntariado.dto.AssignmentDTO;
import com.plataforma.voluntariado.dto.AttendanceDTO;
import com.plataforma.voluntariado.dto.ConfirmAttendanceDTO;
import com.plataforma.voluntariado.service.AssignmentService;
import com.plataforma.voluntariado.service.AttendanceService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentController {

  private final AssignmentService assignmentService;
  private final AttendanceService attendanceService;

  @PostMapping
  public AssignmentDTO assign(@Valid @RequestBody AssignmentDTO dto,
      @AuthenticationPrincipal(expression = "user.id") UUID requesterId) {
    return assignmentService.assignVolunteer(dto, requesterId);
  }

  @PostMapping("/{id}/cancel")
  public AssignmentDTO cancel(@PathVariable UUID id, @RequestBody String reason,
      @AuthenticationPrincipal(expression = "user.id") UUID requesterId) {
    return assignmentService.cancelAssignment(id, reason, requesterId);
  }

  @PostMapping("/{id}/attendance")
  public AttendanceDTO registerAttendance(@PathVariable UUID id, @Valid @RequestBody AttendanceDTO dto,
      @AuthenticationPrincipal(expression = "user.id") UUID requesterId) {
    AttendanceDTO payload = new AttendanceDTO(
        dto.attendanceId(),
        id.toString(),
        dto.date(),
        dto.present(),
        dto.arrivalTime(),
        dto.departureTime(),
        dto.performanceLevel(),
        dto.observations()
    );
    return attendanceService.registerAttendance(payload, requesterId);
  }

  @PostMapping("/{id}/attendance/confirm")
  public boolean confirmDigital(@PathVariable UUID id, @Valid @RequestBody ConfirmAttendanceDTO dto) {
    ConfirmAttendanceDTO payload = new ConfirmAttendanceDTO(id.toString(), dto.code());
    return attendanceService.confirmDigital(payload);
  }
}
