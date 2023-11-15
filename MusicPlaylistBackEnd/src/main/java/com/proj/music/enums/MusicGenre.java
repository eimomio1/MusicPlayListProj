package com.proj.music.enums;

public enum MusicGenre {
 	ROCK("Rock"),
    POP("Pop"),
    HIP_HOP("Hip Hop"),
    JAZZ("Jazz"),
    COUNTRY("Country"),
    CLASSICAL("Classical"),
    ELECTRONIC("Electronic"),
    RAP("Rap"),
    REGGAE("Reggae"),
    BLUES("Blues"),
    METAL("Metal"),
    FOLK("Folk"),
    R_AND_B("R&B"),
    INDIE("Indie"),
    LATIN("Latin"),
    SOUL("Soul"),
    PUNK("Punk"),
    FUNK("Funk"),
    DISCO("Disco"),
    EDM("EDM"),
    ALTERNATIVE("Alternative");
	
	private final String displayName;

    MusicGenre(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
