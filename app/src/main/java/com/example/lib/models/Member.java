package com.example.lib.models;

public class Member {
    public int memberId;
    public String name;
    public String address;
    public String phone;

    public Member(int memberId, String name, String address, String phone) {
        this.memberId = memberId;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    // getters and setters
}
