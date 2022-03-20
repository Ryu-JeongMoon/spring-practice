package org.springframeworkcore.formatter;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Getter
@Setter
@Builder
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
public class Person {

  @Id
  @GeneratedValue
  private Long id;

  private String name;
}
