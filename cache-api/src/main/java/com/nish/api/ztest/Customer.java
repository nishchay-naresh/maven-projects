package com.nish.api.ztest;

//@Entity
public class Customer {

//    @Id
    int id;
    String email;
    int age;

    public Customer() {
    }

    public Customer(int id, String email, int age) {
        this.id = id;
        this.email = email;
        this.age = age;
    }


}
