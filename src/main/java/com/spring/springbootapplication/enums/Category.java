package com.spring.springbootapplication.enums;

public enum Category {
    BACKEND(1, "バックエンド"),
    FRONTEND(2, "フロントエンド"),
    INFRA(3, "インフラ");

    private final Integer id;
    private final String name;

    Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Category fromId(Integer id) {
        for (Category category : values()) {
            if (category.id == id) {
                return category;
            }
        }
        throw new IllegalArgumentException("無効なカテゴリID: " + id);
    }
}
