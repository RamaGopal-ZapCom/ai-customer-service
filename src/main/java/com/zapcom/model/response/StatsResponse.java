package com.zapcom.model.response;

public class StatsResponse {
    private String date;
    private int sessions;
    private int messages;
    private int apicalls;

    public StatsResponse(String date, int sessions, int messages, int apicalls) {
        this.date = date;
        this.sessions = sessions;
        this.messages = messages;
        this.apicalls = apicalls;
    }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public int getSessions() { return sessions; }
    public void setSessions(int sessions) { this.sessions = sessions; }
    public int getMessages() { return messages; }
    public void setMessages(int messages) { this.messages = messages; }
    public int getApicalls() { return apicalls; }
    public void setApicalls(int apicalls) { this.apicalls = apicalls; }
}
