package com.springanything.learning;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;

import org.junit.jupiter.api.Test;

class SerializationTest {

  @Test
  void serialize() throws IOException, ClassNotFoundException {
    // given
    String filename = "person.txt";

    Person person = new Person("홍길동", 30);
    FileOutputStream fos = new FileOutputStream(filename);
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(person);
    oos.close();

    // when
    FileInputStream fis = new FileInputStream(filename);
    ObjectInputStream ois = new ObjectInputStream(fis);
    Person read = (Person) ois.readObject();
    ois.close();

    // then
    assertThat(read.name()).isEqualTo(person.name());
    assertThat(read.age()).isEqualTo(person.age());
  }

  private record Person(String name, int age) implements Serializable {

    @Serial
    private static final long serialVersionUID = -7567422510882910778L;
  }
}
