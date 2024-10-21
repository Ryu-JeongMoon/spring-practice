package com.springanything.jpa.onetomany;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.springanything.tsid.Tsid;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "bear_option_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString
public class BearOptionGroup {

  @Id
  @Tsid
  private String id;

  @ManyToOne
  @JoinColumn(name = "bear_price_id")
  private BearPrice bearPrice;

  @Column(name = "name")
  private String name;

  @Column(name = "price")
  private long price;

  public BearOptionGroup(
    String name,
    long price
  ) {
    this.name = name;
    this.price = price;
  }

  void setBearPrice(BearPrice bearPrice) {
    this.bearPrice = bearPrice;
  }
}
