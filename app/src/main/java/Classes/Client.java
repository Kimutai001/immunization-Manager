package Classes;

public class Client {
    private String clientName;
    private String imageUrl;
    String vaccineName;

    private String Gender;
    private String clientDOB;
    private String fathersName;
    private String fathersContact;
    private String mothersName;
    private String mothersContact;

  

    public Client(String clientName, String imageUrl, String gender, String clientDOB, String fathersName, String fathersContact, String mothersName, String mothersContact, String vaccineName) {
        this.clientName = clientName;
        this.imageUrl = imageUrl;
        Gender = gender;
        this.clientDOB = clientDOB;
        this.fathersName = fathersName;
        this.fathersContact = fathersContact;
        this.mothersName = mothersName;
        this.mothersContact = mothersContact;
        this.vaccineName = vaccineName;
        
    }

    public Client() {
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getClientDOB() {
        return clientDOB;
    }

    public void setClientDOB(String clientDOB) {
        this.clientDOB = clientDOB;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getFathersContact() {
        return fathersContact;
    }

    public void setFathersContact(String fathersContact) {
        this.fathersContact = fathersContact;
    }

    public String getMothersName() {
        return mothersName;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }

    public String getMothersContact() {
        return mothersContact;
    }

    public void setMothersContact(String mothersContact) {
        this.mothersContact = mothersContact;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }
}
