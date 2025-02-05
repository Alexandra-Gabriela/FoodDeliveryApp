package org.fooddelivery.DTOClases.DTOUsers;

import lombok.*;
import org.fooddelivery.DomainModel.Users.UserType;

import java.util.Date;

@Data
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String email;
    private String phoneNumber;
    private String name;
    private Date createdAt;
    private UserType userType;
    private Date lastLogin;
    private Integer totalDiscountsReceived;
}
