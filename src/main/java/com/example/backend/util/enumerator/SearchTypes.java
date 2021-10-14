package com.example.backend.util.enumerator;

import lombok.Getter;

@Getter
public enum  SearchTypes {
    INFO("info"),
    PHONE("phone");

    private String searchType;

    SearchTypes(String searchType) {
        this.searchType = searchType;
    }
}
