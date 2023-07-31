package com.example.zookeeperscheduler.company.model;

import com.example.zookeeperscheduler.company.model.listener.CompanyEntityListener;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "company")
@Getter
@Setter
@ToString(exclude = {"id", "data"})
@EqualsAndHashCode(of = {"id", "data"}, callSuper = false)
@EntityListeners({AuditingEntityListener.class, CompanyEntityListener.class})
public class CompanyEntity extends BaseEntity {
    @Column(updatable = false)
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Company data;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private FetchStatus status;
}
