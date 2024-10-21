package com.springanything.jpa.onetomany;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.springanything.tsid.Tsid;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "panda_price")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString
public class PandaPrice {

  @Id
  @Tsid
  private String id;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "panda_price_id")
  @ToString.Exclude
  private List<PandaOptionGroup> pandaOptionGroups = new ArrayList<>();

  public PandaPrice(List<PandaOptionGroup> pandaOptionGroups) {
    this.pandaOptionGroups = pandaOptionGroups;
  }

  public void update(List<PandaOptionGroup> pandaOptionGroups) {
    this.pandaOptionGroups.clear();
    this.pandaOptionGroups.addAll(pandaOptionGroups);
  }
}
