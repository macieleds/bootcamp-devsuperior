package com.edisonmaciel.dscatalog.dto;


import com.edisonmaciel.dscatalog.services.validation.UserInsertValid;
import com.edisonmaciel.dscatalog.services.validation.UserUpdateValid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@UserUpdateValid
public class UserUpdateDTO extends UserDTO{
    private static final long serialVersionUID = 1L;

}
