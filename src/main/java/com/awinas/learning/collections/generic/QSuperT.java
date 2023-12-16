package com.awinas.learning.collections.generic;

import java.util.List;

public class QSuperT<T> {

	T val;

	public void qSuperT(List<? super T> o) {
		System.out.println(o.toArray());
	}

}
