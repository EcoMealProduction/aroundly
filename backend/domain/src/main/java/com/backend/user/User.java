//package com.backend.user;
//
//import lombok.Builder;
//import lombok.NonNull;
//
 //                                            ATENTIE!!!
//    ***!!!!De discutat in call daca aven nevoie de user(check next comment)!!!!***
//    Eu consider ca nu este nevoie pentru ca noi putem lua username si acelasi id din keyclock,
//    si acestea sa se mearga maparia in entity legat de BD pu ca verificarie la username le setam noi in keyclok,
//    reese ca pu ce noua o entitate aparte de user daca noi avem nevoie doar de username si id si acelea ne trebuie in
//    BD dar nu in domain.
//    Eu am comentat tot pentru a vedea rezultatul final din punctul meu de vedere ca miine sa discutam dar am scris aici
//    ca macar sa ai o viziune de ce am procedat asa.


//
//
//@Builder(toBuilder = true)
//public record User(
//        @NonNull String username
//) {
//
//    public User {
//
//        if (username.trim().isEmpty()) {
//            throw new IllegalArgumentException("Username cannot be empty or only spaces.");
//        }
//
//        if (username.length() < 3)
//            throw new IllegalArgumentException("Username must be at least 3 characters.");
//
//        if (!username.matches("[a-zA-Z0-9._-]+")) {
//            throw new IllegalArgumentException("Invalid username format.");
//        }
//    }
//}
