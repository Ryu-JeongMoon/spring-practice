package com.springanything.jpa.onetone;

import java.util.List;

public interface MasterRepositoryCustom {

	void deleteByIds(List<Long> ids);
}
