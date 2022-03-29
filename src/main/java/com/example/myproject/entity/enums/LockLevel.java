package com.example.myproject.entity.enums;

public enum LockLevel {
	ALL("ALL"), 
	FRIENDS("FRIENDS"),
	ME("ME");
	
	final private String value;
	
	LockLevel(String value) {
        this.value = value;
    }

    public String getKey() {// ALL
        return name();
    }

    public String getValue() {// "all"
        return value;
    }
}
