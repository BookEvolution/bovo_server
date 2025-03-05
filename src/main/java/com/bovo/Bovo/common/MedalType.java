package com.bovo.Bovo.common;

import lombok.Getter;

@Getter
public enum MedalType {
    BM("동메달"),
    SM("은메달"),
    GM("금메달"),
    BT("동트로피"),
    ST("은트로피"),
    GT("금트로피"),
    BC("동왕관"),
    SC("은왕관"),
    GC("금왕관");

    private final String description;

    MedalType(String description) {
        this.description = description;
    }
}
