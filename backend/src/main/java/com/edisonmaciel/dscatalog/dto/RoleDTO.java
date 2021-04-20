package com.edisonmaciel.dscatalog.dto;

import com.edisonmaciel.dscatalog.entities.Role;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String authority;

    public RoleDTO(Role role){
        id = role.getId();
        authority = role.getAuthority();

    }

}
