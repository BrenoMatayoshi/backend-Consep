package br.com.consep.api.toPay.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.consep.api.base.BaseModel;
import br.com.consep.api.payment.entity.Payment;
import br.com.consep.api.project.entity.Project;
import br.com.consep.api.shared.enums.ToPayStatus;
import br.com.consep.api.shared.enums.TypePayment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "to_pay")
public class ToPay extends BaseModel {

  @Column(name = "value")
  private BigDecimal value;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private TypePayment typePayment;

  @Column(name = "pix_code")
  private String pixCode;

  @Column(name = "created_at")
  private LocalDate createdAt;

  @Column(name = "paid_at")
  private LocalDate paidAt;

  @Column(name = "due_date")
  private LocalDate dueDate;

  @Column(name = "scheduled_at")
  private LocalDate scheduledAt;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private ToPayStatus status;

  @OneToMany(mappedBy = "toPay")
  private List<Payment> payment;

  @ManyToOne(optional = false)
  @JoinColumn(name = "project_id", referencedColumnName = "id")
  private Project project;

  @PrePersist
  public void prePersist() {
    if (this.createdAt == null) {
      this.createdAt = LocalDate.now();
    }
    if (this.status == null) {
      this.status = ToPayStatus.PENDING;
    }
  }
}
