package com.example.demoCarsForSale.controllers.dto.response;

public class PhoneResponse {
    private String phone;

    PhoneResponse(String phone) {
        this.phone = phone;
    }

    public static PhoneResponseBuilder builder() {
        return new PhoneResponseBuilder();
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static class PhoneResponseBuilder {
        private String phone;

        PhoneResponseBuilder() {
        }

        public PhoneResponseBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public PhoneResponse build() {
            return new PhoneResponse(phone);
        }

        public String toString() {
            return "PhoneResponse.PhoneResponseBuilder(phone=" + this.phone + ")";
        }
    }
}
