package com.example.bisfproject;

public class HelperClass {
    String duration;
    String carRegNo;
    String timeOut;
    String timeIn;
    String cash;
    String date;
    String venue;
    String slot;
    String payment;
    String mail;
    String recieptNumber;

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRecieptNumber() {
        return recieptNumber;
    }
    public void setRecieptNumber(String recieptNumber) {
        this.recieptNumber = recieptNumber;
    }

    public String getSlot() {
        return slot;
    }
    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getPayment() {
        return payment;
    }
    public void setPayment(String payment) {
        this.payment = payment;
    }


    public String getVenue() {
        return venue;
    }
    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeIn() {
        return timeIn;
    }
    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getCash() {
        return cash;
    }
    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getTimeOut() {
        return timeOut;
    }
    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCarRegNo() {
        return carRegNo;
    }
    public void setCarRegNo(String carRegNo) {
        this.carRegNo = carRegNo;
    }



    public HelperClass(String duration, String carRegNo, String timeOut, String cash, String timeIn,String date,String venue, String slot, String payment,String recieptNumber,String mail) {
        this.duration = duration;
        this.carRegNo = carRegNo;
        this.timeOut = timeOut;
        this.cash = cash;
        this.timeIn = timeIn;
        this.date = date;
        this.venue = venue;
        this.slot = slot;
        this.payment = payment;
        this.recieptNumber = recieptNumber;
        this.mail = mail;
    }

    public HelperClass() {
    }
}
