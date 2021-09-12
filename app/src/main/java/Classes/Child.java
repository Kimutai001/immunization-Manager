package Classes;

public class Child {
    private String clientName;
    private String imageUrl;
    private String Gender;
    private String clientDOB;
    private String fathersName;
    private String fathersContact;
    private String mothersName;
    private String mothersContact;
    private String county;
    private String subCounty;

  

    public Child(String clientName, String imageUrl, String gender, String clientDOB, String fathersName, String fathersContact, String mothersName, String mothersContact, String county, String subCounty) {
        this.clientName = clientName;
        this.imageUrl = imageUrl;
        Gender = gender;
        this.clientDOB = clientDOB;
        this.fathersName = fathersName;
        this.fathersContact = fathersContact;
        this.mothersName = mothersName;
        this.mothersContact = mothersContact;
        this.county=county;
        this.subCounty=subCounty;

        
    }

    public Child() {
    }

    public Child(String cNameUpdate, String dateOBUpdate, String gender) {
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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getSubCounty() {
        return subCounty;
    }

    public void setSubCounty(String subCounty) {
        this.subCounty = subCounty;
    }
}
