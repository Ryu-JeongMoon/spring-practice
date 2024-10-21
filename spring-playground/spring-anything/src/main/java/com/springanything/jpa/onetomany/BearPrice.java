package com.springanything.jpa.onetomany;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.springanything.tsid.Tsid;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "bear_price")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString
public class BearPrice {

  @Id
  @Tsid
  private String id;

  @OneToMany(mappedBy = "bearPrice", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @ToString.Exclude
  private List<BearOptionGroup> bearOptionGroups = new ArrayList<>();

  public BearPrice(List<BearOptionGroup> bearOptionGroups) {
    update(bearOptionGroups);
  }

  public void update(List<BearOptionGroup> pandaOptionGroups) {
    this.bearOptionGroups.clear();
    this.bearOptionGroups.addAll(pandaOptionGroups);
    this.bearOptionGroups.forEach(bearOptionGroup -> bearOptionGroup.setBearPrice(this));
  }
}
