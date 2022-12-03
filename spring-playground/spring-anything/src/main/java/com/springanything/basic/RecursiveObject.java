package com.springanything.basic;

public record RecursiveObject(int depth, String name, RecursiveObject recursiveObject) {

}
