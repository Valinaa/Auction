package cn.valinaa.auction.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum AuthorityEnum {
    ROLE_ANONYMOUS(List.of("ROLE_READ_GOODS")),
    
    ROLE_USER(List.of("ROLE_READ_GOODS","ROLE_ADD_GOODS", "ROLE_ADD_AUCTION",
            "ROLE_READ_AUCTION", "ROLE_READ_RANK")),
    
    ROLE_VIP_USER(List.of("ROLE_EDIT_GOODS","ROLE_ADD_GOODS", "ROLE_ADD_AUCTION",
            "ROLE_READ_AUCTION", "ROLE_READ_RANK","ROLE_READ_GOODS")),
    
    ROLE_ADMIN(List.of("ROLE_EDIT_GOODS","ROLE_ADD_GOODS", "ROLE_ADD_AUCTION",
            "ROLE_READ_AUCTION", "ROLE_READ_RANK","ROLE_READ_GOODS",
            "ROLE_MANAGE_USER","ROLE_MANAGE_GOODS","ROLE_MANAGE_AUCTION"
            ,"ROLE_MANAGE_RANK"));
    private final List<String> authorities;
}
