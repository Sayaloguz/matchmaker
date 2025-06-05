package com.saraylg.matchmaker.matchmaker.controller;

import com.saraylg.matchmaker.matchmaker.dto.input.InvitationInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.output.InvitationOutputDTO;
import com.saraylg.matchmaker.matchmaker.mapper.InvitationMapper;
import com.saraylg.matchmaker.matchmaker.service.InvitationService;
import com.saraylg.matchmaker.matchmaker.service.generics.GenericInvitation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/invitations")
public class InvitationController {

    private final InvitationService invitationService;
    private final InvitationMapper invMapper;

    @Operation(summary = "Obtener todas las invitaciones pendientes de un usuario")
    @GetMapping("/{userId}")
    public List<InvitationOutputDTO> getFromUser(@PathVariable String userId) {

        return invMapper.genericListToOutputList(invitationService.getFromUser(userId));

    }

    @Operation(summary = "Crear una invitación")
    @PostMapping("/")
    public InvitationOutputDTO createInvite(@Valid @RequestBody InvitationInputDTO invInputDTO) {
        System.out.println(invMapper.inputToGeneric(invInputDTO).getInvId());
        return invMapper
                .genericToOutput(invitationService
                        .createInvite(invMapper
                                .inputToGeneric(invInputDTO)));
    }

    @Operation(summary = "Borrar la invitación")
    @DeleteMapping("/{invId}")
    public InvitationOutputDTO deleteInvite(@PathVariable String invId) {
        return invMapper.genericToOutput(invitationService.deleteInvite(invId));
    }

}
