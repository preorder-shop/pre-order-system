package com.example.reservation.dto.request;

import jakarta.persistence.SecondaryTable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class EmailCertificationReq {

    @NotEmpty
    @Email
    private String email;

}
