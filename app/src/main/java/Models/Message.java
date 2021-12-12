package Models;

public class Message {
    private int type;
    private String senderName;
    private String content;
    private String timeStr;

    public Message(int type, String senderName, String content) {
        this.type = type;
        this.senderName = senderName;
        this.content = content;
    }

    public Message(int type, String senderName, String content,String time) {
        this.type = type;
        this.senderName = senderName;
        this.content = content;
        this.timeStr = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
}
