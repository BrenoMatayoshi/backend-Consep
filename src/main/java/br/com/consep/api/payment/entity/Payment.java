package br.com.consep.api.payment.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.consep.api.base.BaseModel;
import br.com.consep.api.shared.enums.TypeDocument;
import br.com.consep.api.toPay.entity.ToPay;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "payment")
public class Payment extends BaseModel {

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private TypeDocument typeDocument;

  @Column(name = "created_at")
  private LocalDate createdAt;

  @Column(name = "value")
  private BigDecimal value;

  @ManyToOne
  @JoinColumn(name = "to_pay_id", referencedColumnName = "id")
  private ToPay toPay;

  @PrePersist
  public void prePersist() {
    if (this.createdAt == null) {
      this.createdAt = LocalDate.now();
    }
  }
}
