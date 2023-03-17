
package com.springanything.mapping.nested;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
@ToString
class InnerRequest implements Serializable {

	private String name;
	private String nickname;
}
