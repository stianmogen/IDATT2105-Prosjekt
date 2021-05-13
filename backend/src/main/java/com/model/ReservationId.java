package com.model;

import com.fasterxml.uuid.Generators;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class ReservationId implements Serializable {

    public ReservationId(UUID userId, UUID sectionId){
        this.userId = userId;
        this.sectionId = sectionId;
    }

    @Column(name = "user_id", columnDefinition = "CHAR(32")
    private UUID userId;
    @Column(name = "section_id", columnDefinition = "CHAR(32)")
    private UUID sectionId;
    @Column(columnDefinition = "CHAR(32)")
    private final UUID uniqueId = Generators.randomBasedGenerator().generate();
}
