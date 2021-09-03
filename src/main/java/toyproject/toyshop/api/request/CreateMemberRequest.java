package toyproject.toyshop.api.request;

import lombok.Data;
import toyproject.toyshop.domain.Address;

@Data
public class CreateMemberRequest {
    private String name;
    private String email;
    private String password;
    private Address address;

    public CreateMemberRequest(String name, String email, String password, Address address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public CreateMemberRequest() {
    }
}
