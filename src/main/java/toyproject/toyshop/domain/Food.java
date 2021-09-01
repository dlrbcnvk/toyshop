package toyproject.toyshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("F")
@Getter
@Setter
public class Food extends Item {
    private String name;
    private LocalDateTime endDate;
}
