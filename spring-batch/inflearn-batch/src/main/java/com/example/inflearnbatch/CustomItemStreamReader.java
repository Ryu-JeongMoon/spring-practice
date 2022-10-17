package com.example.inflearnbatch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;

public class CustomItemStreamReader<T> implements ItemStreamReader<T> {

	private final List<T> items = new ArrayList<>();
	private int index;
	private boolean restarted;

	public CustomItemStreamReader(List<T> items) {
		this.items.addAll(items);
	}

	@Override
	public T read() throws ParseException, NonTransientResourceException {
		T item = null;
		if (index < items.size()) {
			item = items.get(index++);
		}

		if (index == 6 && !restarted) {
			throw new RuntimeException("restart required");
		}

		return item;
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		if (executionContext.containsKey("index")) {
			index = executionContext.getInt("index");
			restarted = true;
			return;
		}

		executionContext.put("index", 0);
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		executionContext.put("index", index);
	}

	@Override
	public void close() throws ItemStreamException {
		System.out.println("CustomItemStreamReader.close");
	}
}
