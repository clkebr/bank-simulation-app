package com.bankSimulation.entity;

import com.bankSimulation.enums.AccountStatus;
import com.bankSimulation.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private BigDecimal balance;

    @Enumerated(value = EnumType.STRING)
    private AccountType accountType;

   @Column(columnDefinition = "TIMESTAMP")
    private Date creationDate;

    private Long userId;

    @Enumerated(value = EnumType.STRING)
    private AccountStatus accountStatus;


}
